package com.example.tokenizedretrofit

import android.app.Application
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.tokenizedretrofit.models.LoginResponse
import com.example.tokenizedretrofit.providers.ApiClient
import com.example.tokenizedretrofit.providers.PreferencesProvider
import com.example.tokenizedretrofit.providers.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val application: Application): ViewModel() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(Constants.TAG_REFRESH)



    fun refreshResponse(login: String, password: String){
        sessionManager = SessionManager(application.applicationContext)
        apiClient = ApiClient()
//        val credentials = LoginRequest("DANIELM 054", "e10adc3949ba59abbe56e057f20f883e")
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
                    } else {
                        Log.d(TAG, "Error at login in ${response.body()} ${response.errorBody()}")
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(TAG, "Error at retrofit connection ${t.message.toString()}")
                }

            })
    }

}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
