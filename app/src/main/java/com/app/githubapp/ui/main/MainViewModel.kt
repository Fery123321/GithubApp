package com.app.githubapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubapp.data.ApiClient
import com.app.githubapp.data.model.user.User
import com.app.githubapp.data.model.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<MutableList<User>>()

    fun setSearchUser(query: String) {
        ApiClient.instance.getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Api Failed", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.value = response.body()?.items
                    }
                }
            })
    }

    fun getSearchUsers(): LiveData<MutableList<User>> = listUsers
}