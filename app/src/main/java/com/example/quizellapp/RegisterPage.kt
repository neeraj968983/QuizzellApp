package com.example.quizellapp

import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class RegisterPage : AppCompatActivity() {
    var cal = Calendar.getInstance()
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

        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                DOB.setText(""+sdf.format(cal.time))
            }
        }

        DOB.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                DatePickerDialog(this@RegisterPage,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

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
                    if(databasehelper.checkUserIsThere(uname)){
                        Toast.makeText(this,"Username is already there!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if (pass == cpass) {
                            var user = UserInfo(uname,eid,dateOfBirth.toString(),phone.toLong(),pass)

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
        }

        alreadyHaveAnAccount.setOnClickListener(){
            val intent: Intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}