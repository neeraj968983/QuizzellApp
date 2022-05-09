package com.example.quizellapp

import android.app.Dialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.*
import androidx.annotation.RequiresApi

class LoginPage : AppCompatActivity() {
    var databasehelper:DatabaseHelperClass = DatabaseHelperClass(this)

    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Your password has been changed!"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var strName: String
    private lateinit var strPassword: String
    private lateinit var strCheckBox: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        var forgotPassword:TextView = findViewById(R.id.forgot)



        var signup:TextView = findViewById(R.id.signup)
        var userName:EditText = findViewById(R.id.username)
        var password:EditText = findViewById(R.id.password)
        var login:Button = findViewById(R.id.login)
        var aboutUs:TextView = findViewById(R.id.About_us)
        var rememberInfo:CheckBox = findViewById(R.id.sharedPreferenceButton)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()



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

        forgotPassword.setOnClickListener {
            getPasswordBackDialog()
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getPasswordBackDialog() {
        var dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.forgotpasswordotp)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        var username:EditText = dialog.findViewById(R.id.username)
        var contact:EditText = dialog.findViewById(R.id.MobileNumber)
        var getOtp:Button = dialog.findViewById(R.id.GetOTP)
        var otp:EditText = dialog.findViewById(R.id.OTP)
        var submit:Button = dialog.findViewById(R.id.SubmitButton)

        val c = Calendar.getInstance()
        val minute = c.get(Calendar.MINUTE)
        var otpGenerate:Long = 0




        getOtp.setOnClickListener {
            var check2 = databasehelper.verifyUsernameAndContact(usernameAndContactdataclass(username.text.toString(),contact.text.toString().toLong()))
            if(check2){
                otpGenerate = (((contact.text.toString().toLong())/999999)*minute)
                System.out.println("OTP is $otpGenerate")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                    notificationChannel.lightColor = Color.BLUE
                    notificationManager.createNotificationChannel(notificationChannel)
                    builder = Notification.Builder(this, channelId).setContentTitle("Privacy Alert ")
                            .setContentText("To reset password OTP is $otpGenerate")
                            .setSmallIcon(R.drawable .quizzelllogo)
                            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                }
                notificationManager.notify(12345, builder.build())
            }
            else{
                Toast.makeText(this,"Details doesn't match with database! Try again",Toast.LENGTH_SHORT).show()
            }

        }
        submit.setOnClickListener {
            if(otpGenerate==(otp.text.toString().toLong())){
                Toast.makeText(this,"Details are valid! move forward",Toast.LENGTH_SHORT).show()
                changePassword(username.text.toString())
                dialog.dismiss()
            }
            else{
                Toast.makeText(this,"OTP doesn't match",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()




    }

    fun changePassword(username:String){
        var dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.passwordchangthroghotp)

        var password:EditText = dialog.findViewById(R.id.NewPassword)
        var re_enterpassword:EditText = dialog.findViewById(R.id.ReEnterPassword)
        var change:Button = dialog.findViewById(R.id.ChangeButton)

        change.setOnClickListener {
            if((password.text.toString())==(re_enterpassword.text.toString())){
                databasehelper.changePasswordThroughOTP(username,password.text.toString())
                Toast.makeText(this,"Password Changed!",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            else{
                Toast.makeText(this,"Password not matched!",Toast.LENGTH_SHORT).show()
            }
        }


        dialog.show()

    }

    override fun onBackPressed() {
        Toast.makeText(this,"Sorry you can't go back!",Toast.LENGTH_SHORT).show()
    }
}