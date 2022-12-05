package com.example.music_buddy
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.time.format.DateTimeFormatter
import java.util.*

@ParseClassName("userChat")
class Message : ParseObject() {

    fun getMessage(): String? {
        return getString(KEY_MESSAGE)
    }

    fun setMessage(message : String) {
        return put(KEY_MESSAGE, message)
    }

    fun getUserOne(): ParseUser? {
        return getParseUser(KEY_USERONE)
    }

    fun getUserTwo(): ParseUser? {
        return getParseUser(KEY_USERTWO)
    }

    fun setUserOne(user : ParseUser) {
        put(KEY_USERONE , user)
    }

    fun setUserTwo(user : ParseUser) {
        put(KEY_USERTWO , user)
    }

    fun getCreationDate(): String {
        return createdAt.toString()
    }


    companion object {
        const val KEY_MESSAGE = "chatText"
        const val KEY_USERONE = "userOne"
        const val KEY_USERTWO = "userTwo"
    }
}