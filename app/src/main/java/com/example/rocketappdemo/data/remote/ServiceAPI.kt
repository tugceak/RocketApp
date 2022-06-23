package com.example.rocketappdemo.data.remote

import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.rockets.RocketsModel
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModel
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModelItem
import com.example.rocketappdemo.data.repository.RocketsRepository
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceAPI{

    //Get All Rockets
    @GET("rockets")
  suspend fun getRockets(): Response<RocketsModel>

    //Get Rocket Detail
    @GET("rockets/{id}")
   suspend fun getRocketDetail(
        @Path("id") id: String
    ): Response<RocketsModelItem>

    //Get Upcoming Launches
    @GET("launches/upcoming")
   suspend fun getUpcomingLaunches(): Response<UpcomingLaunchesModel>

    //Get Launch Detail
    @GET("launches/{id}")
     suspend fun getLaunchDetail(
        @Path("id") id: String
    ): Response<UpcomingLaunchesModelItem>

}