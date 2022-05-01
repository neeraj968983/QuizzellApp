package com.example.quizellapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WhoAreYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_who_are_you)
        val i = intent

        var aboutUs:TextView = findViewById(R.id.About_us)
        var mentor:Button = findViewById(R.id.mentor)
        var student:Button = findViewById(R.id.student)

        aboutUs.setOnClickListener(){
            val intent: Intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }

        mentor.setOnClickListener(){
            val intent: Intent = Intent(this, MentorDashboard::class.java)
            intent.putExtra("usernameFromLogin",i.getStringExtra("username"))
            startActivity(intent)
        }

        student.setOnClickListener(){
            val intent = Intent(this, Category::class.java)
            intent.putExtra("usernameFromLogin",i.getStringExtra("username"))
            startActivity(intent)
        }
    }
}