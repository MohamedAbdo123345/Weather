package com.example.weatherapp.modelfavorite.viewFavorite

import com.example.weatherapp.dto.LocationDTO

interface OnClickListtenerr {
    fun OnClick(item: LocationDTO)
    fun  onLocationDataClick(latitude: Double, longitude: Double)
}