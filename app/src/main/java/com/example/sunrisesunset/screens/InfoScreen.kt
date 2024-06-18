import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sunrisesunset.models.InfoViewModel
import com.example.sunrisesunset.R
import androidx.compose.material3.Button
import androidx.navigation.NavController
import com.example.sunrisesunset.Routes

@Composable
fun InfoScreen(navController: NavController, latitude: String, longitude: String, country: String, infoViewModel: InfoViewModel = viewModel()) {
    val lat = latitude.toDoubleOrNull() ?: 0.0
    val lon = longitude.toDoubleOrNull() ?: 0.0

    LaunchedEffect(lat, lon) {
        infoViewModel.fetchSunriseSunset(lat, lon)
        infoViewModel.fetchTomorrowSunriseSunset(lat, lon)
    }

    val sunriseSunsetData by remember { infoViewModel.sunriseSunsetData }
    val tomorrowSunriseSunsetData by remember { infoViewModel.tomorrowSunriseSunsetData }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {


        // ------------------------------------ TODAY ---------------------------------------

        Text(
            text = "Today",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        SunriseInfo(title = "Sunrise", time = sunriseSunsetData?.sunrise ?: "Loading...")

        Text(
            text = "Dawn: ${sunriseSunsetData?.dawn ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Sunrise today in $country will be at ${sunriseSunsetData?.sunrise}.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SunsetInfo(title = "Sunset", time = sunriseSunsetData?.sunset ?: "Loading...")

        Text(
            text = "Dusk: ${sunriseSunsetData?.dusk ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Sunset today in $country will be at ${sunriseSunsetData?.sunset}.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 100.dp)
        )


        // ------------------------------------ TOMORROW ---------------------------------------

        Text(
            text = "Tomorrow",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        SunriseInfo(title = "Sunrise", time = tomorrowSunriseSunsetData?.sunrise ?: "Loading...")

        Text(
            text = "Dawn: ${tomorrowSunriseSunsetData?.dawn ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Sunrise tomorrow in $country will be at ${tomorrowSunriseSunsetData?.sunrise}.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SunsetInfo(title = "Sunset", time = tomorrowSunriseSunsetData?.sunset ?: "Loading...")

        Text(
            text = "Dusk: ${tomorrowSunriseSunsetData?.dusk ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Sunset tomorrow in $country will be at ${tomorrowSunriseSunsetData?.sunset}.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Button(
            onClick = {

                navController.navigate(Routes.calendarScreenRoute(lat,lon))
            },
        ){
            Text("Go to Calendar", style=MaterialTheme.typography.bodyMedium)
        }


    }


}

@Composable
fun SunriseInfo(title: String, time: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 20.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.sunrise),
        contentDescription = "Sunrise Graphic",
        modifier = Modifier.size(64.dp)
    )
    Text(
        text = time,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 15.dp)
    )
}

@Composable
fun SunsetInfo(title: String, time: String){
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 20.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.sunset),
        contentDescription = "Sunset Graphic",
        modifier = Modifier.size(64.dp)
    )
    Text(
        text = time,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 15.dp)
    )
}