package com.example.music_buddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import com.parse.ParseObject
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import java.util.*
import android.widget.Toast

import com.parse.FindCallback

import com.parse.ParseQuery

class LoginActivity : AppCompatActivity() {
    val CLIENT_ID = "73eb7f1868ed45ce9434228fa009f30e"
    val AUTH_TOKEN_REQUEST_CODE = 0x10
    val REDIRECT_URI = "http://musicbuddy388.com/callback/"
    var email = ""

    private val mOkHttpClient: OkHttpClient = OkHttpClient()
    var mAccessToken: String? = null
    private var mCall: Call? = null
    private var mCall2: Call? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onDestroy() {
        cancelCall()
        super.onDestroy()
    }

    fun onGetUserProfileClicked() {
        val request: Request = Request.Builder()
            .url("https://api.spotify.com/v1/me")
            .addHeader("Authorization", "Bearer $mAccessToken")
            .build()
        cancelCall()
        mCall = mOkHttpClient.newCall(request)
        mCall!!.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG,"Failed to fetch data: $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: okhttp3.Response) {
                try {
                    val jsonObject = JSONObject(response.body!!.string())
                    email = jsonObject.getString("email")
                    Log.i(TAG,jsonObject.toString())
                    var query: ParseQuery<ParseUser> = ParseUser.getQuery()
                    query = query.whereEqualTo("username", email);
                    try {
                        val queryCount = query.count()
                        Log.d(Companion.TAG, "Count: $queryCount")
                        if (queryCount == 0){
                            val topArtistsObj  = getTopArtists()
                            Log.i(TAG,topArtistsObj.toString())
                            registerUser(email,"password",topArtistsObj)
                        }
                        else{
                            val topArtistsObj = getTopArtists()
                            Log.i(TAG,topArtistsObj.toString())
                            loginUser(email,"password",topArtistsObj)

                        }

                    } catch (parseException: ParseException) {
                        parseException.printStackTrace()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG,"Failed to parse data: $e")
                }
            }
        })
    }

    fun getTopArtists():JSONObject{

            var jsonObject2 : JSONObject = JSONObject().put("something"," hi")
            val request2: Request = Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?limit=3")
                .addHeader("Authorization", "Bearer $mAccessToken")
                .build()
            cancelCall()
                mOkHttpClient.newCall(request2).execute().use { response2 ->
                if (!response2.isSuccessful) throw IOException("Unexpected code $response2")

                try {
                    jsonObject2 = JSONObject(response2.body!!.string())
                    Log.i(TAG,jsonObject2.toString())
                } catch (e: JSONException) {
                    Log.i(TAG,e.toString())
                }
//                println(response2.body!!.string())
            }
            return jsonObject2




//        mCall2!!.enqueue(object : okhttp3.Callback {
//            override  fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG,"Failed to fetch data: $e")
//            }
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response2: okhttp3.Response) {
//
//
//            }
//        })
//        return jsonObject2
    }


    fun registerUser(username:String, password:String,topArtistsObj:JSONObject){
        val user = ParseUser()
        user.setUsername(username)
        user.setPassword(password)
        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG,"Registration ParseUser successful")
                val userParseObject: ParseObject = ParseObject.create("userData")
                userParseObject.put("username",username)
                userParseObject.put("email",username)
                userParseObject.put("userID",ParseUser.createWithoutData("_User", user.objectId))
                userParseObject.put("topArtists",topArtistsObj)
                userParseObject.saveInBackground{
                    if (it != null) {
                        it.localizedMessage?.let { message -> Log.e(TAG, message) }
                    }
                    else{
                        Log.d(TAG,"ParseObject saved in userData.")
                    }
                }
            } else {
                Log.e(TAG,e.toString())
            }
        }

        
    }
    fun loginUser(username:String, password:String,topArtistsObj:JSONObject){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG,"Login ParseUser successful")
                var query = ParseQuery<ParseObject>("userData")
                query = query.whereEqualTo("email", ParseUser.getCurrentUser().username)
                query.findInBackground { objects: List<ParseObject>, e: ParseException? ->
                    if (e == null) {
                        Log.i(TAG,"Login user adding topArtists")
                        Log.i(TAG,topArtistsObj.toString())
                        objects.get(0).put("topArtists",topArtistsObj)
                        objects.get(0).saveInBackground()
                    } else {
                        Log.e(TAG, "Parse Error: ", e)
                    }
                }

            } else {
                Log.e(TAG,e.toString())
            }})
        )
    }

    fun onRequestTokenClicked(view: View?) {
        val request: AuthorizationRequest =
            getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)

    }
    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        return AuthorizationRequest.Builder(CLIENT_ID, type, REDIRECT_URI)
            .setShowDialog(false)
            .setScopes(arrayOf("user-read-email","user-top-read"))
            .setCampaign("your-campaign-token")
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response: AuthorizationResponse = AuthorizationClient.getResponse(resultCode, data)
        if (response.error != null && !response.error.isEmpty()) {
            Log.e(TAG,response.error)
        }
        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            mAccessToken = response.accessToken
            if (mAccessToken != null) {
                ParseUser.logOut()
                val intent = Intent(this, HomeActivity::class.java)
                onGetUserProfileClicked()
                startActivity(intent)

            }
        }
    }




    private fun cancelCall() {
        if (mCall != null) {
            mCall?.cancel()
        }
    }

    companion object{
        val TAG = "LoginActivity"
    }


}