package com.example.music_buddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_buddy.databinding.ActivityHomeBinding
import com.example.music_buddy.ui.fragments.messageFragment
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


class ChatuserAdapter(val context: Context, val currentUser: List<Data>, val friends: List<Data>, private val activity: FragmentActivity) : RecyclerView.Adapter<ChatuserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chatuser, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends[position]
        holder.bind(friend, currentUser[0])

        holder.tvChatlistName.setOnClickListener {
//            Toast.makeText(context, "this is number:"+ friends[position], Toast.LENGTH_SHORT).show()
//            Toast.makeText(context, "this is extra:"+ friends[position].getEmail(), Toast.LENGTH_SHORT).show()
//
//            Toast.makeText(context, "this is number:"+ currentUser[0], Toast.LENGTH_SHORT).show()
//            Toast.makeText(context, "this is extra:"+ currentUser[0].getEmail(), Toast.LENGTH_SHORT).show()

            val messageFragment = messageFragment()
            val bundle = Bundle()
            bundle.putParcelable("FRIEND_EXTRA" ,  friends[position])
            bundle.putParcelable("USER_EXTRA" ,  currentUser[0])
            messageFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.flContainer, messageFragment).addToBackStack("chatlistFragment()").commit()
        }

        holder.tvChatlistMessage.setOnClickListener {
            val messageFragment = messageFragment()
            val bundle = Bundle()
            bundle.putParcelable("FRIEND_EXTRA" ,  friends[position])
            bundle.putParcelable("USER_EXTRA" ,  currentUser[0])
            messageFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.flContainer, messageFragment).addToBackStack("chatlistFragment()").commit()
        }

        holder.ivChatlistPicture.setOnClickListener {
            val messageFragment = messageFragment()
            val bundle = Bundle()
            bundle.putParcelable("FRIEND_EXTRA" ,  friends[position])
            bundle.putParcelable("USER_EXTRA" ,  currentUser[0])
            messageFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.flContainer, messageFragment).addToBackStack("chatlistFragment()").commit()
        }

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

        fun bind(data: Data, currentUser: Data){
            tvChatlistName.text = data.getUsername()

            val query: ParseQuery<Message> = ParseQuery.getQuery(Message::class.java)
            query.whereEqualTo(Message.KEY_USERTWO, currentUser.getEmail())
            query.whereEqualTo(Message.KEY_USERONE, data.getEmail())
            query.addDescendingOrder("createdAt")

            query.findInBackground(object : FindCallback<Message> {
                override fun done(messageList: MutableList<Message>?, e: ParseException?) {
                    if (e != null) {
                        Log.e(messageFragment.TAG, "Error fetching message data")
                    } else {
                        Log.i(messageFragment.TAG, "Success fetching message data")
                        if (messageList != null && messageList.isNotEmpty()) {
                            var message = messageList.first()
                            tvChatlistMessage.text = message.getMessage()
                        }
                    }
                }
            })

            Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivChatlistPicture)

        }

    }

}