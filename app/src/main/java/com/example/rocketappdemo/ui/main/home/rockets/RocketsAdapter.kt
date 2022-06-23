package com.example.rocketappdemo.ui.main.home.rockets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.room.FavoritedRockets
import com.example.rocketappdemo.databinding.ItemRocketsBinding
import com.example.rocketappdemo.utils.toJson
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.properties.Delegates


@InternalCoroutinesApi
class RocketsAdapter(
    private var rocketList: ArrayList<RocketsModelItem>,
    private val listener: Listener
) : RecyclerView.Adapter<RocketsAdapter.MyViewHolder>() {


    interface Listener {
        fun onItemClick(id: String)
        fun insertRoom(rocketItem: FavoritedRockets)
        fun deleteFromRoom(id: String)
    }

    class MyViewHolder(val binding: ItemRocketsBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRocketsBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var rocket = rocketList[position]
        holder.binding.textName.text = rocket.name
        Glide.with(holder.itemView.context).load(rocket.flickr_images[0])
            .into(holder.binding.imgRocket)
        if (rocket.isFavorite) {
            rocket.isFavorite = true
            holder.binding.favBtn.setImageResource(R.drawable.favorited_24)
        } else {
            rocket.isFavorite = false
            holder.binding.favBtn.setImageResource(R.drawable.favorite_border_24)
        }
        holder.binding.favBtn.setOnClickListener {

            if (!rocket.isFavorite) {

                rocket.isFavorite = true
                holder.binding.favBtn.setImageResource(R.drawable.favorited_24)

                val favRocket = FavoritedRockets(
                    0,
                    rocket.id,
                    rocket.name,
                    rocket.description,
                    rocket.flickr_images.toJson(),
                    rocket.isFavorite
                )
                listener.insertRoom(favRocket)

            } else {
                holder.binding.favBtn.setImageResource(R.drawable.favorite_border_24)
                rocket.isFavorite = false
                listener.deleteFromRoom(rocket.id)

            }
        }


        holder.binding.root.setOnClickListener {
            listener.onItemClick(rocket.id)
        }

    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRocketList(_rocketList: ArrayList<RocketsModelItem>) {
        rocketList = _rocketList
        notifyDataSetChanged()
    }

    fun setItemFavoriteStatus(itemId: String, status: Boolean) {
        rocketList.firstOrNull {
            it.id == itemId
        }?.isFavorite = status
        notifyDataSetChanged()
    }
}