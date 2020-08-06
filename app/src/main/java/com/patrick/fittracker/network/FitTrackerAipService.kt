package com.patrick.fittracker.network

import com.patrick.fittracker.BuildConfig
import com.patrick.fittracker.data.GymLocationListResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val HOST_NAME = "maps.googleapis.com/maps"
private const val API_VERSION = "place"
private const val BASE_URL = "https://$HOST_NAME/api/$API_VERSION/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = when (BuildConfig.LOGGER_VISIABLE) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface FitTrackerAipService {
    @GET("nearbysearch/json")
    suspend fun getLocationList(@Query("key") key: String? = null , @Query("location") location: String? = null, @Query("radius") radius: Int? = 800, @Query("language") language: String? = "zh-TW" , @Query("keyword") keyword: String? = "健身"): GymLocationListResult
}

object FitTrackerApi {
    val retrofitService : FitTrackerAipService by lazy { retrofit.create(FitTrackerAipService::class.java) }
}