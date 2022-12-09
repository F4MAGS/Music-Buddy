package com.example.music_buddy
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import org.json.JSONArray
import org.json.JSONObject
import java.time.format.DateTimeFormatter
import java.util.*

@ParseClassName("userData")
class Data : ParseObject() {

    fun getFriendsArray(): JSONArray? {
        return getJSONArray(KEY_FRIENDS)
    }

    fun getTopArtistsObject(): JSONObject? {
        return getJSONObject(KEY_ARTISTS)
    }

    fun getUsername(): String? {
        return getString(KEY_USERNAME)
    }

    fun getEmail(): String? {
        return getString(KEY_EMAIL)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun getUserID(): String? {
        return getParseUser(KEY_USER)?.objectId
    }

    fun getCreationDate(): String {
        return createdAt.toString()
    }

    fun getProfilePic(): ParseFile? {
        return getParseFile(KEY_PICTURE)
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_FRIENDS = "friends"
        const val KEY_USER = "userID"
        const val KEY_PICTURE = "profilePic"
        const val KEY_ARTISTS = "topArtists"
    }
}