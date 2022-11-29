package com.example.music_buddy







import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            SpotifyService.connect(this) {
                if(it) {
                    redirectHome()
                }
            }
        }
    }

    private fun redirectHome() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}