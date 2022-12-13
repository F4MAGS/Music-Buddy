package com.example.music_buddy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_buddy.ui.fragments.messageFragment


class MessageAdapter(val context: Context, val currentUser: Data, val currentFriend: Data, val messages: List<Message>, val activity: FragmentActivity) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewLeft = LayoutInflater.from(context).inflate(R.layout.item_messageleft, parent, false)
//        val viewRight = LayoutInflater.from(context).inflate(R.layout.item_messageright, parent, false)
        return ViewHolder(viewLeft)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        if(message.getUserOne() == currentUser.getEmail()) {
            holder.bind(currentUser, message, currentUser)
        }
        else{
            holder.bind(currentFriend, message, currentUser)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvmessageMessage: TextView
        val tvmessageName: TextView
        val ivmessagePicture: ImageView
        val pictureBorder: CardView
        val tvmessageTime: TextView

        val tvmessageMessageRight: TextView
        val tvmessageNameRight: TextView
        val ivmessagePictureRight: ImageView
        val pictureBorderRight: CardView
        val tvmessageTimeRight: TextView

        init{
            tvmessageMessage = itemView.findViewById(R.id.tv_messageMessage)
            tvmessageName = itemView.findViewById(R.id.tv_messageName)
            ivmessagePicture = itemView.findViewById(R.id.iv_messagePicture)
            pictureBorder = itemView.findViewById(R.id.pictureBorder)
            tvmessageTime = itemView.findViewById(R.id.tv_messagetime)


            tvmessageMessageRight = itemView.findViewById(R.id.tv_messageMessageRight)
            tvmessageNameRight = itemView.findViewById(R.id.tv_messageNameRight)
            ivmessagePictureRight = itemView.findViewById(R.id.iv_messagePictureRight)
            pictureBorderRight = itemView.findViewById(R.id.pictureBorderRight)
            tvmessageTimeRight = itemView.findViewById(R.id.tv_messagetimeRight)

        }

        fun bind(data: Data, message: Message, currentUser: Data){

            if(message.getUserOne() != currentUser.getEmail()) {
                pictureBorder.visibility = View.VISIBLE
                tvmessageMessage.visibility = View.VISIBLE
                tvmessageName.visibility = View.VISIBLE
                tvmessageTime.visibility = View.VISIBLE

                tvmessageName.text = data.getUsername()
                tvmessageMessage.text = message.getMessage()
                Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivmessagePicture)
                tvmessageTime.text = message.getFormattedTimeStamp()

                pictureBorderRight.visibility = View.GONE
                tvmessageMessageRight.visibility = View.GONE
                tvmessageNameRight.visibility = View.GONE
                tvmessageTimeRight.visibility = View.GONE
            }
            else{
                pictureBorderRight.visibility = View.VISIBLE
                tvmessageMessageRight.visibility = View.VISIBLE
                tvmessageNameRight.visibility = View.VISIBLE
                tvmessageTimeRight.visibility = View.VISIBLE


                tvmessageNameRight.text = data.getUsername()
                tvmessageMessageRight.text = message.getMessage()
                Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivmessagePictureRight)
                tvmessageTimeRight.text = message.getFormattedTimeStamp()

                pictureBorder.visibility = View.GONE
                tvmessageMessage.visibility = View.GONE
                tvmessageName.visibility = View.GONE
                tvmessageTime.visibility = View.GONE

            }
        }

    }


}


