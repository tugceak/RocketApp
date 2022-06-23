package com.example.rocketappdemo.data.repository

import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.rockets.RocketsModel
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModel
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModelItem
import com.example.rocketappdemo.data.remote.ServiceAPI
import com.example.rocketappdemo.utils.RetrofitUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RocketsRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val serviceApi: ServiceAPI) :RocketsRepository{

    override suspend fun getRockets(): Output<RocketsModel> {
        val response = serviceApi.getRockets()
        return RetrofitUtils.getResponse(retrofit,{response},"Error")
    }

    override suspend fun getRocketDetail(id :String) : Output<RocketsModelItem> {
        return  RetrofitUtils.getResponse(retrofit,{serviceApi.getRocketDetail(id)},"Error")
    }

    override suspend fun getLaunchs(): Output<UpcomingLaunchesModel> {
        return RetrofitUtils.getResponse(retrofit,{serviceApi.getUpcomingLaunches()},"Error")
    }

    override suspend fun getLaunchDetail(id:String): Output<UpcomingLaunchesModelItem> {
        return RetrofitUtils.getResponse(retrofit,{serviceApi.getLaunchDetail(id)},"Error")
    }

}