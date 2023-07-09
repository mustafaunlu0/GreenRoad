package com.example.greenroad.ui.view.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenroad.data.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DatastoreRepository,
    private val sensorManager: SensorManager
) : ViewModel(), SensorEventListener {

    private val _stepCount = MutableLiveData<Int>()
    private val _currentSteps = MutableLiveData<Int>()

    val currentSteps : LiveData<Int>
        get() = _currentSteps
    val stepCount: LiveData<Int>
        get() = _stepCount

    var running = true
        private set
    private var totalSteps = 0f
     var previousTotalSteps = 0f
         private set

    fun checkSensorDetection(){
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            //println("No sensor detected on this device")
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            //println("Sensor detected on this device")
        }
    }

    fun writeCount(stepCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.writeStepCount(stepCount)
        }


    }

    fun readCount() {
        viewModelScope.launch {
            _stepCount.value = dataStoreRepository.readStepCount()
            previousTotalSteps= dataStoreRepository.readStepCount()?.toFloat() ?: 0f
        }
    }

    fun findCurrentSteps(totalSteps: Float, previousSteps: Float): Int {
        return totalSteps.toInt() - previousSteps.toInt()
    }

    fun resetSteps(){
        previousTotalSteps=totalSteps
        writeCount(previousTotalSteps.toInt())
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running) {
            println("Tetiklendi")
            totalSteps = p0!!.values[0]
            _currentSteps.value = findCurrentSteps(totalSteps, previousTotalSteps)

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onCleared() {
        super.onCleared()
        running = false


    }


}