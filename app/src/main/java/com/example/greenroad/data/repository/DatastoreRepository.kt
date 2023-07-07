package com.example.greenroad.data.repository

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.greenroad.ui.view.home.dataStore
import com.example.greenroad.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")

class DatastoreRepository(
    private var context : Context
) {
    var stepCounters : Int = 0


    suspend fun writeStepCount( key: Preferences.Key<Int>,stepCount : Int){
        withContext(Dispatchers.IO){
            context.dataStore.edit { stepCounter ->
                stepCounter[key]= stepCount
            }
        }
    }

    suspend fun readStepCount( key: Preferences.Key<Int>) : Int{
        withContext(Dispatchers.IO){
            stepCounters = context.dataStore.data.first()[key] ?: 0
        }
        return stepCounters
    }

}