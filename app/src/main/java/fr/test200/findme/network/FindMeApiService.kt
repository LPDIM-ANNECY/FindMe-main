package fr.test200.findme.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.test200.findme.Place
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://0.0.0.0:8081"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FindMeApiService {

    @GET("places")
    suspend fun getPlaceList(@Query("sort") sort: String? = null): Response<List<Place>>

    @GET("api/user/find/{name}")
    suspend fun getPlaceByName(@Path("name") username: String): Response<Place>
}

object FindMeApi {
    val userService : FindMeApiService by lazy {
        retrofit.create(FindMeApiService::class.java) }
}