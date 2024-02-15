package com.jetpackcompose.actors.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    fun provideApi(builder: Retrofit.Builder): CharacterAPI {
        return builder
            .build()
            .create(CharacterAPI::class.java)
    }

    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
    }
}