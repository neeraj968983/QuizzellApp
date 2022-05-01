package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginRegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register_page)

        var login:Button = findViewById(R.id.Login)
        var signup:Button = findViewById(R.id.Sign_up)
        var aboutus:TextView = findViewById(R.id.About_us)

        login.setOnClickListener(){
            val intent: Intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener(){
            val intent: Intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        aboutus.setOnClickListener(){
            val intent: Intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }
    }
}