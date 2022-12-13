package com.example.music_buddy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.music_buddy.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling


class messageFragment: Fragment(R.layout.fragment_message) {

    lateinit var swipeContainer : SwipeRefreshLayout

    lateinit var messagesRecyclerView: RecyclerView

    lateinit var adapter: MessageAdapter

    var allMessages: MutableList<Message> = mutableListOf()

    lateinit var currentUser: Data
    lateinit var currentFriend: Data

    val parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer = view.findViewById(R.id.swipeContainer)

        currentUser = arguments?.get("USER_EXTRA") as Data
        currentFriend = arguments?.get("FRIEND_EXTRA") as Data

        messagesRecyclerView = view.findViewById(R.id.rv_messageContainer)

        adapter = MessageAdapter(requireContext(), currentUser, currentFriend, allMessages, activity as HomeActivity)
        messagesRecyclerView.adapter = adapter

        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        allMessages.clear()
        queryMessages()

        adapter.itemCount

        view.findViewById<ImageButton>(R.id.ibSend).setOnClickListener {
            val textMessage = view.findViewById<EditText>(R.id.etMessage).text.toString()
            if (textMessage != null && textMessage.isNotEmpty()) {
                submitMessage(textMessage)
                view.findViewById<EditText>(R.id.etMessage).setText("")
            } else {
                Log.e(TAG, "No text message!")
                val toast = Toast.makeText(
                    requireContext(),
                    "Cannot send empty message",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        swipeContainer.setColorSchemeResources(R.color.yellow , R.color.pink , R.color.orange);
        swipeContainer.setOnRefreshListener {
            Log.i("RefreshTest" , "Refreshing Feed")
            allMessages.clear()
            queryMessages()
            swipeContainer.isRefreshing = false
        }



    }

    open fun queryMessages() {
        // Specify which class to query

        val query: ParseQuery<Message> = ParseQuery.getQuery(Message::class.java)
        query.whereEqualTo(Message.KEY_USERONE, currentUser.getEmail())
        query.whereEqualTo(Message.KEY_USERTWO, currentFriend.getEmail())

        val query2: ParseQuery<Message> = ParseQuery.getQuery(Message::class.java)
        query2.whereEqualTo(Message.KEY_USERONE, currentFriend.getEmail())
        query2.whereEqualTo(Message.KEY_USERTWO, currentUser.getEmail())

        val queries: MutableList<ParseQuery<Message>> = ArrayList()
        queries.add(query)
        queries.add(query2)

        val mainQuery = ParseQuery.or(queries)
        mainQuery.addAscendingOrder("createdAt")

        mainQuery.findInBackground(object : FindCallback<Message> {
            override fun done(messageList: MutableList<Message>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching message data")
                } else {
                    Log.i(TAG, "Success fetching message data")
                    if (messageList != null && messageList.isNotEmpty()) {
                        for(message in messageList) {
                            Log.i(
                                TAG,
                                "userOne: " + message.getUserOne()
                                        +"userTwo: " + message.getUserTwo()
                                        + " , Message: " + message.getMessage()
                            )
                        }
                        allMessages.addAll(messageList)
                        adapter.notifyDataSetChanged()
                        messagesRecyclerView.scrollToPosition(allMessages.size-1);
                    }
                }
            }
        })

    }

    fun submitMessage(textMessage: String){
//        val pb = view?.findViewById<View>(R.id.progressload)
//        pb?.visibility = ProgressBar.VISIBLE
        val message = Message()
        message.setMessage(textMessage)
        message.setUserOne(currentUser.getEmail()!!)
        message.setUserTwo(currentFriend.getEmail()!!)
        message.saveInBackground { exception ->
            if (exception != null){
                Log.e(TAG, "Error while saving message")
                exception.printStackTrace()
            } else {
                Log.i(TAG, "Successfully saved message")
                allMessages.add(message)
                adapter.notifyDataSetChanged()
                messagesRecyclerView.scrollToPosition(allMessages.size-1);
            }
        }
    }


    companion object {
        const val TAG = "messageFragment"
    }
}