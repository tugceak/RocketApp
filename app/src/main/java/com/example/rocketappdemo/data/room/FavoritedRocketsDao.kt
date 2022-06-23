package com.example.rocketappdemo.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface  FavoritedRocketsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addtoFav(rocket:FavoritedRockets)

    @Query("SELECT * FROM favorited_table ")
    fun readAllData() : LiveData<List<FavoritedRockets>>

    @Query("DELETE FROM FAVORITED_TABLE where id= :id")
    suspend fun deleteFromRoom(id : String)

    @Query("SELECT * FROM favorited_table where id= :id")
    suspend fun getBasketItem(id : String) : FavoritedRockets?
}