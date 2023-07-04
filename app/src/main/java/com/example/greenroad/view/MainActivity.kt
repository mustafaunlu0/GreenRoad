package com.example.greenroad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.greenroad.R
import com.example.greenroad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit  var _binding : ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.bottomNavigationView.background=null
        _binding.bottomNavigationView.menu.getItem(2).isEnabled=false

        //Navigation with Menu
        val navController = findNavController(R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(_binding.bottomNavigationView, navController = navController)

        _binding.fab.setOnClickListener {
            navController.navigate(R.id.mapsFragment)
        }





    }



}