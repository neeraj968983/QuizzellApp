package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ad4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad4)

        var startapp:Button = findViewById(R.id.Start)

        startapp.setOnClickListener(){
            val intent: Intent = Intent(this, LoginRegisterPage::class.java)
            startActivity(intent)
        }
    }
}