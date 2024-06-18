import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sunrisesunset.models.ThemeViewModel

@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel = viewModel(),
    locationPermission: Boolean,
    onLocationPermissionToggle: (Boolean) -> Unit,
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = "Settings", fontSize = 32.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Theme", modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { themeViewModel.toggleTheme() }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Location Permission", modifier = Modifier.weight(1f))
            Switch(
                checked = locationPermission,
                onCheckedChange = { onLocationPermissionToggle(it) }
            )
        }
    }
}
