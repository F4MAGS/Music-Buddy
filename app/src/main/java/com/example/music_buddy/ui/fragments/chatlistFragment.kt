package com.example.music_buddy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_buddy.ChatuserAdapter
import com.example.music_buddy.Data
import com.example.music_buddy.R
import com.example.music_buddy.User
import com.parse.FindCallback
import com.parse.*

class chatlistFragment: Fragment(R.layout.fragment_chatlist) {

    lateinit var friendsRecyclerView: RecyclerView

    lateinit var adapter: ChatuserAdapter

    var allFriends: MutableList<Data> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendsRecyclerView = view.findViewById(R.id.friendsRecyclerView)

        adapter = ChatuserAdapter(requireContext(), allFriends)
        friendsRecyclerView.adapter = adapter

        friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        allFriends.clear()
        queryFriends()
    }

    open fun queryFriends() {
        // Specify which class to query
        val query: ParseQuery<Data> = ParseQuery.getQuery(Data::class.java)
        query.whereEqualTo(Data.KEY_USERNAME, "person")
//        query.addDescendingOrder("createdAt")
//        query.include(Data.KEY_USER)

        query.findInBackground(object : FindCallback<Data> {
            override fun done(dataUsers: MutableList<Data>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching user data")
                } else {
                    Log.i(TAG, "Success fetching user data")
                    if (dataUsers != null) {
                        for (dataUser in dataUsers) {
                            Log.i(
                                TAG,
                                "username: " + dataUser.getUser()?.objectId
                                        + " , friendsArray: " + dataUser.getFriendsArray()
//                                        + " , singleArray: " + dataUser
//                                        + " , Fullarray: " + dataUsers
                            )
                            val friendsListJSONArray = dataUser.getFriendsArray()
                            if (friendsListJSONArray != null)
                                for (i in 0 until friendsListJSONArray.length()) {
                                    val friendID = friendsListJSONArray.getString(i)
                                    Log.i(
                                        TAG,
                                        "friendID: " + friendID
                                    )
                                    queryParseUser(friendID)
                                }
                        }
//                        allPosts.clear()
//                        allFriends.addAll(dataUsers)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    fun queryParseUser(userID: String) {
        val query: ParseQuery<User> = ParseQuery.getQuery(User::class.java)
        query.whereEqualTo(User.KEY_OBJECTID, userID)

        query.findInBackground(object : FindCallback<User> {
            override fun done(dataUsers: MutableList<User>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching parseUser data")
                } else {
                    Log.i(TAG, "Success fetching parseUser data")
                    Log.i(
                        TAG,
//                                        + " , singleArray: " + dataUser
                        " , Fullarray: " + dataUsers
                    )
                    if (dataUsers != null) {
                        for (dataUser in dataUsers) {
                            Log.i(
                                TAG,
                                "Friend objectID: " + dataUser.getUsername()
                            )
                            queryFriendData(dataUser)
                        }
                    }
                }
            }
        })
    }

    fun queryFriendData(friendID: User) {
        // Specify which class to query
        val query: ParseQuery<Data> = ParseQuery.getQuery(Data::class.java)
        query.whereEqualTo(Data.KEY_USER, friendID)
//        query.addDescendingOrder("createdAt")
//        query.include(Data.KEY_USER)

        query.findInBackground(object : FindCallback<Data> {
            override fun done(dataUsers: MutableList<Data>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching friend data")
                } else {
                    Log.i(TAG, "Success fetching friend data")
                    Log.i(
                        TAG,
//                                        + " , singleArray: " + dataUser
                                        " , Fullarray: " + dataUsers
                    )
                    if (dataUsers != null) {
                        for (dataUser in dataUsers) {
                            Log.i(
                                TAG,
                                "Friend objectID: " + dataUser.getUser()?.objectId
                            )
                        }
                        allFriends.addAll(dataUsers)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }


    companion object {
        const val TAG = "chatlistFragment"
    }
}