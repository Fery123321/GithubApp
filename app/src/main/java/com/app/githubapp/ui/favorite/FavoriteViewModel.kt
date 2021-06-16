package com.app.githubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.githubapp.data.local.FavoriteUser
import com.app.githubapp.data.local.FavoriteUserDao
import com.app.githubapp.data.local.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var favoriteUserDatabase: FavoriteUserDatabase?
    private var favoriteUserDao: FavoriteUserDao?

    init {
        favoriteUserDatabase = FavoriteUserDatabase.getInstance(application)
        favoriteUserDao = favoriteUserDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<MutableList<FavoriteUser>>? {
        return favoriteUserDao?.getFavoriteUser()
    }
}