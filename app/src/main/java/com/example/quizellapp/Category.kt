package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColor

class Category : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        var i = intent
        val conti:Button = findViewById(R.id.Continue)
        val searchButton:Button = findViewById(R.id.SearchButton)

        var category1:CheckBox = findViewById(R.id.checkBox1)
        var category2:CheckBox = findViewById(R.id.checkBox2)
        var category3:CheckBox = findViewById(R.id.checkBox3)
        var category4:CheckBox = findViewById(R.id.checkBox4)
        var category5:CheckBox = findViewById(R.id.checkBox5)
        var category6:CheckBox = findViewById(R.id.checkBox6)
        var category7:CheckBox = findViewById(R.id.checkBox7)
        var category8:CheckBox = findViewById(R.id.checkBox8)
        var category9:CheckBox = findViewById(R.id.checkBox9)
        var category10:CheckBox = findViewById(R.id.checkBox10)
        var category11:CheckBox = findViewById(R.id.checkBox11)
        var category12:CheckBox = findViewById(R.id.checkBox12)
        var category13:CheckBox = findViewById(R.id.checkBox13)
        var category14:CheckBox = findViewById(R.id.checkBox14)
        var category15:CheckBox = findViewById(R.id.checkBox15)
        var category16:CheckBox = findViewById(R.id.checkBox16)




        conti.setOnClickListener(){
            var checkboxCount = 0
            if(category1.isChecked){
                checkboxCount++
            }
            if (category2.isChecked){
                checkboxCount++
            }
            if(category3.isChecked){
                checkboxCount++
            }
            if (category4.isChecked){
                checkboxCount++
            }
            if(category5.isChecked){
                checkboxCount++
            }
            if (category6.isChecked){
                checkboxCount++
            }
            if(category7.isChecked){
                checkboxCount++
            }
            if (category8.isChecked){
                checkboxCount++
            }
            if(category9.isChecked){
                checkboxCount++
            }
            if (category10.isChecked){
                checkboxCount++
            }
            if(category11.isChecked){
                checkboxCount++
            }
            if (category12.isChecked){
                checkboxCount++
            }
            if(category13.isChecked){
                checkboxCount++
            }
            if (category14.isChecked){
                checkboxCount++
            }
            if(category15.isChecked){
                checkboxCount++
            }
            if (category16.isChecked){
                checkboxCount++
            }
            if(checkboxCount > 5){
                Toast.makeText(this,"Maximum 5 categories allowed",Toast.LENGTH_LONG).show()
            }
            else if(checkboxCount == 0){
                var toast:Toast = Toast.makeText(this,"Select atleast one",Toast.LENGTH_LONG)
                //toast.view?.setBackgroundColor(Color.CYAN)
                toast.show()

            }
            else{
                var intent = Intent(this,LanguageSelection::class.java)
                intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                startActivity(intent)
            }

        }

        searchButton.setOnClickListener(){
            val alert:AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("Alert")
            alert.setMessage("This time couldn't load this service")
            alert.setPositiveButton("OK"){
                _,_ -> Toast.makeText(this,"Sorry Sir/Mam",Toast.LENGTH_LONG).show()
            }
            val alertBox:AlertDialog = alert.create()
            alertBox.setCanceledOnTouchOutside(true)
            alertBox.show()
        }
    }
}