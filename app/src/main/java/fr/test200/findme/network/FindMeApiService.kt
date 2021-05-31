package fr.test200.findme.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.test200.findme.dataClass.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://172.17.192.49:8081"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FindMeApiService {

    @GET("categories/")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("places/category/{name}")
    suspend fun getAllPlacesByCategory(@Path("name") category: String): Response<List<Place>>

    @GET("places/users/{id}")
    suspend fun getPlaceList(@Path("id") id: Int, @Query("sort") sort: String? = null): Response<List<Place>>

    @GET("places/{id}")
    suspend fun getPlace(@Path("id") id: Int): Response<List<Place>>

    @GET("api/user/find/{name}")
    suspend fun getPlaceByName(@Path("name") username: String): Response<Place>

    @GET("/users/1")
    suspend fun getUserById() : Response<List<User>>

    @FormUrlEncoded
    @POST("/userItinerary/visited")
    suspend fun addUserItinerary(@Field("placeId") placeId: Int, @Field("userId") userId: Int): Response<List<UserItineraryResponse>>
}

object FindMeApi {
    val APIService : FindMeApiService by lazy {
        retrofit.create(FindMeApiService::class.java) }
}