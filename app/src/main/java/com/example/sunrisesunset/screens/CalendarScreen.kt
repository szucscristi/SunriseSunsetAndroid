import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sunrisesunset.models.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    latitude: String,
    longitude: String,
    calendarViewModel: CalendarViewModel = viewModel(),
    navController: NavController
) {
    val lat = latitude.toDoubleOrNull() ?: 0.0
    val lon = longitude.toDoubleOrNull() ?: 0.0
    val startDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    val endDate = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_DATE)

    LaunchedEffect(lat, lon) {
        calendarViewModel.fetchMonthlySunriseSunset(lat, lon, startDate, endDate)
    }

    val monthlySunriseSunsetData by remember { calendarViewModel.monthlySunriseSunsetData }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button",
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Calendar for the next month",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 23.sp,
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(monthlySunriseSunsetData) { data ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 20.dp)
                ) {
                    Text(
                        text = "Date: ${data.date}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Sunrise: ${data.sunrise},\nSunset: ${data.sunset}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
