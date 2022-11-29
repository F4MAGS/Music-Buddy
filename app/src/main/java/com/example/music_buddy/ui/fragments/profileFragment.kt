package com.example.music_buddy.ui.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.music_buddy.LoginActivity
import com.example.music_buddy.R
import com.example.music_buddy.SettingsActivity

class profileFragment : Fragment(R.layout.fragment_profile) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.settingsbtn).setOnClickListener {
            activity?.let{
                val intent = Intent(it, SettingsActivity::class.java)
                startActivity(intent)
            }

        }

    }
}