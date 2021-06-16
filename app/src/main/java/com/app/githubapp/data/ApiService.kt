package com.app.githubapp.data

import com.app.githubapp.data.model.detailuser.UserDetailResponse
import com.app.githubapp.data.model.user.User
import com.app.githubapp.data.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<MutableList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<MutableList<User>>
}