package com.example.rocketappdemo.ui.main.home.upcoming_launches

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModelItem
import com.example.rocketappdemo.databinding.ItemUpcomingLaunchesBinding



class UpcomingLaunchesAdapter(
    private var upcList: ArrayList<UpcomingLaunchesModelItem>,
    private val listener: Listener
) : RecyclerView.Adapter<UpcomingLaunchesAdapter.MyViewHolder>() {
    interface Listener {
        fun onItemClick(idLaunch: String)
    }

    class MyViewHolder(val binding: ItemUpcomingLaunchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUpcomingLaunchesBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val upcoming = upcList[position]
        holder.binding.flightNumber.text = upcoming.flight_number.toString()
        holder.binding.textDate.text = upcoming.date_utc
        holder.binding.textName.text = upcoming.name


        holder.binding.root.setOnClickListener {
            listener.onItemClick(upcoming.id)
        }
    }

    override fun getItemCount(): Int {
        return upcList.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpcList(_upcList: ArrayList<UpcomingLaunchesModelItem>) {
        upcList = _upcList
        notifyDataSetChanged()

    }
}