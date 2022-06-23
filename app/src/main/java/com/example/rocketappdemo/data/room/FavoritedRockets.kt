package com.example.rocketappdemo.data.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson


@Entity(tableName = "favorited_table")
data class FavoritedRockets(
    @PrimaryKey(autoGenerate = true)
    val primary: Int,
    val id: String,
    val name: String,
    val description: String,
    val flickr_images: String,
    var isFavorite: Boolean

    )

