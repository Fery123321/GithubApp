package com.app.githubapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    @PrimaryKey(autoGenerate = true) val id: Int,
    val avatar_url: String
) : Serializable