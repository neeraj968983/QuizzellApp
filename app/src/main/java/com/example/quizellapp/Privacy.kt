package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Privacy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        val changePassword:Button = findViewById(R.id.change)
        var back:Button = findViewById(R.id.back)

        changePassword.setOnClickListener(){
            var intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }

        back.setOnClickListener(){
            onBackPressed()
        }

    }
}