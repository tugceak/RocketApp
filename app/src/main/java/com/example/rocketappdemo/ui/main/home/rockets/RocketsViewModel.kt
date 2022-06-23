package com.example.rocketappdemo.ui.main.home.rockets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.rockets.RocketsModel
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.remote.ServiceAPI
import com.example.rocketappdemo.data.repository.RocketsRepository
import com.example.rocketappdemo.data.repository.RocketsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(private val rocketsRepository: RocketsRepository) :
    ViewModel() {
    val rocketsLivedata: MutableLiveData<Output<RocketsModel?>> by lazy {
        MutableLiveData<Output<RocketsModel?>>()
    }

    suspend fun getRockets() {
        rocketsLivedata.postValue(Output.loading(null))
        val response = rocketsRepository.getRockets()
        rocketsLivedata.postValue(response)
    }


}
