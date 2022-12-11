package com.example.music_buddy

import android.content.Context
import android.provider.MediaStore.Audio.Artists
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject

class HomeAdapter(val context: Context, val users: List<Data>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = users.get(position)
        holder.bind(friend)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Username: TextView
        val bio: TextView
        val profilePic: ImageView

        val artist1: TextView
        val artist2: TextView
        val artist3: TextView
        val artistPic1: ImageView
        val artistPic2: ImageView
        val artistPic3: ImageView

        init{
            Username = itemView.findViewById(R.id.Username)
            bio = itemView.findViewById(R.id.bio)
            profilePic = itemView.findViewById(R.id.profilePic)

            artist1 = itemView.findViewById(R.id.artist_1)
            artist2 = itemView.findViewById(R.id.artist_2)
            artist3 = itemView.findViewById(R.id.artist_3)
            artistPic1 = itemView.findViewById(R.id.artist_1_img)
            artistPic2 = itemView.findViewById(R.id.artist_2_img)
            artistPic3 = itemView.findViewById(R.id.artist_3_img)


        }

        fun bind(data: Data ){
            Username.text = data.getUsername()
            bio.text = data.getDescription()

            artist1.text = data.getTopArtistsObject()?.getJSONObject("name").toString()

            Glide.with(itemView.context).load(data.getProfilePic()?.url).into(profilePic)
        }


    }

}
