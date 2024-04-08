package com.john.nycschools.data.remote

import android.util.Log
import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Interface that calls the apis to retrieve School and SchoolDetails through remote network
 */

interface NycSchoolService {

    @GET("/resource/s3k6-pzi2.json")
    suspend fun getAllSchools(): List<School>


    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchoolsByBoro(
        @Query("boro") boro: String
    ): List<School>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSchoolDetails(
        @Query("dbn") dbn: String
    ): List<SchoolDetails>?


    companion object {
        private const val BASE_URL = "https://data.cityofnewyork.us/"
        fun create(): NycSchoolService {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(logger)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NycSchoolService::class.java)

        }
    }
}