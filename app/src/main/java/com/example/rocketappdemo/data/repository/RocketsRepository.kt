package com.example.rocketappdemo.data.repository

import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.data.model.rockets.RocketsModel
import com.example.rocketappdemo.data.model.rockets.RocketsModelItem
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModel
import com.example.rocketappdemo.data.model.upcoming_launches.UpcomingLaunchesModelItem
import com.example.rocketappdemo.data.remote.ServiceAPI
import retrofit2.Response
import javax.inject.Inject

interface RocketsRepository  {

    suspend fun getRockets(): Output<RocketsModel>

    suspend fun getRocketDetail(id :String) : Output<RocketsModelItem>

    suspend fun getLaunchs(): Output<UpcomingLaunchesModel>

    suspend fun getLaunchDetail(id:String): Output<UpcomingLaunchesModelItem>




}