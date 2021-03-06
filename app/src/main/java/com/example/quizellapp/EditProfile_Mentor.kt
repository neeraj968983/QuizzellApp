package com.example.quizellapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class EditProfile_Mentor : AppCompatActivity() {
    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile__mentor)

        var userextradetails:UserExtraDetails = UserExtraDetails(this)
        var databaseHelperClass:DatabaseHelperClass = DatabaseHelperClass(this)
        var i = intent
        var uname = i.getStringExtra("usernameFromLogin")
        var Fname: EditText = findViewById(R.id.FirstName)
        var Lname: EditText = findViewById(R.id.LastName)
        var gender: Spinner = findViewById(R.id.gender)
        var address: EditText = findViewById(R.id.Address)
        var usernameOfEdit: TextView = findViewById(R.id.usernameOfEdit)
        var email: TextView = findViewById(R.id.Email)
        var dob: TextView = findViewById(R.id.DOB)
        var contact: TextView = findViewById(R.id.Contact)

        var mentorName:String

        usernameOfEdit.setText(""+uname)

        val edit: Button = findViewById(R.id.edit)
        val backButton: Button = findViewById(R.id.backbtn)
        val update: Button = findViewById(R.id.updatebtn)

        ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_dropdown_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gender.adapter=adapter
            gender.setSelection(0,false)
        }

        if(userextradetails.checkUserIsThere(uname)){
            var num = 0
            var (a,b,c,d) = userextradetails.fetchData(uname)
            Fname.setText(""+a)
            Lname.setText(""+b)

            if(c.equals("Male")){
                num = 0
            }
            else if(c.equals("Female")){
                num = 1
            }
            else if(c.equals("Other")){
                num = 2
            }
            gender.setSelection(num)
            address.setText(""+d)

        }
        var (e,f,g) = databaseHelperClass.fetchLocalData(uname)
        email.setText(""+e)
        dob.setText(""+f)
        System.out.println("Date of Borth is $f .........//")
        contact.setText(""+g)

        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                dob.setText(""+sdf.format(cal.time))
            }
        }

        dob.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                DatePickerDialog(this@EditProfile_Mentor,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        edit.setOnClickListener{
            if(userextradetails.checkUserIsThere(uname)){
                var userextradetail: extraDetail = extraDetail(uname, Fname.text.toString(), Lname.text.toString(), gender.selectedItem.toString(), address.text.toString())
                userextradetails.updateUserDetail(userextradetail)
                mentorName = (Fname.text.toString() + " " + Lname.text.toString())
                System.out.println("Mentor Name in Edit profile page is "+mentorName+"...........")
                var intent = Intent(this,MentorDashboard::class.java)
                intent.putExtra("MentorName",mentorName)
                intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                startActivity(intent)
            }
            else {
                var userextradetail: extraDetail = extraDetail(uname, Fname.text.toString(), Lname.text.toString(), gender.selectedItem.toString(), address.text.toString())
                userextradetails.addUser(userextradetail)
            }
        }

        backButton.setOnClickListener(){
            onBackPressed()
        }

        update.setOnClickListener{
            var updateUserLocalData:UserInfoUpdate = UserInfoUpdate(uname,email.text.toString(),dob.text.toString(),contact.text.toString().toLong())
            databaseHelperClass.updateUserDetail(updateUserLocalData)
            onBackPressed()
        }
    }
}