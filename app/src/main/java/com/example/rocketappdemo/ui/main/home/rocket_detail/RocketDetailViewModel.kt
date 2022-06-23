package com.example.rocketappdemo.ui.main.home.rocket_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.repository.RocketsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(private val rocketsRepository: RocketsRepository) :
    ViewModel() {
    val rocketDetailLivedata: MutableLiveData<Output<RocketsModelItem>> by lazy {
        MutableLiveData<Output<RocketsModelItem>>()
    }

    suspend fun getRocketDetails(id: String) {
        rocketDetailLivedata.postValue(Output.loading(null))
        val response = rocketsRepository.getRocketDetail(id)
        rocketDetailLivedata.postValue(response)


    }


}