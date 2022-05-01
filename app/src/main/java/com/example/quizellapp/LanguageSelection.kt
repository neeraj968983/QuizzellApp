package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

class LanguageSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)
        var i = intent

        var conti:Button = findViewById(R.id.Continue)



        var language1:CheckBox = findViewById(R.id.checkBox1)
        var language2:CheckBox = findViewById(R.id.checkBox2)
        var language3:CheckBox = findViewById(R.id.checkBox3)
        var language4:CheckBox = findViewById(R.id.checkBox4)
        var language5:CheckBox = findViewById(R.id.checkBox5)
        var language6:CheckBox = findViewById(R.id.checkBox6)

        conti.setOnClickListener(){

            var checkBoxCount = 0

            if (language1.isChecked){
                checkBoxCount++
            }
            if (language2.isChecked){
                checkBoxCount++
            }
            if (language3.isChecked){
                checkBoxCount++
            }
            if (language4.isChecked){
                checkBoxCount++
            }
            if (language5.isChecked){
                checkBoxCount++
            }
            if (language6.isChecked){
                checkBoxCount++
            }
            if(checkBoxCount > 2){
                Toast.makeText(this,"Only 2 languages allowed!",Toast.LENGTH_LONG).show()
            }
            else if (checkBoxCount == 0){
                Toast.makeText(this,"Select atleast 1",Toast.LENGTH_LONG).show()
            }
            else{
                var intent = Intent(this, StudentHomePage::class.java)
                intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                startActivity(intent)
            }

        }
    }
}