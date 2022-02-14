package com.example.tokenizedretrofit.models

import com.example.tokenizedretrofit.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String):
            Call<LoginResponse>
//   fun login(@Body request: LoginRequest): Call<LoginResponse>

}