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
import java.util.*


class LoginActivity : AppCompatActivity() {
    val CLIENT_ID = "73eb7f1868ed45ce9434228fa009f30e"
    val AUTH_TOKEN_REQUEST_CODE = 0x10
    val REDIRECT_URI = "http://musicbuddy388.com/callback/"

    private val mOkHttpClient: OkHttpClient = OkHttpClient()
    var mAccessToken: String? = null
    private var mCall: Call? = null

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
                    Log.i(TAG,jsonObject.toString())
                } catch (e: JSONException) {
                    Log.e(TAG,"Failed to parse data: $e")
                }
            }
        })
    }


    fun onRequestTokenClicked(view: View?) {
        val request: AuthorizationRequest =
            getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)

    }
    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        return AuthorizationRequest.Builder(CLIENT_ID, type, REDIRECT_URI)
            .setShowDialog(false)
            .setScopes(arrayOf("user-read-email"))
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
                val intent = Intent(this, SettingsActivity::class.java)
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