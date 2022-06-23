package com.example.rocketappdemo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [FavoritedRockets::class], version = 5, exportSchema = false)
abstract class FavoritedRocketsDb :RoomDatabase(){

    abstract fun rocketDao():FavoritedRocketsDao

    companion object{

        @Volatile
        private var INSTANCE :FavoritedRocketsDb? = null
        @InternalCoroutinesApi
        fun getDatabase(context: Context):FavoritedRocketsDb{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }
           synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritedRocketsDb::class.java,
                    "favorited_database"
                )

                    .build()
                INSTANCE = instance
                return instance
            }
        }






    }
}