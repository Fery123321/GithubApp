package com.app.githubapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubapp.data.model.user.User
import com.app.githubapp.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolders>() {
    private val list = mutableListOf<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: MutableList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolders(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)

                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders =
        ViewHolders(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.onBind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClick(user: User)
    }

}

