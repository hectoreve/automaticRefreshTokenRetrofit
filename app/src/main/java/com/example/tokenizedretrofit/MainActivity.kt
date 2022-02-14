package com.example.tokenizedretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import com.example.tokenizedretrofit.models.LoginResponse
import com.example.tokenizedretrofit.providers.ApiClient
import com.example.tokenizedretrofit.providers.PreferencesProvider
import com.example.tokenizedretrofit.providers.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(application) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var login = PreferencesProvider.string(this, "Login")!!
        var password = PreferencesProvider.string(this, "Password")!!

        findViewById<Button>(R.id.save_login).setOnClickListener{
            saveLogin()
            login = PreferencesProvider.string(this, "Login")!!
            password = PreferencesProvider.string(this, "Password")!!
        }

        findViewById<Button>(R.id.refresh_response).setOnClickListener{
            viewModel.refreshResponse(login, password)
        }
    }

    private fun saveLogin() {
        PreferencesProvider.set(this, "Login", "DANIELM 054")
        PreferencesProvider.set(this, "Password", "e10adc3949ba59abbe56e057f20f883e")
    }

}
