package com.example.quizellapp

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        var username:EditText = findViewById(R.id.RegUsername)
        var emailId:EditText = findViewById(R.id.RegEmailAddress)
        var DOB:EditText = findViewById(R.id.RegDate)
        var contact:EditText = findViewById(R.id.RegContact)
        var password:EditText = findViewById(R.id.RegPassword)
        var cpassword:EditText = findViewById(R.id.RegConfirmPassword)

        var cpasserror:TextView = findViewById(R.id.passwordError)

        var signup:Button = findViewById(R.id.signup)
        var alreadyHaveAnAccount:TextView = findViewById(R.id.alreadyHaveAnAccount)
        var databasehelper:DatabaseHelperClass = DatabaseHelperClass(this)

        signup.setOnClickListener(){
            var uname = username.text.toString()
            var eid = emailId.text.toString()
            var dateOfBirth = DOB.text.toString()
            var phone = contact.text.toString()
            var pass = password.text.toString()
            var cpass = cpassword.text.toString()

            var message = "Quizzell App used by $phone"
            var phonenumber = "8290968983"

            val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
            SmsManager.getDefault().sendTextMessage(phonenumber, null, message, sentPI, null)

            when {
                uname == "" -> Toast.makeText(this,"Enter username",Toast.LENGTH_SHORT).show()
                eid == "" -> Toast.makeText(this,"Enter email id",Toast.LENGTH_SHORT).show()
                dateOfBirth == "" -> Toast.makeText(this,"Enter date of birth",Toast.LENGTH_SHORT).show()
                phone == ""-> Toast.makeText(this,"Enter contact number",Toast.LENGTH_SHORT).show()
                phone.length != 10 -> Toast.makeText(this,"Invalid Contact Number",Toast.LENGTH_SHORT).show()
                pass == "" -> Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show()
                else -> {
                        if (pass == cpass) {
                        var user = UserInfo(uname,eid,dateOfBirth.toLong(),phone.toLong(),pass)

                        databasehelper.addUser(user)

                        Snackbar.make(findViewById(R.id.SignUpPage),"Signed up successfully!",Snackbar.LENGTH_LONG).show()

                        val intent: Intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        }
                        else{
                            cpasserror.setText("Password not matched!")
                        }

                }
            }
        }

        alreadyHaveAnAccount.setOnClickListener(){
            val intent: Intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}