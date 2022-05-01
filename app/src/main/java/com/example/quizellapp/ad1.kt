package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.r0adkll.slidr.model.SlidrInterface

class ad1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad1)

        var swipe:TextView = findViewById(R.id.swipe)

        swipe.setOnClickListener(){
            val intent: Intent = Intent(this, ad2::class.java)
            startActivity(intent)
        }

    }
}