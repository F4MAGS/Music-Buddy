package com.example.music_buddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.music_buddy.databinding.ActivityHomeBinding
import com.example.music_buddy.ui.fragments.homeFragment
import com.example.music_buddy.ui.fragments.profileFragment
import com.example.music_buddy.ui.fragments.chatlistFragment
import com.example.music_buddy.ui.fragments.friendsFragment
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.*

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment())

        // Used for UI Changes in navigation bars
        // Profile, Home, Chat, Friends
        var prevStatus = "Home"
        //Profile Press
        binding.rlTopBar.ivProfile.setOnClickListener{
            replaceFragment(profileFragment())
            previousFragment(prevStatus)
            prevStatus = "Profile"
            currentFragment(prevStatus)
        }

        //Home Press
        binding.rlBottomBar.ivHome.setOnClickListener{
            replaceFragment(homeFragment())
            previousFragment(prevStatus)
            prevStatus = "Home"
            currentFragment(prevStatus)
        }

        //Profile Press
        binding.rlBottomBar.ivFriends.setOnClickListener{
            replaceFragment(friendsFragment())
            previousFragment(prevStatus)
            prevStatus = "Friends"
            currentFragment(prevStatus)
        }

        //Profile Press
        binding.rlBottomBar.ivChatList.setOnClickListener{
            replaceFragment(chatlistFragment())
            previousFragment(prevStatus)
            prevStatus = "Chat"
            currentFragment(prevStatus)
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContainer, fragment)
        fragmentTransaction.commit()

    }

    private fun previousFragment(previous: String) {
        val homeIcon: ImageView = findViewById(R.id.iv_home)
        val friendsIcon: ImageView = findViewById(R.id.iv_friends)
        val chatIcon: ImageView = findViewById(R.id.iv_chatList)
        val profileIcon: ImageView = findViewById(R.id.iv_profile)

        if (previous == "Home") {
            homeIcon.setImageResource(R.drawable.homeicon);
            homeIcon.isClickable = true
        }
        else if (previous == "Friends") {
            friendsIcon.setImageResource(R.drawable.friendsicon);
            friendsIcon.isClickable = true
        }
        else if (previous == "Chat") {
            chatIcon.setImageResource(R.drawable.chaticon);
            chatIcon.isClickable = true
        }
        else if (previous == "Profile") {
            profileIcon.visibility = View.VISIBLE // show image view
        }
    }

    private fun currentFragment(current: String) {
        val homeIcon: ImageView = findViewById(R.id.iv_home)
        val friendsIcon: ImageView = findViewById(R.id.iv_friends)
        val chatIcon: ImageView = findViewById(R.id.iv_chatList)
        val profileIcon: ImageView = findViewById(R.id.iv_profile)

        if (current == "Home") {
            homeIcon.setImageResource(R.drawable.selectedhomeicon);
            homeIcon.isClickable = false
        }
        else if (current == "Friends") {
            friendsIcon.setImageResource(R.drawable.selectedfriendsicon);
            friendsIcon.isClickable = false
        }
        else if (current == "Chat") {
            chatIcon.setImageResource(R.drawable.selectedchaticon);
            chatIcon.isClickable = false
        }
        else if (current == "Profile") {
            profileIcon.visibility = View.GONE // show image view
        }
    }

}
