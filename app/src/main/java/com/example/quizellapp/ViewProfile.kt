package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ViewProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)
        var i = intent
        var userDetailsFetchUp:UserDetailsFetchUp = UserDetailsFetchUp(this)
        var username:TextView = findViewById(R.id.update_editprofile11)
        var emailid:TextView = findViewById(R.id.update_editprofile12)
        var dateOfBirth:TextView = findViewById(R.id.update_editprofile13)
        var contact:TextView = findViewById(R.id.update_editprofile14)
        var uname = i.getStringExtra("usernameFromLogin")

        var (email,dob,cont) = userDetailsFetchUp.getDetails(uname)

        username.setText(""+uname)
        emailid.setText(""+email)
        dateOfBirth.setText(""+dob)
        contact.setText(""+cont)



        val backButton:Button = findViewById(R.id.backButton)

        backButton.setOnClickListener(){
            onBackPressed()
        }
    }
}