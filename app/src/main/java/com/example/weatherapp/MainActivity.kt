package com.example.weatherapp

import FavoriteFragment
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weatherapp.network.RetrofitHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    lateinit var  homeFragment:HomeFragment
    lateinit var favoriteFragment: FavoriteFragment
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        navController= Navigation.findNavController(this,R.id.nav_graphh)
//        setupWithNavController(binding.bottomNavigationView,navController)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(navListener)

        // Display the default fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, HomeFragment()).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.home-> selectedFragment = HomeFragment()
            R.id.favorite -> selectedFragment = FavoriteFragment()
         R.id.setting -> selectedFragment =SeetingFragment()
        }
        selectedFragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, it).commit()
            true
        } ?: false
    }
//fun fatchData()=lifecycleScope.launch (Dispatchers.IO){
//   val data= RetrofitHelper.retrofitservice.getWeather()
//    Log.i("TAG", "fatchData: $data")
//}

    }
