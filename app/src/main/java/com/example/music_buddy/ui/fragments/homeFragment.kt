package com.example.music_buddy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_buddy.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class homeFragment: Fragment(R.layout.item_home) {

    lateinit var usersRecyclerView: RecyclerView

    lateinit var adapter: HomeAdapter

    var allFriends: MutableList<Data> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecyclerView = view.findViewById(R.id.usersRecyclerView)

        adapter = HomeAdapter(requireContext(), allFriends)
        usersRecyclerView.adapter = adapter

        usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

//        queryFriends()


    }

    open fun queryFriends() {
        // Specify which class to query

//        try {
        val query: ParseQuery<Data> = ParseQuery.getQuery(Data::class.java)
//        query.whereEqualTo(Data.KEY_EMAIL, ParseUser.getCurrentUser().username)

//        query.addDescendingOrder("createdAt")
//        query.include(Data.KEY_USER)

        query.findInBackground(object : FindCallback<Data> {
            override fun done(dataUsers: MutableList<Data>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching user data")
                } else {
                    Log.i(TAG, "Success fetching user data")
                    if (dataUsers != null && dataUsers.isNotEmpty()) {
                        val dataUser = dataUsers[0]

                        //TODO THIS CODE DECIDES WHAT IS DISPLAYED ON SCREEN
                        //allFriends.clear()
                        allFriends.add(dataUser)
                        adapter.notifyDataSetChanged()

                        //ONCE THEY PRESS EITHER BUTTON YOU INCREMENT AND GO TO NEXT USER WHICH IS dataUsers[1] etc

                        //TODO ONCE THEY PRESS BUTTON TO ADD FRIEND
//                        addFriend(dataUser.getEmail())

                    }
                }
            }
        })
    }

//    fun addFriend(userID: String?){
//        // Specify which class to query
//
////        try {
//        val query: ParseQuery<Data> = ParseQuery.getQuery(Data::class.java)
//        query.whereEqualTo(Data.KEY_EMAIL, ParseUser.getCurrentUser().username)
//
////        query.addDescendingOrder("createdAt")
////        query.include(Data.KEY_USER)
//
//        query.findInBackground(object : FindCallback<Data> {
//            override fun done(dataUsers: MutableList<Data>?, e: ParseException?) {
//                if (e != null) {
//                    Log.e(chatlistFragment.TAG, "Error fetching user data")
//                } else {
//                    Log.i(chatlistFragment.TAG, "Success fetching user data")
//                    if (dataUsers != null && dataUsers.isNotEmpty()) {
//                        val dataUser = dataUsers.first()
//                        var friendsListJSONArray = dataUser.getFriendsArray()
//
//                       // friendsListJSONArray.add(userID)
//                        // TODO FIX THIS
//
//                        //dataUser.put("friends",friendsListJSONArray)
//                    }
//                }
//            }
//        })
//    }



    companion object {
        const val TAG = "homeFragment"
    }
}