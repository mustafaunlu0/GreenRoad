package com.example.greenroad.ui.view.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.greenroad.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeViewModel (private var sensorManagerFromActivity: SensorManager?) : ViewModel(),SensorEventListener {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")



    //Step Sensor
    private  var sensorManager : SensorManager? = null

    private var running = false

    private var totalSteps = 0f

    private var previousTotalSteps = 0f

    private object PreferencesKey{
        val STEP_COUNT = intPreferencesKey("stepCount")
    }

    init {
        if(sensorManagerFromActivity != null)
            sensorManager=sensorManagerFromActivity
        else
            println("sensorManagerFromActivity")

    }


    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    private fun saveData() {
        viewModelScope.launch {
            //writeStepCount(previousTotalSteps.toInt())
        }
    }

    private fun laodData() {
        viewModelScope.launch {
            /*
            val deger = readStepCount()

            if(deger != null){
                previousTotalSteps = deger!!.toFloat()
            }else{
                previousTotalSteps = 20f

            }

             */

        }
    }

    private fun resetSteps() {
            previousTotalSteps = totalSteps
            saveData()


    }

}