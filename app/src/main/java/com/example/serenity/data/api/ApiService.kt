package com.example.serenity.data.api

import com.example.serenity.data.music.AudioTrack
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("0229c3d89b5cd92795bd")
    suspend fun getPlaylist(): List<AudioTrack>

    companion object {
        // Alamat server utama (Base URL)
        private const val BASE_URL = "https://api.npoint.io/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}