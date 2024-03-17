package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.db.WeatherLocalDatasourceImpl
import com.example.weatherapp.model.AllWeatherViewModelFactory

import com.example.weatherapp.model.WeatherViewModel
import com.example.weatherapp.network.ApiState
import com.example.weatherapp.network.WeatherRemoteDatasourceImp
import com.example.weatherapp.reposetry.WeatherRepositoryImpl
import com.example.weatherapp.viewhome.DailyAdapter
import com.example.weatherapp.viewhome.HourlyAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.properties.Delegates

private const val My_LOCATION_PERMISSION_ID = 5005
var  latitude:Double=0.0
var longitude :Double=0.0
class HomeFragment : Fragment() {

    private lateinit var allmoviefactory:AllWeatherViewModelFactory
    private lateinit var viewModel:WeatherViewModel
    lateinit var  hourlyrecyclerView: RecyclerView
    lateinit var  dailyrecyclerView: RecyclerView
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var dailyAdapter: DailyAdapter

    lateinit var city:TextView
    lateinit var temperature:TextView
    lateinit var DateTime:TextView
    private lateinit var fusedClient : FusedLocationProviderClient






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view:View= inflater.inflate(R.layout.fragment_home, container, false)
        hourlyrecyclerView=view.findViewById(R.id.recyclerView)
        dailyrecyclerView=view.findViewById(R.id.recyclerViewdaily)
        city=view.findViewById(R.id.txtViewLocation)
        temperature=view.findViewById(R.id.temperatura)
        DateTime=view.findViewById(R.id.textViewDateTime)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        city=view.findViewById(R.id.txtViewLocation)
       // city.text=cityName

        hourlyAdapter= HourlyAdapter(requireContext())
        dailyAdapter= DailyAdapter(requireContext(), TimeZone.getDefault())
        allmoviefactory=AllWeatherViewModelFactory(
            WeatherRepositoryImpl.getInstance(
                WeatherRemoteDatasourceImp.getInstance(requireContext()),
                        WeatherLocalDatasourceImpl.getInstance(requireContext())




            ))
        viewModel= ViewModelProvider(this,allmoviefactory).get(WeatherViewModel::class.java)
        val locationData = getLocationData()
        if (locationData != null) {
            latitude = locationData.first
            longitude = locationData.second
            Log.i("TAG", "onViewCreated41414: $latitude")
            viewModel.fatchDate(latitude,longitude,"metric","en")
            city.text = getAddressEnglish(requireContext(),latitude,longitude)
        }









            // Use latitude and longitude in your API calls or other logic
//        } else {
//            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
//            val cityName = geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()?.locality
//
//            Log.i("TAG", "onViewCreated:565 $cityName")
//            city.text=cityName.toString()
//            viewModel.fatchDate(latitude,longitude,"metric","en")
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weather.collect { weatherData->
                when(weatherData){
                    is ApiState.Success ->{
                        val data=weatherData.data
                        val hourly= data.current?.temp
                        val dataTime=data.current?.dt?.toLong()
                        val x= dataTime?.let { convertTimestamp(it) }
                        Log.i("TAG", "onViewCreated5858:$dataTime ")
                        temperature.text=hourly.toString()
                        DateTime.text=x.toString()


                        hourlyrecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        hourlyrecyclerView.adapter = hourlyAdapter
                        hourlyAdapter.submitList(data.hourly)
                        hourlyAdapter.notifyDataSetChanged()


                        dailyrecyclerView.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                         dailyrecyclerView.adapter=dailyAdapter
                        dailyAdapter.submitList(data.daily)
                        dailyAdapter.notifyDataSetChanged()
                        Toast.makeText(requireContext(), "succes", Toast.LENGTH_SHORT).show()
                    }
                    else ->{
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

    }



    //////////////////////////////////////////////////////////////////
        fun checkPermissions(): Boolean{
            var result = false
            if ((ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ||
                (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)) {
                result = true
            }
            return result
        }
        override fun onRequestPermissionsResult(requestCode: Int, permissions:Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == My_LOCATION_PERMISSION_ID ) {
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                }
            }
        }
        private fun isLocationEnabled(): Boolean {
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }


        @SuppressLint("MissingPermission")
        private fun getLocation():Unit{
            if(checkPermissions()){
                if(isLocationEnabled()){
                    requestNewLocationData()
                }else{
                    Toast.makeText(requireActivity(), "turn onlocation", Toast.LENGTH_SHORT).show()
                    val intent=Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)

                }
            }else{
                requestPermissions()
            }

        }



        @SuppressLint("MissingPermission")
        private fun requestNewLocationData() {
            val locationRequest = LocationRequest()
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setInterval(0)
            fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            fusedClient.requestLocationUpdates(
                locationRequest,
                mlocationCallback,
                Looper.myLooper()
            )
        }
        private  val mlocationCallback : LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                val mLastLocation: Location =locationResult.lastLocation
//                latitude=mLastLocation.latitude
//                  longitude=mLastLocation.longitude
//                city.text = getAddressEnglish(requireContext(),latitude,longitude)
//                viewModel.fatchDate(latitude,longitude,"metric","en")
                saveLocationDataHome(mLastLocation.latitude, mLastLocation.longitude)



            }

        }



        private  fun  requestPermissions(){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                My_LOCATION_PERMISSION_ID
            )
        }


    override fun onStart() {
        super.onStart()
        getLocation()
    }



    private fun getLocationData(): Pair<Double, Double>? {
        val sharedPreferences = requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val latitude = sharedPreferences.getFloat("Latitude", 0.0f).toDouble()
        val longitude = sharedPreferences.getFloat("Longitude", 0.0f).toDouble()
        return if (latitude != 0.0 && longitude != 0.0) {
            Pair(latitude, longitude)
        } else {
            Log.i("TAG", "getLocationData:nulllllllllllll ")

            null
        }
    }
    fun getAddressEnglish(context: Context, lat: Double?, lon: Double?):String{
        var address:MutableList<Address>?
        val geocoder= Geocoder(context)
        address =geocoder.getFromLocation(lat!!,lon!!,1)
        if (address?.isEmpty()==true) {
            return "Unknown location"
        } else if (address?.get(0)?.countryName.isNullOrEmpty()) {
            return "Unknown Country"
        } else if (address?.get(0)?.adminArea.isNullOrEmpty()) {
            return address?.get(0)?.countryName.toString()
        } else{
            var result = address?.get(0)?.adminArea.toString()+", "+address?.get(0)?.countryName
            result = result.replace(" Governorate", "")
            return result
            }
        }



    private fun saveLocationDataHome(latitude: Double, longitude: Double) {
        val sharedPreferences = requireContext().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("Latitude", latitude.toFloat())
        editor.putFloat("Longitude", longitude.toFloat())
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }
   fun convertTimestamp(timestamp: Long): String {
        // Convert timestamp to Date object
        val date = Date(timestamp * 1000) // Multiply by 1000 to convert seconds to milliseconds

        // Format the date and time
        val dateFormat = SimpleDateFormat("EEE MMMM dd | HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }







}