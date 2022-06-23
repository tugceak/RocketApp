package com.example.rocketappdemo.ui.main.home.favorite_rockets

import android.app.Application
import androidx.lifecycle.*
import com.example.rocketappdemo.data.room.FavoritedRockets
import com.example.rocketappdemo.data.room.FavoritedRocketsDb
import com.example.rocketappdemo.data.room.FavoritedRocketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class FavRocketsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritedRocketsRepository
    val readAllData: LiveData<List<FavoritedRockets>>
    val favoriteStatus: MutableLiveData<Pair<String, Boolean>> by lazy {
        MutableLiveData<Pair<String, Boolean>>()
    }

    init {
        val rocketDao = FavoritedRocketsDb.getDatabase(application).rocketDao()
        repository = FavoritedRocketsRepository(rocketDao)
        readAllData = repository.readAllData
    }

    fun addtoFav(rocket: FavoritedRockets) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addtoFav(rocket)
        }
    }

    fun deleteFromRoom(rocketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromRoom(rocketId)
        }
    }


}