package tn.request.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import tn.request.network.model.LoginRequest
import tn.request.network.model.LoginResponse

interface BackendService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    companion object {
        private const val BASE_URL = "http://192.168.0.17:9555/api/v1/"

        operator fun invoke(
            baseUrl: String = BASE_URL
        ): BackendService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendService::class.java)
        }
    }
}