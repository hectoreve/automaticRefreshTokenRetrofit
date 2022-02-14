package com.example.tokenizedretrofit.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status")
    var statusCode: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("expiredTimeToken")
    var expTime: String,
)