package com.example.quizellapp

import android.app.Dialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.telephony.SmsManager
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MentorDashboard : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var count = 1
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_dashboard)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)
        var logout:LinearLayout = findViewById(R.id.logout)

        var userextradetails:UserExtraDetails = UserExtraDetails(this)


        var i = intent
        var (a,b,c,d) = userextradetails.fetchData(i.getStringExtra("usernameFromLogin"))
        System.out.println("A = $a\n B = $b\n C = $c\n D = $d")
        var mentorName = (a.toString() + " " + b.toString())

        toggle = ActionBarDrawerToggle(this,drawerLayout,findViewById(R.id.toolbar),R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)


        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.View_Profile -> {
                    var intent: Intent = Intent(this,ViewProfile_Mentor::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    intent.putExtra("mentorName", mentorName)
                    startActivity(intent)
                }
                R.id.Edit_Profile -> {
                    var intent: Intent = Intent(this,EditProfile_Mentor::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                }
                R.id.AccountSummary -> {
                    accountSummaryDialog()
                }
                R.id.Privacy -> {
                    var intent: Intent = Intent(this,PrivacyMentor::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                }

                R.id.Create_Quiz -> {
                    var intent: Intent = Intent(this, QuizCreationInfo::class.java)
                    intent.putExtra("usernameFromLogin", i.getStringExtra("usernameFromLogin"))
                    intent.putExtra("MentorName",mentorName)
                    startActivity(intent)
                }
                R.id.Your_Quizzes -> Toast.makeText(applicationContext, "This session is under progress", Toast.LENGTH_LONG).show()
                R.id.QA -> Toast.makeText(applicationContext, "Question/Answer", Toast.LENGTH_LONG).show()
                R.id.ChatUs -> Toast.makeText(applicationContext, "ChatUs", Toast.LENGTH_LONG).show()
                R.id.ContactUS -> {
                    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("Type details below:")
                    val input = EditText(this)
                    input.minLines = 3
                    input.setHint("Enter your problem here")
                    builder.setView(input)
                    builder.setPositiveButton("Send",DialogInterface.OnClickListener{
                        dialog, which ->
                        var msg = "From Mentor: " + input.text.toString()
                        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
                        SmsManager.getDefault().sendTextMessage("8290968983", null, msg, sentPI, null)
                    })
                    builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{
                        dialog, which ->
                        dialog.cancel()
                    })
                    builder.show()
                }
                R.id.AboutUS -> {
                    var intent: Intent = Intent(this, AboutUs::class.java)
                    startActivity(intent)
                }

            }
            true
        }
        logout.setOnClickListener{
            editor.putString(getString(R.string.checkBox), "False")
            editor.commit()
            editor.putString(getString(R.string.name), "")
            editor.commit()
            editor.putString(getString(R.string.password), "")
            editor.commit()
            intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        if(count==1){
            Toast.makeText(this,"Press back again to logout",Toast.LENGTH_SHORT).show()
            count++
        }
        else{
            count=1
            var inten:Intent = Intent(this,LoginPage::class.java)
            startActivity(inten)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)

    }
    fun accountSummaryDialog(){
        var dialog:Dialog = Dialog(this)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.account_summary_dialogbox)

        var exit:Button = dialog.findViewById(R.id.Exit)
        var withdraw:Button = dialog.findViewById(R.id.Withdraw)
        var info:TextView = dialog.findViewById(R.id.Information)

        exit.setOnClickListener {
            dialog.dismiss()
        }
        withdraw.setOnClickListener {
            Toast.makeText(this, "Currently payment option not available!",Toast.LENGTH_SHORT).show()
        }
        info.setOnClickListener {
            var infoDialog = Dialog(this)
            infoDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            infoDialog.setContentView(R.layout.infopage_account_summary)

            var OK:TextView = infoDialog.findViewById(R.id.OK)

            OK.setOnClickListener {
                infoDialog.dismiss()
            }
            infoDialog.show()

        }
        dialog.show()
    }
}