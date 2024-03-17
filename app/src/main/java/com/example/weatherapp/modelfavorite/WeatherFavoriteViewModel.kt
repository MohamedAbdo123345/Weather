import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WeatherFavoriteViewModel(private val irpo: com.example.weatherapp.reposetry.FakeReposetry) : ViewModel() {
    private var _weatherItem: MutableStateFlow<List<LocationDTO>> = MutableStateFlow(emptyList())
    val weather = _weatherItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDate() // Fetch initial data
        }
    }

    fun fetchDate() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherFlow = irpo.getLocalAllWeatherFav() // Get the Flow of weather data
            val weatherData = weatherFlow.first()// Collect the first emitted value from the Flow

            // Update _weatherItem with the collected weather data
            _weatherItem.value = weatherData
            Log.i("TAG", "fetchDate: ${_weatherItem.value}")
            Log.i("TAG", "fetchDate: $weatherData")
        }
    }

    fun deleteWeather(locationDTO: LocationDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            irpo.deletWeather(locationDTO).collect { rowsDeleted ->
                // Handle the result, such as displaying a message or updating the UI
                Log.i("TAG", "Rows deleted: $rowsDeleted")
                fetchDate() // Fetch updated data after deletion
            }
        }
    }

    fun insertWeather(locationDTO: LocationDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            irpo.insertWeather(locationDTO)
            fetchDate() // Fetch updated data after insertion
        }
    }
}
