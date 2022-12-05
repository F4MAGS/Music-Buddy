package com.example.music_buddy
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import org.json.JSONArray
import java.time.format.DateTimeFormatter
import java.util.*

@ParseClassName("User")
class User : ParseUser() {

    fun getParseUser(): ParseUser? {
        return getParseUser(KEY_OBJECTID)
    }

    fun getUserID(): String? {
        return getString(KEY_OBJECTID)
    }


    companion object {
        const val KEY_OBJECTID = "objectId"
        const val KEY_USERNAME = "username"

    }
}