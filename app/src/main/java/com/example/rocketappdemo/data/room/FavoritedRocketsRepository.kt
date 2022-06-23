package com.example.rocketappdemo.data.room

import androidx.lifecycle.LiveData

class FavoritedRocketsRepository(private val rocketDao:FavoritedRocketsDao) {

    val readAllData: LiveData<List<FavoritedRockets>> = rocketDao.readAllData()
    suspend fun addtoFav(favrocket: FavoritedRockets){
        rocketDao.addtoFav(favrocket)
    }
    suspend fun deleteFromRoom(rocketId: String){
       rocketDao.deleteFromRoom(rocketId)
    }
}
