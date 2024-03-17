import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.db.WeatherLocalDatasourceImpl
import com.example.weatherapp.dto.LocationDTO

import com.example.weatherapp.modelfavorite.WeatherFavoriteViewModelFactory
import com.example.weatherapp.modelfavorite.viewFavorite.OnClickListtenerr
import com.example.weatherapp.modelfavorite.viewFavorite.WeatherFavAdapter
import com.example.weatherapp.network.WeatherRemoteDatasourceImp
import com.example.weatherapp.reposetry.WeatherRepositoryImpl
import com.example.weatherapp.viewhome.HourlyAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class FavoriteFragment : Fragment(), OnMapReadyCallback, OnClickListtenerr {

    private lateinit var googleMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private var isMapVisible = false
    private var marker: Marker? = null
    private var selectedLatLng: Double = 0.0
    private var selectedLonLng: Double = 0.0
    private lateinit var selectedLocation: LocationDTO
    private var id: Long = 1
    private lateinit var allWeatherfactory: WeatherFavoriteViewModelFactory
    private lateinit var viewWeather: WeatherFavoriteViewModel
    lateinit var cityrecyclerView: RecyclerView
    lateinit var weatherFavAdapter: WeatherFavAdapter
    private val locationList = mutableListOf<LocationDTO>()
    private lateinit var btnOpenMap: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        allWeatherfactory = WeatherFavoriteViewModelFactory(
            WeatherRepositoryImpl.getInstance(
                WeatherRemoteDatasourceImp.getInstance(requireContext()),
                WeatherLocalDatasourceImpl.getInstance(requireContext())
            )
        )
        viewWeather =
            ViewModelProvider(this, allWeatherfactory).get(WeatherFavoriteViewModel::class.java)
        cityrecyclerView = rootView.findViewById(R.id.recyclerViewCity)
        btnOpenMap = rootView.findViewById<Button>(R.id.btnOpenMap)
        btnOpenMap.setOnClickListener {
            toggleMapVisibility()
           // btnOpenMap.visibility = View.GONE
        }

        return rootView
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        // Initialize map settings
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Initially hide the map
        mapFragment.view?.visibility = View.GONE

        gMap.setOnMapClickListener { latLng ->

            marker?.remove()

            // Add a new marker at the clicked position
            marker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("New Marker")
            )
            selectedLatLng = latLng.latitude
            selectedLonLng = latLng.longitude
            Log.i("TAG", "onMapReady: $selectedLatLng")
            Log.i("TAG", "onMapReady: $selectedLonLng")
            val geocoder = Geocoder(requireActivity(), Locale.getDefault())


            try {
                val addresses = geocoder.getFromLocation(selectedLatLng, selectedLonLng, 1)
                if (addresses!!.isNotEmpty()) {
                    val cityName = addresses[0].locality
                    Log.d("TAG", cityName)
                    Log.i("TAG", "onMapReady:$cityName ")
                    selectedLocation = LocationDTO(selectedLatLng, selectedLonLng, cityName)
                    Toast.makeText(requireContext(), "City: $cityName", Toast.LENGTH_SHORT).show()
                    locationList.add(selectedLocation)

                    // Show Snackbar to ask user if they want to save the place
                    viewWeather.viewModelScope.launch(Dispatchers.IO) {
                        Snackbar.make(
                            requireView(),
                            "Do you want to save this place?",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Save") {
                                // Do something with the selectedLatLng (e.g., save it to database)
                                Log.d("Selected LatLng", selectedLatLng.toString())
                                toggleMapVisibility()
                            }
                            .show()
                        viewWeather.insertWeather(selectedLocation)


                    }
                }
            } catch (e: Exception) {
                Log.e("Geocoder", "Error: ${e.message}")
            }

            // Do whatever you want with the selected LatLng
            Log.d("Selected LatLng", latLng.toString())
        }
    }

    private fun toggleMapVisibility() {
        isMapVisible = !isMapVisible
        mapFragment.view?.visibility = if (isMapVisible) View.VISIBLE else View.GONE
        cityrecyclerView.visibility = if (!isMapVisible) View.VISIBLE else View.GONE
        btnOpenMap.visibility = if (!isMapVisible) View.VISIBLE else View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherFavAdapter = WeatherFavAdapter(requireContext(), this)
//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
//            viewWeather.fatchDate()
//
//            // Observe the weather data from the StateFlow
//            viewWeather.weather.collect { weatherr ->
//                if (weatherr.isNotEmpty()) {
//                    val weather = weatherr[0]
//                    Log.i("TAG", "Weather data: $weather")
//                    Log.i("TAG", "onViewCreated: $weatherr[0]")
//
//                    withContext(Dispatchers.Main) {
//                        cityrecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                        cityrecyclerView.adapter = weatherFavAdapter
//                        weatherFavAdapter.submitList(weatherr)
//                        weatherFavAdapter.notifyDataSetChanged()
//                    }
//                } else {
//                    Log.e("TAG", "Weather data is null or empty")
//                    // Handle the case when weather data is null or empty, such as showing an error message
//                }
//            }
//        }
        cityrecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                     cityrecyclerView.adapter = weatherFavAdapter

        // Observe changes in weatherItems StateFlow
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewWeather.weather.collect { weatherList ->
                weatherFavAdapter.submitList(weatherList)
            }
        }

        cityrecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherFavAdapter
        }
    }





    private fun handleSaveAction() {
        // Hide the map, RecyclerView, and button
        mapFragment.view?.visibility = View.GONE
        cityrecyclerView.visibility = View.VISIBLE
        btnOpenMap.visibility = View.VISIBLE
    }

    override fun OnClick(item: LocationDTO) {
        viewWeather.viewModelScope.launch(Dispatchers.IO) {
            viewWeather.deleteWeather(item)
            Log.i("TAG", "OnClick:ll;,;l.   ")
//    withContext(Dispatchers.Main){    weatherFavAdapter.notifyDataSetChanged()
//    }
        }
    }

    override fun onLocationDataClick(latitude: Double, longitude: Double) {
        saveLocationData(latitude, longitude)
        Log.i("TAG", "onLocationDataClicklat: $latitude")
        Log.i("TAG", "onLocationDataClicklong: $longitude")
    }
    private fun saveLocationData(latitude: Double, longitude: Double) {
        val sharedPreferences = requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("Latitude", latitude.toFloat())
        editor.putFloat("Longitude", longitude.toFloat())
        editor.apply()
    }
}