package com.example.sunrisesunset.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import java.io.IOException
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.sunrisesunset.Country
import com.example.sunrisesunset.Routes


@Composable
fun ExploreScreen(navController: NavController, context: Context) {
    LoadCountries(navController, context = context)
}

@Composable
fun LoadCountries(navController: NavController, context: Context) {
    val countries = remember { mutableStateOf<List<Country>?>(null) }

    LaunchedEffect(Unit) {
        val jsonString = try {
            context.assets.open("countries.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return@LaunchedEffect
        }

        countries.value = Json.decodeFromString(jsonString)
    }

    countries.value?.let { countryList ->
        CountryList(navController, countries = countryList)
    }
}

@Composable
fun CountryList(navController: NavController, countries: List<Country>) {
    LazyColumn {
        items(countries) { country ->
            CountryListItem(navController = navController, country = country)
        }
    }
}


@Composable
fun CountryListItem(navController: NavController, country: Country) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(
                Routes.infoScreenRoute(
                    country.latitude,
                    country.longitude,
                    country.name
                )
            ) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val flagResId = getFlagResourceId(country.key.lowercase())

        if (flagResId != null) {
            Image(
                painter = painterResource(id = flagResId),
                contentDescription = "${country.name} flag",
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            text = country.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
    Divider()
}


@Composable
fun getFlagResourceId(key: String): Int? {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(key, "drawable", context.packageName)
    return if (resId != 0) resId else null
}