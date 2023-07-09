package com.example.greenroad.ui.view.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greenroad.R

import com.google.android.gms.maps.CameraUpdateFactory
import android.content.res.Resources
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

class MapFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.map))

        
        placeMarkers(googleMap)
        
        



    }

    private fun placeMarkers(googleMap: GoogleMap) {

        val locationArrayList = arrayListOf<LatLng>()

        val florya = LatLng(40.9769097, 28.7855918)
        val bakirkoy= LatLng(40.975238, 28.880226)
        val kucukcekmece = LatLng(41.000138, 28.764883)
        val menekse= LatLng(40.981473, 28.762336)
        val buyukcekmece = LatLng(41.016865, 28.588332)
        val balat = LatLng(41.030339,28.9526729)

        locationArrayList.add(florya)
        locationArrayList.add(bakirkoy)
        locationArrayList.add(kucukcekmece)
        locationArrayList.add(menekse)
        locationArrayList.add(buyukcekmece)
        locationArrayList.add(balat)

        for (location in locationArrayList){
            googleMap.addMarker(MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)))
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(florya,11f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}