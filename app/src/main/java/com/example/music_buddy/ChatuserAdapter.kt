package com.example.music_buddy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChatuserAdapter(val context: Context, val friends: List<Data>) : RecyclerView.Adapter<ChatuserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chatuser, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends.get(position)
        holder.bind(friend)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvChatlistName: TextView
        val tvChatlistMessage: TextView
        val ivChatlistPicture: ImageView

        init{
            tvChatlistName = itemView.findViewById(R.id.tvChatlistName)
            tvChatlistMessage = itemView.findViewById(R.id.tvChatlistMessage)
            ivChatlistPicture = itemView.findViewById(R.id.ivChatlistPicture)
        }

        fun bind(data: Data ){
            tvChatlistName.text = data.getUsername()
//            tvChatlistMessage.text = message.getMessage()

            Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivChatlistPicture)
        }


    }

}