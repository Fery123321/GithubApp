package com.app.githubapp.data

import com.app.githubapp.data.model.detailuser.UserDetailResponse
import com.app.githubapp.data.model.user.User
import com.app.githubapp.data.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<MutableList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<MutableList<User>>
}