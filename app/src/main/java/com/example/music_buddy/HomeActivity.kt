package com.example.music_buddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.music_buddy.ui.fragments.profileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
//        val fragmentManager: FragmentManager = supportFragmentManager
//
//        var fragmentToShow: Fragment? = null
//        findViewById<RelativeLayout>(R.id.rl_bottomBar).setOnClickListener() { }
//                item ->
//
//            when (item.itemId){
//                R.id.iv_home -> {
//                    fragmentToShow = profileFragment()
////                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
//                }
////                R.id.action_compose -> {
////                    fragmentToShow = ComposeFragment()
//////                    Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show()
////                }
////                R.id.action_profile -> {
////                    fragmentToShow = ProfileFragment()
//////                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
////                }
//            }
//            if (fragmentToShow != null){
//                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow!!).commit()
//            }
//
//            true
//        }
//    }
