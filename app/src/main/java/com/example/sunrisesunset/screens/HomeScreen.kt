import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sunrisesunset.Routes
import com.example.sunrisesunset.R



@Composable
fun HomeScreen(navController: NavController) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun_logo),
                contentDescription = "Sun Icon",
                modifier = Modifier.size(128.dp)
            )
            Text(
                text = "SunriseSunset",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = latitude,
                onValueChange = {
                    latitude = it
                    errorMessage = ""
                },
                label = { Text("Enter latitude") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            TextField(
                value = longitude,
                onValueChange = {
                    longitude = it
                    errorMessage = ""
                },
                label = { Text("Enter longitude") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Button(
                onClick = {
                    val lat = latitude.toDoubleOrNull()
                    val lon = longitude.toDoubleOrNull()
                    if (lat != null && lon != null) {
                        navController.navigate(Routes.infoScreenRoute(lat, lon, "the specified location"))
                    } else {
                        errorMessage = "Please enter valid numbers for latitude and longitude."
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Search")
            }
        }
    }
}