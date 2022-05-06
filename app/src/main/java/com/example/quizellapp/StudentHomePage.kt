package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class StudentHomePage : AppCompatActivity() {
    var count = 1
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)
        var userextradetails:UserExtraDetails = UserExtraDetails(this)
        var quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        var quizName= ""

        var i = intent

        var intent:Intent
        var (a,b,c,d) = userextradetails.fetchData(i.getStringExtra("usernameFromLogin"))
        System.out.println("A = $a\n B = $b\n C = $c\n D = $d")
        var User_Name = (a.toString() + " " + b.toString())

        var quizNames = quizInfoDatabase.quizNameList()

        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)
        var logout:LinearLayout = findViewById(R.id.logout)

        val listView:ListView = findViewById(R.id.QuizList)

        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, quizNames)
        listView.setBackgroundColor(Color.parseColor("#81FFFFFF"))
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            quizName = parent.getItemAtPosition(position).toString()
            if(quizName != ""){
                var intent = Intent(this,QuizInfo::class.java)
                intent.putExtra("QuizName",quizName)
                intent.putExtra("username",i.getStringExtra("usernameFromLogin"))
                startActivity(intent)
            }
        }



        toggle = ActionBarDrawerToggle(this,drawerLayout,findViewById(R.id.toolbar),R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)


        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.View_Profile -> {
                    intent = Intent(this,ViewProfile::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    intent.putExtra("user_name",User_Name)
                    startActivity(intent)
                }
                R.id.Edit_Profile -> {
                    intent = Intent(this,EditProfile::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                }
                R.id.Privacy -> {
                    intent = Intent(this,Privacy::class.java)
                    intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                }
                R.id.Ranking -> {
                    intent = Intent(this,RankingCategory::class.java)
                    intent.putExtra("username",i.getStringExtra("usernameFromLogin").toString())
                    startActivity(intent)
                }

                R.id.Finish_Quizzes -> {
                    intent = Intent(this, FinishedQuizzes::class.java)
                    startActivity(intent)
                }
                R.id.New_Quizzes -> {
                    intent = Intent(this, NewQuizzes::class.java)
                    startActivity(intent)
                }
                R.id.QA -> Toast.makeText(applicationContext, "Question/Answer", Toast.LENGTH_LONG).show()
                R.id.ChatUs -> Toast.makeText(applicationContext, "ChatUs", Toast.LENGTH_LONG).show()
                R.id.ContactUS -> Toast.makeText(applicationContext, "ContactUS", Toast.LENGTH_LONG).show()
                R.id.AboutUS -> {
                    intent = Intent(this,AboutUs::class.java)
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
