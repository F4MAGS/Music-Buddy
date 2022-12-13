package com.example.music_buddy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FriendsAdapter(val context: Context, val friends: List<Data>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_friendicon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends.get(position)
        holder.bind(friend)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivFriendIcon: ImageView = itemView.findViewById(R.id.iv_friendIcon)

        fun bind(data: Data){
            Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivFriendIcon)
        }

    }
}