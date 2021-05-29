package fr.test200.findme.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.test200.findme.Itinerary
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

private const val BASE_URL = "https://maps.googleapis.com/api"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GoogleMapApiService {

    @GET("directions/json")
    suspend fun getItinerary(
        @QueryMap options: Map<String, String>
    ): Response<String>?
}

object GoogleMapApi {
    val mapService : GoogleMapApiService by lazy {
        retrofit.create(GoogleMapApiService::class.java)
    }
}