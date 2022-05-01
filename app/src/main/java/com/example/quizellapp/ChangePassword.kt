package com.example.quizellapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class ChangePassword : AppCompatActivity() {
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Your password has been changed!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        var oldPass:EditText = findViewById(R.id.oldPassword)
        var newPass:EditText = findViewById(R.id.newPassword)
        var confirmPass:EditText = findViewById(R.id.conformPassword)
        var changePassword:Button = findViewById(R.id.update)
        var oldPasswordError:TextView = findViewById(R.id.OldPasswordError)
        var passwordError:TextView = findViewById(R.id.PasswordError)

        var i = intent
        var username = i.getStringExtra("usernameFromLogin")
        var switchValue = i.getStringExtra("switchValue")

        var databasehelper:DatabaseHelperClass = DatabaseHelperClass(this)

        changePassword.setOnClickListener(){
            var OPass = oldPass.text.toString()
            var NPass = newPass.text.toString()
            var CPass = confirmPass.text.toString()


            if(NPass.equals(CPass)){
                var passwordData = changePasswordDatabase(username,OPass,NPass)
                var checkpassword = databasehelper.changePassword(passwordData)
                print("\n $checkpassword")
                if (checkpassword){
                    Snackbar.make(findViewById(R.id.changePasswordPage),"Password Changed Successfully!",Snackbar.LENGTH_LONG).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (switchValue.equals("true")){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                                notificationChannel.lightColor = Color.BLUE
                                notificationManager.createNotificationChannel(notificationChannel)
                                builder = Notification.Builder(this, channelId).setContentTitle("Privacy Alert ")
                                        .setContentText("Your password has been changed!")
                                        .setSmallIcon(R.drawable .quizzelllogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                            }
                            notificationManager.notify(12345, builder.build())
                        }
                        onBackPressed()
                    },3500)
                }
                else{
                    oldPasswordError.setText("Password incorrect!")
                    Handler(Looper.getMainLooper()).postDelayed({
                        oldPasswordError.setText("")
                    },3000)
                }
            }
            else{
                passwordError.setText("Password not Matched!")
                Handler(Looper.getMainLooper()).postDelayed({
                    passwordError.setText("")
                },3000)
            }

        }
    }
}