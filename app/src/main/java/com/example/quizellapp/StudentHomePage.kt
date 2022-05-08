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
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class StudentHomePage : AppCompatActivity() {
    var count = 1
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var ratingReview = RatingReview(this)
    lateinit var username:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        var userextradetails:UserExtraDetails = UserExtraDetails(this)
        var quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        var quizName= ""

        var i = intent
        username = i.getStringExtra("usernameFromLogin").toString()

        var intent:Intent
        var (a,b,c,d) = userextradetails.fetchData(i.getStringExtra("usernameFromLogin"))
        System.out.println("A = $a\n B = $b\n C = $c\n D = $d")
        var User_Name = (a.toString() + " " + b.toString())

        var quizNames = quizInfoDatabase.quizNameList()

        var data = quizInfoDatabase.quizDetailList()
        System.out.println("Size of data: " + data.size)

        var quiznames = arrayListOf<String>()
        var mentornames = arrayListOf<String>()
        var quiztype = arrayListOf<String>()
        var categories = arrayListOf<String>()
        var maxmarks = arrayListOf<Int>()
        var durations = arrayListOf<Int>()
        var attemptsleft = arrayListOf<Int>()

        for (i in 0..(data.size-1)){
            quiznames.add((data[i].quizname).toString())
            mentornames.add((data[i].mentorname).toString())
            quiztype.add((data[i].quiztype).toString())
            categories.add((data[i].category).toString())
            maxmarks.add((data[i].maxmarks).toInt())
            durations.add((data[i].duration).toInt())
            attemptsleft.add((data[i].totalattempts).toInt())
        }


        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)
        var logout:LinearLayout = findViewById(R.id.logout)

        val listView:ListView = findViewById(R.id.QuizList)
        val myAdaptor = QuizListCustomAdaptor(this,quiznames,mentornames,quiztype,categories,maxmarks,durations,attemptsleft)
        listView.adapter = myAdaptor

//        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, quizNames)
        listView.setBackgroundColor(Color.parseColor("#81FFFFFF"))
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
                    intent.putExtra("username",i.getStringExtra("usernameFromLogin").toString())
                    startActivity(intent)
                }
                R.id.New_Quizzes -> {
                    intent = Intent(this, NewQuizzes::class.java)
                    startActivity(intent)
                }
                R.id.QA -> Toast.makeText(applicationContext, "Question/Answer", Toast.LENGTH_LONG).show()
                R.id.Feedback -> feedbackAlertBox(username)
                R.id.ContactUS -> {
                    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("Type details below:")
                    val input = EditText(this)
                    input.minLines = 3
                    input.setHint("Enter your problem here")
                    builder.setView(input)
                    builder.setPositiveButton("Send", DialogInterface.OnClickListener{
                        dialog, whick ->
                        var msg = "From Student: " + input.text.toString()
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
                    intent = Intent(this,AboutUs::class.java)
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

    private fun feedbackAlertBox(username:String) {
        var dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.feedback)

        var rating = 0

        var cancel:Button = dialog.findViewById(R.id.cancel)
        var share:Button = dialog.findViewById(R.id.share)
        var review:EditText = dialog.findViewById(R.id.Review)

        var star1:Button = dialog.findViewById(R.id.Star1)
        var star2:Button = dialog.findViewById(R.id.Star2)
        var star3:Button = dialog.findViewById(R.id.Star3)
        var star4:Button = dialog.findViewById(R.id.Star4)
        var star5:Button = dialog.findViewById(R.id.Star5)

        star1.setOnClickListener {
            star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            rating = 1
        }
        star2.setOnClickListener {
            star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            rating = 2
        }
        star3.setOnClickListener {
            star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            rating = 3
        }
        star4.setOnClickListener {
            star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star0,0,0,0)
            rating = 4
        }
        star5.setOnClickListener {
            star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rating_star1,0,0,0)
            rating = 5
        }

        share.setOnClickListener {
            if (rating==0){
                Toast.makeText(this,"Give Some Rating!", Toast.LENGTH_SHORT).show()
            }
            else if(review.text.isEmpty()){
                Toast.makeText(this,"Enter Some Review!", Toast.LENGTH_SHORT).show()
            }
            else{
                ratingReview.addReview(username,rating,review.text.toString())
                Toast.makeText(this,"Thank You! for the Review!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()

    }

}
