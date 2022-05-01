package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ad2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad2)

        var swipe: TextView = findViewById(R.id.swipe)

        swipe.setOnClickListener() {
            val intent: Intent = Intent(this, ad3::class.java)
            startActivity(intent)
        }
    }
}