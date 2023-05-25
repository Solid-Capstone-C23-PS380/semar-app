package com.solidcapstone.semar.data.remote.retrofit

import android.content.Context
import com.solidcapstone.semar.data.remote.response.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://wayang-prediction-mrlpwmp4cq-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}