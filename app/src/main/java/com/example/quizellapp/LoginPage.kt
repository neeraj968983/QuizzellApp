package com.example.quizellapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.*

class LoginPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var strName: String
    private lateinit var strPassword: String
    private lateinit var strCheckBox: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)



        var signup:TextView = findViewById(R.id.signup)
        var userName:EditText = findViewById(R.id.username)
        var password:EditText = findViewById(R.id.password)
        var login:Button = findViewById(R.id.login)
        var aboutUs:TextView = findViewById(R.id.About_us)
        var rememberInfo:CheckBox = findViewById(R.id.sharedPreferenceButton)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        var databasehelper:DatabaseHelperClass = DatabaseHelperClass(this)

        aboutUs.setOnClickListener(){
            val intent: Intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener(){
            val intent: Intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        login.setOnClickListener(){
            var uname = userName.text.toString()
            var pass = password.text.toString()

            var loginUser = LoginData(uname,pass)

            var check = databasehelper.loginUser(loginUser)

            if(check == true){
                if (rememberInfo.isChecked) {
                    editor.putString(getString(R.string.checkBox), "True")
                    editor.apply()
                    strName = uname
                    editor.putString(getString(R.string.name), strName)
                    editor.commit()
                    strPassword = pass
                    editor.putString(getString(R.string.password), strPassword)
                    editor.commit()
                } else {
                    editor.putString(getString(R.string.checkBox), "False")
                    editor.commit()
                    editor.putString(getString(R.string.name), "")
                    editor.commit()
                    editor.putString(getString(R.string.password), "")
                    editor.commit()
                }
                var intent:Intent =Intent(this, WhoAreYou::class.java)
                System.out.println(uname)
                intent.putExtra("username",uname)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Invalid id and password",Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onBackPressed() {
        Toast.makeText(this,"Sorry you can't go back!",Toast.LENGTH_SHORT).show()
    }
}