package com.example.greenroad.ui.view.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import com.example.greenroad.databinding.FragmentHomeBinding
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.example.greenroad.util.Constants.STEP_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first



@AndroidEntryPoint
class HomeFragment  : Fragment(){

    private val homeViewModel : HomeViewModel by viewModels()

    //KULLANICININ APP İZNİ VERMESİ LAZIM KONTROL ET
    private var _binding : FragmentHomeBinding? = null


    private var previousTotalSteps = 0f




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.readCount()

        _binding!!.timeImageView.setOnClickListener {
            homeViewModel.resetSteps()
            _binding!!.stepCounter.text=0.toString()
        }

        homeViewModel.currentSteps.observe(viewLifecycleOwner){
            _binding!!.stepCounter.text=it.toString()
        }


    }

    override fun onResume() {
        super.onResume()
        homeViewModel.checkSensorDetection()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }



}



