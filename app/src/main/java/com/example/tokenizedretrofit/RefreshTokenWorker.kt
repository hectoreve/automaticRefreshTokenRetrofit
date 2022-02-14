package com.example.tokenizedretrofit

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tokenizedretrofit.models.LoginResponse
import com.example.tokenizedretrofit.providers.ApiClient
import com.example.tokenizedretrofit.providers.PreferencesProvider
import com.example.tokenizedretrofit.providers.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.suspendCoroutine

class RefreshTokenWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    private val login = PreferencesProvider.string(ctx, "Login")!!
    private val password = PreferencesProvider.string(ctx, "Password")!!
    var token = PreferencesProvider.string(ctx, "user_token")
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    override suspend fun doWork(): Result {
        return suspendCoroutine {
            try {
                val call = apiClient.getApiService()
                call.login(login, password)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            val loginResponse = response.body()
                            if (loginResponse?.statusCode == 201) {
                                sessionManager.saveAuthToken(loginResponse.token)
                                Result.success()
                            } else {
                                Log.d(
                                    TAG,
                                    "Error at login in ${response.body()} ${response.errorBody()}"
                                )
                                Result.failure()
                            }
                        }
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Log.d(TAG, "Error at retrofit connection ${t.message.toString()}")
                            Result.failure()
                        }

                    })

            } catch (exception: Exception) {
                Result.failure()
            }
        }
    }
}