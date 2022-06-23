package com.example.rocketappdemo.data.model.rockets



data class RocketsModelItem(
    val id: String,
    var name: String,
    var description: String,
    val flickr_images: List<String>,
    var isFavorite: Boolean
)








