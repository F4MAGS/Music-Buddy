package com.example.music_buddy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_buddy.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

@Suppress("DEPRECATION")
class homeFragment: Fragment(R.layout.item_home) {

    lateinit var usersRecyclerView: RecyclerView

    lateinit var adapter: HomeAdapter

    lateinit var next: Button

    var count = 0

    var allFriends: MutableList<Data> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecyclerView = view.findViewById(R.id.usersRecyclerView)

        adapter = HomeAdapter(requireContext(), allFriends)
        usersRecyclerView.adapter = adapter

        usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var add_friend = view?.findViewById<Button>(R.id.add_friend)
        var next = view?.findViewById<Button>(R.id.next)

        allFriends.clear()
        queryFriends()

        add_friend?.setOnClickListener {
            Log.i(TAG, "button was clicked")
           // val dataUser = dataUsers?.get(count)
            count += 1
            allFriends.clear()
           // if (dataUser != null) {
           //     allFriends.add(dataUser)
          //  }
            adapter.notifyDataSetChanged()
        }

        next?.setOnClickListener {
            Log.i(TAG, "button was clicked")
            // val dataUser = dataUsers?.get(count)
            count += 1
            allFriends.clear()
            // if (dataUser != null) {
            //     allFriends.add(dataUser)
            //  }
            adapter.notifyDataSetChanged()
        }





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
                val dataUser = dataUsers?.get(count)
                if (e != null) {
                    Log.e(TAG, "Error fetching user data")
                } else {
                    Log.i(TAG, "Success fetching user data")
                    if (dataUsers != null && dataUsers.isNotEmpty()) {
                        val dataUser = dataUsers[count]



                        //TODO THIS CODE DECIDES WHAT IS DISPLAYED ON SCREEN
                        allFriends.clear()
                        allFriends.add(dataUser)
                        adapter.notifyDataSetChanged()


                            //your implementation goes here




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


