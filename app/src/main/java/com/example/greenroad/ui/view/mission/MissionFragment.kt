package com.example.greenroad.ui.view.mission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenroad.R
import com.example.greenroad.adapters.MissionAdapter
import com.example.greenroad.databinding.FragmentMissionBinding
import com.example.greenroad.ui.view.map.MapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MissionFragment : Fragment() {

    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var missionAdapter: MissionAdapter
    private var binding: FragmentMissionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMissionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding!!.missionRecyclerView.layoutManager = LinearLayoutManager(context)
        missionAdapter = MissionAdapter()

        mapViewModel.getDataFromFirebase()
        mapViewModel.locationList.observe(viewLifecycleOwner) {
            println("Buraya geldi" + it.size)
            missionAdapter.setList(it)
        }
        binding!!.missionRecyclerView.adapter = missionAdapter


    }


}