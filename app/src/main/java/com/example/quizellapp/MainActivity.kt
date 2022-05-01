package com.example.quizellapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var strName: String
    private lateinit var strPassword: String
    private lateinit var strCheckBox: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val checkPreferences = checkSharedPreference()

        Handler(Looper.getMainLooper()).postDelayed({

            if(checkPreferences==true){
                val intent: Intent = Intent(this, WhoAreYou::class.java)
                intent.putExtra("username", sharedPreferences.getString("name",""))
                startActivity(intent)
            }
            else{
                val intent: Intent = Intent(this, ad1::class.java)
                startActivity(intent)
            }

        },4400)
    }
    private fun checkSharedPreference():Boolean {
        strCheckBox = sharedPreferences.getString(getString(R.string.checkBox), "False").toString()
        strName = sharedPreferences.getString(getString(R.string.name), "").toString()
        strPassword = sharedPreferences.getString(getString(R.string.password), "").toString()


        return strName.isNotEmpty()

    }
}