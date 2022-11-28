package com.example.music_buddy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.parse.ParseFile
import com.parse.ParseObject
import java.io.File
import com.parse.ParseUser

class SettingsActivity : AppCompatActivity() {
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    val photoFileName = "photo.jpg"
    var photoFile: File? = null
    lateinit var ivPreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val userParseObject = ParseObject("userData")
        val ages = IntArray(67) { 13 + (it + 1) }.toCollection(ArrayList())
        val state_spinner: Spinner = findViewById(R.id.states)
        val age_spinner: Spinner = findViewById(R.id.age)

        ivPreview = findViewById(R.id.profilePic_img)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.States_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            state_spinner.adapter = adapter
        }

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ages
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            age_spinner.adapter = adapter
        }

        findViewById<Button>(R.id.save).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val description = findViewById<EditText>(R.id.bio).text.toString()
            val age = age_spinner.selectedItem
            val location = state_spinner.selectedItem.toString()
                if (description == ""){
                    Toast.makeText(this, "Please provide description", Toast.LENGTH_SHORT).show()
                }
                else {
                    submitPost(userParseObject, username,
                        age as Number, location, description, photoFile!!)
                    Toast.makeText(this, "Successfully posted!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }

        }

        findViewById<Button>(R.id.bt_takePicture).setOnClickListener{
            onLaunchCamera()
        }
    }

    fun submitPost( user:ParseObject, username:String, age:Number, location:String,description:String, profilePicture:File){

        user.put("username",username)
        user.put("profileDescription",description)
        user.put("profilepic",ParseFile(profilePicture))
        user.put("userAge",age)
        user.put("location",location)
        user.saveInBackground{ if (it != null){
            it.localizedMessage?.let { message -> Log.e(TAG, message) }
        }else{
            Log.d(TAG,"Object saved.")
        }
        }
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.musicbuddy", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(this.packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
// Resize the bitmap to 150x100 (width x height)
                val bMapScaled = Bitmap.createScaledBitmap(takenImage, 300, 400, true)
                // Load the taken image into a preview

                ivPreview.setImageBitmap(bMapScaled)
            } else { // Result was a failure
                Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show()
            }

    }


    companion object{
        const val TAG = "SettingPage"
    }
}