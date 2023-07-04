package com.example.greenroad.view.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import com.example.greenroad.databinding.FragmentHomeBinding
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.greenroad.view.home.HomeFragment.PreferencesKey.STEP_COUNT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")


class HomeFragment : Fragment(), SensorEventListener {


    //KULLANICININ APP İZNİ VERMESİ LAZIM KONTROL ET
    private var _binding : FragmentHomeBinding? = null


    //Step Sensor
    private  var sensorManager : SensorManager? = null

    private var running = false

    private var totalSteps = 0f

    private var previousTotalSteps = 0f

    //DataStore

    //Key
    private object PreferencesKey{
        val STEP_COUNT = intPreferencesKey("stepCount")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager


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
        //Step Counter with Sensor
        laodData()
        resetSteps()



    }

    suspend fun writeStepCount(stepCount : Int){
        requireContext().dataStore.edit { stepCounter ->
            stepCounter[STEP_COUNT]= stepCount
        }
    }
    suspend fun readStepCount() : Int? {
        return requireContext().dataStore.data.first()[STEP_COUNT]
    }

    override fun onResume() {
        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            //Toast.makeText(requireContext(),"No sensor detected on this device", Toast.LENGTH_LONG).show()
        }else{
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
            //Toast.makeText(requireContext(),"Sensor detected on this device", Toast.LENGTH_LONG).show()
        }


    }

    private fun resetSteps() {

        _binding!!.stepCounter.setOnLongClickListener{

            previousTotalSteps = totalSteps
            _binding!!.stepCounter.text=0.toString()
            saveData()
            true
        }
    }

    private fun saveData() {
        lifecycleScope.launch {
            writeStepCount(previousTotalSteps.toInt())
        }
    }

    private fun laodData() {
        lifecycleScope.launch {

            val deger = readStepCount()

            if(deger != null){
                previousTotalSteps = deger!!.toFloat()
            }else{
                previousTotalSteps = 20f

            }

        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val tvStepsTaken = _binding!!.stepCounter
        if(running){
            Toast.makeText(requireContext(),"Running", Toast.LENGTH_LONG).show()
            totalSteps = event!!.values[0]

            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()

            tvStepsTaken.text = "$currentSteps"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }



}



