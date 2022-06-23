package com.example.rocketappdemo.ui.main.home.launch_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModelItem
import com.example.rocketappdemo.data.repository.RocketsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(private val rocketsRepository: RocketsRepository) :
    ViewModel() {
    val launchDetailLivedata: MutableLiveData<Output<UpcomingLaunchesModelItem>> by lazy {
        MutableLiveData<Output<UpcomingLaunchesModelItem>>()
    }

    suspend fun getLaunchDetails(id: String) {
        launchDetailLivedata.postValue(Output.loading(null))
        val response = rocketsRepository.getLaunchDetail(id)
        launchDetailLivedata.postValue(response)


    }

}