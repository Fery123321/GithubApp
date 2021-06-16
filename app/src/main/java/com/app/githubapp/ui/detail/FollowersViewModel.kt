package com.app.githubapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubapp.data.ApiClient
import com.app.githubapp.data.model.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<MutableList<User>>()

    fun setListFollowers(username: String) {
        ApiClient.instance.getFollowers(username)
            .enqueue(object : Callback<MutableList<User>> {
                override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                    Log.e("Api Client Failed", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<MutableList<User>>,
                    response: Response<MutableList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.value = response.body()
                    }
                }

            })
    }

    fun getListFollowers(): LiveData<MutableList<User>> = listFollowers
}