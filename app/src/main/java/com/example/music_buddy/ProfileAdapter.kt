package com.example.music_buddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProfileAdapter(val context: Context, val profiles: List<Data>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profiles.get(position)
        holder.bind(profile)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvUsername: TextView
        val tvDescription: TextView
        val ivProfilePic: ImageView
        val  artist1: TextView
        val  artist2: TextView
        val  artist3: TextView


        init{
            tvUsername = itemView.findViewById(R.id.Username)
            tvDescription = itemView.findViewById(R.id.bio)
            ivProfilePic = itemView.findViewById(R.id.profilePic)

            artist1 = itemView.findViewById(R.id.artist_1)
            artist2 = itemView.findViewById(R.id.artist_2)
            artist3 = itemView.findViewById(R.id.artist_3)
        }

        fun bind(data: Data){
            tvUsername.text = data.getUsername()
            tvDescription.text = data.getDescription()

//
//            artist2.text = data.getTopArtistsObject()?.getJSONObject("name").toString()
//            artist3.text = data.getTopArtistsObject()?.getJSONObject("name").toString()
            Glide.with(itemView.context).load(data.getProfilePic()?.url).into(ivProfilePic)
        }

    }


}