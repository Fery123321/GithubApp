package com.app.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubapp.R
import com.app.githubapp.data.model.user.User
import com.app.githubapp.databinding.ActivityMainBinding
import com.app.githubapp.ui.detail.DetailUserActivity
import com.app.githubapp.ui.favorite.FavoriteActivity
import com.app.githubapp.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.onItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(user: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                    .putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    .putExtra(DetailUserActivity.EXTRA_ID, user.id)
                    .putExtra(DetailUserActivity.EXTRA_AVATAR_URL, user.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            ivSearch.setOnClickListener {
                searchUser()
            }
        }

        viewModel.getSearchUsers().observe(this, Observer {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUser() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) {
                return showLoading(true)
            }
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuFav -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.menuSetting -> startActivity(Intent(this, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}