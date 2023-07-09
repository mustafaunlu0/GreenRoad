package com.example.greenroad.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.greenroad.util.Constants
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class DatastoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun writeStepCount(stepCount : Int ){
        dataStore.edit { stepCounter ->
            stepCounter[Constants.STEP_COUNT] = stepCount
        }
    }

    suspend fun readStepCount() : Int?{
        return dataStore.data.first()[Constants.STEP_COUNT]
    }


}