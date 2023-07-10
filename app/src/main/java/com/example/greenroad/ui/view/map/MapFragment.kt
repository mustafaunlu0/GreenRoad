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
import androidx.fragment.app.viewModels
import com.example.greenroad.data.model.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val mapViewModel: MapViewModel by viewModels()


    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map))
        placeMarkers(googleMap)
    }

    private fun placeMarkers(googleMap: GoogleMap) {

        mapViewModel.getDataFromFirebase()
        mapViewModel.locationList.observe(viewLifecycleOwner) { locations ->
            for (location in locations) {
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    ).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
                )
            }
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(40.9769097, 28.7855918),
                    11f
                )
            )

        }

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

        //mapViewModel.pushDataToFirebase()

    }

}