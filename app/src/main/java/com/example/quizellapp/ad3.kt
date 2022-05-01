package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ad3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad3)

        var swipe: TextView = findViewById(R.id.swipe)

        swipe.setOnClickListener() {
            val intent: Intent = Intent(this, ad4::class.java)
            startActivity(intent)
        }
    }
}