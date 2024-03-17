package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class SeetingFragment : Fragment() {
 lateinit var   kelvinButton:Button
    lateinit var   fahrenheitButton:Button
    lateinit var   celsiusButton:Button
    lateinit var   milesButton:Button
    lateinit var   metreButton:Button
    lateinit var   arabicButton:Button
    lateinit var   englishButton:Button
    lateinit var   darkButton:Button
    lateinit var   lightButton:Button
    lateinit var   locationButton:Button
    lateinit var   mapButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view=inflater.inflate(R.layout.fragment_seeting, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kelvinButton=view.findViewById(R.id.kelvinButton)
        fahrenheitButton=view.findViewById(R.id.fahrenheitButton)
        celsiusButton=view.findViewById(R.id.celsiusButton)
        milesButton=view.findViewById(R.id.mphButton)
            metreButton=view.findViewById(R.id.mpsButton)
            arabicButton=view.findViewById(R.id.arabicButton)
            englishButton=view.findViewById(R.id.englishButton)
            darkButton=view.findViewById(R.id.darkButton)
            lightButton=view.findViewById(R.id.lightButton)
            locationButton=view.findViewById(R.id.locationButton)
            mapButton=view.findViewById(R.id.mapButton)


        kelvinButton.setOnClickListener {
            saveTemperature("Kelvin")
        }

        fahrenheitButton.setOnClickListener {
            saveTemperature("Fahrenheit")
        }

        celsiusButton.setOnClickListener {
            saveTemperature("Celsius")
        }
        englishButton.setOnClickListener {
            saveLanguage("english")
        }
        arabicButton.setOnClickListener {
            saveLanguage("arabic")
        }
        metreButton.setOnClickListener {
            saveWindSpead("Metre")
        }
        milesButton.setOnClickListener {
            saveWindSpead("Miles")
        }
        locationButton.setOnClickListener {
            saveLocationMode("Location")
        }
        mapButton.setOnClickListener {
            saveLocationMode("Map")
        }





    }
    private fun saveTemperature(temp: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Save the selected temperature value based on the button clicked
        when (temp) {
            "Kelvin" -> editor.putString("TemperatureUnit", "K")
            "Fahrenheit" -> editor.putString("TemperatureUnit", "F")
            "Celsius" -> editor.putString("TemperatureUnit", "C")
        }
        editor.apply()
    }
    private fun saveLanguage(language:String) {
        val sharedPreferences = requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when(language) {
            "english" -> editor.putString("Language","en")
            "arabic" -> editor.putString("Language","ar")

        }
        editor.apply()
    }
    private fun saveWindSpead(distance:String) {
        val sharedPreferences = requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when(distance){
            "Miles" -> editor.putString("Distance","miles")
            "Metre" -> editor.putString("Distance","metre")

        }

        editor.apply()
    }
    private fun saveLocationMode(locationMode:String) {
        val sharedPreferences = requireActivity().getSharedPreferences("LocationData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
       when(locationMode){
           "Location" -> editor.putString("LocationMode","location")
           "Map" -> editor.putString("LocationMode","map")
       }

        editor.apply()
    }


}