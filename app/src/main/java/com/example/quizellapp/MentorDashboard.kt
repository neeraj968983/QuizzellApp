package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MentorDashboard : AppCompatActivity() {
    var count = 1
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_dashboard)
        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)
        var logout:LinearLayout = findViewById(R.id.logout)

        var userextradetails:UserExtraDetails = UserExtraDetails(this)


        var i = intent
        System.out.println(i.getStringExtra("usernameFromLogin"))
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
                R.id.AccountSummary -> Toast.makeText(applicationContext, "Account Summary is under progress", Toast.LENGTH_LONG).show()
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
                R.id.ContactUS -> Toast.makeText(applicationContext, "ContactUS", Toast.LENGTH_LONG).show()
                R.id.AboutUS -> {
                    var intent: Intent = Intent(this,AboutUs::class.java)
                    startActivity(intent)
                }

            }
            true
        }
        logout.setOnClickListener{
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
}