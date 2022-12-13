package com.example.music_buddy.ui.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_buddy.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class profileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var profileRecylerView: RecyclerView

    lateinit var adapter: ProfileAdapter

    var allProfiles: MutableList<Data> = mutableListOf()

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileRecylerView = view.findViewById(R.id.profileRecyclerView)
        adapter = ProfileAdapter(requireContext(), allProfiles)
        profileRecylerView.adapter = adapter

        profileRecylerView.layoutManager = LinearLayoutManager(requireContext())

        allProfiles.clear()
        queryProfile()

        profileRecylerView.findViewById<View>(R.id.settingsbtn)?.setOnClickListener(){
            activity?.let{
                val intent = Intent(it, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    open fun queryProfile() {
        val query: ParseQuery<Data> = ParseQuery.getQuery(Data::class.java)
        query.whereEqualTo(Data.KEY_EMAIL, ParseUser.getCurrentUser().username)

        query.findInBackground(object : FindCallback<Data>{
            override fun done(data: MutableList<Data>?, e: ParseException?) {
                if (e != null){
                    Log.e(TAG, "Error fetching User Data")
                } else {
                    if(data != null){
                        for(data1 in data){
                            Log.i(TAG, "User data for " + data1.getUserID() + " retrieved")
                        }

                        allProfiles.addAll(data)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

    }

    companion object{
        const val TAG = "profileFragment"
    }
}