package com.app.githubapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubapp.data.local.FavoriteUser
import com.app.githubapp.data.model.user.User
import com.app.githubapp.databinding.ActivityFavoriteBinding
import com.app.githubapp.ui.detail.DetailUserActivity
import com.app.githubapp.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.onItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(user: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                    .putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    .putExtra(DetailUserActivity.EXTRA_ID, user.id)
                    .putExtra(DetailUserActivity.EXTRA_AVATAR_URL, user.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recyclerView.adapter = adapter

            viewModel.getFavoriteUser()?.observe(this@FavoriteActivity, Observer {
                if (it != null) {
                    val list = mapList(it)
                    adapter.setList(list)
                }
            })
        }
    }

    private fun mapList(user: MutableList<FavoriteUser>?): MutableList<User> {
        val list = mutableListOf<User>()
        for (i in user!!) {
            val userMap = User(i.login, i.id, i.avatar_url)
            list.add(userMap)
        }
        return list
    }

}