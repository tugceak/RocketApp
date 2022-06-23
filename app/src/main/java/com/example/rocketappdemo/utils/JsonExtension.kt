package com.example.rocketappdemo.utils

import com.google.gson.Gson


   fun List<String>.toJson():String{
      return  Gson().toJson(this)
    }
  fun String.fromJson():List<String>{
      return Gson().fromJson(this, Array<String>::class.java).toList()
  }