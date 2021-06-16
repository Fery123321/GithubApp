package com.app.githubapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.githubapp.data.ApiClient
import com.app.githubapp.data.local.FavoriteUser
import com.app.githubapp.data.local.FavoriteUserDao
import com.app.githubapp.data.local.FavoriteUserDatabase
import com.app.githubapp.data.model.detailuser.UserDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()

    private var favoriteUserDatabase: FavoriteUserDatabase?
    private var favoriteUserDao: FavoriteUserDao?

    init {
        favoriteUserDatabase = FavoriteUserDatabase.getInstance(application)
        favoriteUserDao = favoriteUserDatabase?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        ApiClient.instance.getDetailUsers(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.e("Api Failure", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.value = response.body()
                    }
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> = user

    fun addFavorite(username: String, id: Int, avatarUrl: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var favoriteUser = FavoriteUser(username, id, avatarUrl)
            favoriteUserDao?.addFavorite(favoriteUser)
        }
    }

    suspend fun checkUser(id: Int) = favoriteUserDao?.checkUser(id)

    fun deleteFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            favoriteUserDao?.deleteFavorite(id)
        }
    }
}