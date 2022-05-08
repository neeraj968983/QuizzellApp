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
import androidx.core.view.GravityCompat
import androidx.core.view.marginLeft
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MentorDashboard : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var count = 1
    var ratingReview = RatingReview(this)
    lateinit var username:String

    var data:Array<Int> = Array(3){0}
    var data2:Array<Int> = Array(3){0}
    lateinit var data3:cashCoin
    var totalcash:Double = 0.0

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
        var accountSummaryDatabase = AccountSummaryDatabase(this)
        var accounntSummaryDatabase2 = AccounntSummaryDatabase2(this)
        var scoreDatabase = ScoreDatabase(this)
        var cashAccounts = cashAccount(this)


        var i = intent
        username = i.getStringExtra("usernameFromLogin").toString()
        var (a,b,c,d) = userextradetails.fetchData(i.getStringExtra("usernameFromLogin"))
        System.out.println("A = $a\n B = $b\n C = $c\n D = $d")
        var mentorName = (a.toString() + " " + b.toString())

        data = accountSummaryDatabase.getTotalQuizList(mentorName) //Data for Account Summary
        data2 = accounntSummaryDatabase2.getData(mentorName)
        data3 = accountSummaryDatabase.getTotalCashCoinQuizName(i.getStringExtra("usernameFromLogin").toString())

        totalcash = cashAccounts.totalCash(i.getStringExtra("usernameFromLogin").toString())

        toggle = ActionBarDrawerToggle(this,drawerLayout,findViewById(R.id.toolbar),R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        var candidatename = arrayListOf<String>()
        var candidateattempts = arrayListOf<Int>()
        var candidatescore = arrayListOf<Double>()


        var data = accountSummaryDatabase.getMentorQuizList(i.getStringExtra("usernameFromLogin").toString())

        var quiznames = arrayListOf<String>()
        var categories = arrayListOf<String>()
        var quiztype = arrayListOf<String>()
        var getattempts:Int
        var studentAttempts = arrayListOf<Int>()


        for (j in 0..(data.size-1)){
            getattempts = accounntSummaryDatabase2.getAttemptsByList((data[j].quizname).toString())
            studentAttempts.add(getattempts)
            quiznames.add((data[j].quizname).toString())
            quiztype.add((data[j].quiztype).toString())
            categories.add((data[j].category).toString())
        }

        val listView:ListView = findViewById(R.id.MentorQuizList)
        val listView2:ListView = findViewById(R.id.StudentList)
        val myAdaptor = CustomAdaptorMentorQuizList(this,quiznames,categories,quiztype,studentAttempts)
        listView.adapter = myAdaptor
        listView.setOnItemClickListener { parent, view, position, id ->
            var data2 = accounntSummaryDatabase2.getCandidateNameAndAttempts(parent.getItemAtPosition(position).toString())
            var QUIZNAME = parent.getItemAtPosition(position).toString()

            for (j in 0..(data2.size-1)){
                var score = scoreDatabase.getScore((data2[j].candidateName).toString(),QUIZNAME)
                candidatescore.add(score)
                candidatename.add((data2[j].candidateName).toString())
                candidateattempts.add((data2[j].attemptsGiven).toInt())
            }
            val myAdaptor2 = CustomAdaptorStudentList(this,candidatename,candidateattempts,candidatescore)
            listView2.adapter = myAdaptor2
            listView2.setOnItemClickListener{parent, view, position,id ->
                System.out.println("Candidate name is: ${parent.getItemAtPosition(position)}............/////")
            }

        }


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
                R.id.Your_Quizzes -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.QA -> Toast.makeText(applicationContext, "Question/Answer", Toast.LENGTH_LONG).show()
                R.id.Feedback -> {
                    feedbackAlertBox(username)
                }
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
        var totalfreeQuiz:TextView = dialog.findViewById(R.id.totalquizzes)
        var totalpaidQuiz:TextView = dialog.findViewById(R.id.FreeQuizzes)
        var totalquiz:TextView = dialog.findViewById(R.id.PaidQuizzes)
        var freeStudentAttempts:TextView = dialog.findViewById(R.id.StudentAttempted)
        var paidStudentAttempts:TextView = dialog.findViewById(R.id.PaidQuizzesAttempted)
        var paidTotalAttempts:TextView = dialog.findViewById(R.id.PaidTotalAttempts)
        var coins:TextView = dialog.findViewById(R.id.Coins)
        var cash:TextView = dialog.findViewById(R.id.Cash)


        totalfreeQuiz.setText(""+data[0])
        totalpaidQuiz.setText(""+data[1])
        totalquiz.setText(""+data[2])
        freeStudentAttempts.setText(""+data2[0])
        paidStudentAttempts.setText(""+data2[1])
        paidTotalAttempts.setText(""+data2[2])
        coins.setText(""+data2[2]*data3.coin)
        cash.setText(""+totalcash)

        System.out.println("Student = ${data2[1]} and Cash is ${data3.cash}")


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