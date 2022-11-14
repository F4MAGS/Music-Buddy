package com.example.music_buddy

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val ages = IntArray(67) { 13 + (it + 1) }.toCollection(ArrayList())
        val state_spinner: Spinner = findViewById(R.id.states)
        val age_spinner: Spinner = findViewById(R.id.age)
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



    }
}