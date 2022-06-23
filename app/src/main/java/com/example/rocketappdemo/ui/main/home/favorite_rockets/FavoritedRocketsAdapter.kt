package com.example.rocketappdemo.ui.main.home.favorite_rockets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.room.FavoritedRockets
import com.example.rocketappdemo.utils.fromJson
import kotlinx.android.synthetic.main.item_rockets.view.*

class FavoritedRocketsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<FavoritedRocketsAdapter.MyViewHolder>() {
    private var favoritedRocketList = emptyList<FavoritedRockets>()

    interface Listener {
        fun onItemClick(id: String)
        fun insertRoom(rocketItem: FavoritedRockets)
        fun deleteFromRoom(id: String)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rockets, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = favoritedRocketList[position]
        holder.itemView.textName.text = currentItem.name
        holder.itemView.fav_btn.setImageResource(R.drawable.favorited_24)
        Glide.with(holder.itemView.context).load(currentItem.flickr_images.fromJson()[0])
            .into(holder.itemView.imgRocket)

        if (currentItem.isFavorite) {
            currentItem.isFavorite = true
            holder.itemView.fav_btn.setImageResource(R.drawable.favorited_24)
        } else {
            currentItem.isFavorite = false
            holder.itemView.fav_btn.setImageResource(R.drawable.favorite_border_24)
        }
        holder.itemView.fav_btn.setOnClickListener {

            if (!currentItem.isFavorite) {

                currentItem.isFavorite = true
                holder.itemView.fav_btn.setImageResource(R.drawable.favorited_24)

                val favRocket = FavoritedRockets(
                    0,
                    currentItem.id,
                    currentItem.name,
                    currentItem.description,
                    currentItem.flickr_images,
                    currentItem.isFavorite
                )
                listener.insertRoom(favRocket)

            } else {
                holder.itemView.fav_btn.setImageResource(R.drawable.favorite_border_24)
                currentItem.isFavorite = false
                listener.deleteFromRoom(currentItem.id)
            }
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentItem.id)
        }
    }


    override fun getItemCount(): Int {
        return favoritedRocketList.size
    }

    fun setData(rocket: List<FavoritedRockets>) {
        this.favoritedRocketList = rocket
        notifyDataSetChanged()
    }

    fun setItemFavoriteStatus(itemId: String, status: Boolean) {
        favoritedRocketList.firstOrNull {
            it.id == itemId
        }?.isFavorite = status
        notifyDataSetChanged()
    }
}