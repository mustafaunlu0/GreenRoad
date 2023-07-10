package com.example.greenroad.ui.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenroad.data.model.Location
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) :  ViewModel() {

    private val _locationList = MutableLiveData<List<Location>>()

    val locationList : LiveData<List<Location>>
        get() = _locationList

    fun pushDataToFirebase(locations : List<Location>){
        for(location in locations){
            firestore.collection("locations").add(location).addOnSuccessListener {
                println("Success")
            }.addOnFailureListener {
                println("Failure!")
            }
        }

    }

    fun getDataFromFirebase(){
        val locations = arrayListOf<Location>()
        firestore.collection("locations").get().addOnSuccessListener {result ->
            for(document in result){
                locations.add(Location(document.data["name"].toString(),document.data["latitude"].toString().toDouble(),document.data["longitude"].toString().toDouble(),document.data["point"].toString().toInt()))
            }
            _locationList.value=locations
        }

    }





}