package com.example.rocketappdemo.ui.main.home.upcoming_launches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModel
import com.example.rocketappdemo.data.repository.RocketsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingLaunchesViewModel @Inject constructor(private val rocketsRepository: RocketsRepository) :
    ViewModel() {

    val upcLivedata: MutableLiveData<Output<UpcomingLaunchesModel>> by lazy {
        MutableLiveData<Output<UpcomingLaunchesModel>>()
    }

    suspend fun getLaunchs() {
        upcLivedata.postValue(Output.loading(null))
        val response = rocketsRepository.getLaunchs()
        upcLivedata.postValue(response)
    }

}