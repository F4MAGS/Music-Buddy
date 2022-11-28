package com.example.music_buddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.music_buddy.databinding.ActivityHomeBinding
import com.example.music_buddy.ui.fragments.homeFragment
import com.example.music_buddy.ui.fragments.profileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlTopBar.ivProfile.setOnClickListener{
            replaceFragment(profileFragment())
        }

        binding.rlBottomBar.ivHome.setOnClickListener{
            replaceFragment(homeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContainer, fragment)
        fragmentTransaction.commit()

    }
}
