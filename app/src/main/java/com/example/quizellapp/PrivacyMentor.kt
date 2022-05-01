package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Switch
import android.widget.Toast

class PrivacyMentor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_mentor)

        val changePassword: Button = findViewById(R.id.change)
        val notificationSwitch:Switch = findViewById<Switch>(R.id.notification_switch)

        var i = intent
        var switchValue:String = "false"
        var back: Button = findViewById(R.id.back)

        changePassword.setOnClickListener(){
            var intent = Intent(this, ChangePassword::class.java)
            intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
            intent.putExtra("switchValue", switchValue)
            startActivity(intent)
        }

        notificationSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                switchValue = "true"
                var toast:Toast = Toast.makeText(this, "Notification On", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0,0)
                toast.show()

            } else {
                switchValue = "false"
                var toast:Toast = Toast.makeText(this, "Notification Off", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0,0)
                toast.show()
            }
        }

        back.setOnClickListener(){
            onBackPressed()
        }
    }
}