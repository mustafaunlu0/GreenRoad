package com.example.greenroad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenroad.data.model.Location
import com.example.greenroad.databinding.MissionItemBinding

class MissionAdapter : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {

    private var locationList: List<Location> = listOf()
    fun setList(locationList: List<Location>) {
        this.locationList = locationList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MissionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MissionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            with(locationList[position]) {
                binding.locationName.text = this.name
                binding.locationPoint.text = this.point.toString()


            }
        }


    }


    override fun getItemCount(): Int {
        return locationList.size
    }


}