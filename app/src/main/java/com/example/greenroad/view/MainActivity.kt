package com.example.greenroad.view

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.greenroad.R
import com.example.greenroad.databinding.ActivityMainBinding
import com.example.greenroad.view.home.HomeFragment
import com.example.greenroad.view.home.ProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit  var _binding : ActivityMainBinding
    var homeFragment = HomeFragment()
    var profileFragment = ProfileFragment()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.bottomNavigationView.background=null
        _binding.bottomNavigationView.menu.getItem(2).isEnabled=false

        //Navigation with Menu
        val navController = findNavController(R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(_binding.bottomNavigationView, navController = navController)





    }



}