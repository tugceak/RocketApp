package com.example.rocketappdemo.utils

import com.example.rocketappdemo.data.model.ApiError
import com.example.rocketappdemo.data.model.Output
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

object RetrofitUtils {
    suspend fun <T> getResponse(
        retrofit: Retrofit,
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Output<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Output.success(result.body())
            } else {
                val errorResponse = parseError(retrofit,result)
                Output.error(errorResponse?.statusMessage ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Output.error("Unknown Error ${e.localizedMessage}", null)
        }
    }


    private fun parseError(retrofit:Retrofit, response: Response<*>): ApiError? {
        val converter =
            retrofit.responseBodyConverter<ApiError>(ApiError::class.java, arrayOfNulls(0))
        return try {
            response.errorBody()?.let {
                converter.convert(it)
            }
        } catch (e: IOException) {
            ApiError()
        }
    }
}