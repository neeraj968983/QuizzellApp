package com.example.quizellapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.FieldPosition

class FinishedQuizzes : AppCompatActivity() {
    var quizInfoDatabase = QuizInfoDatabase(this)
    var scoreDatabase = ScoreDatabase(this)
    var scores:ArrayList<Double> = ArrayList()

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_quizzes)

        var mentorNames:ArrayList<String> =ArrayList()
        var quizNames:ArrayList<String> =ArrayList()
        var categories:ArrayList<String> =ArrayList()
        var quiztypes:ArrayList<String> = ArrayList()


        var i = intent
        var username = i.getStringExtra("username")

        var scoreDatabase = ScoreDatabase(this)

        var quizAndScoreListWithZeroAttemptsLeft = scoreDatabase.getQuizNameAndScoreWithZeroAttemptsLeft(username)
        for (j in 0..(quizAndScoreListWithZeroAttemptsLeft.size-1)){
            var (mentorname,quizname,category,quiztype) = quizInfoDatabase.getDetailOfFinnishedQuiz(quizAndScoreListWithZeroAttemptsLeft[j].quizname.toString())
            mentorNames.add(mentorname)
            quizNames.add(quizAndScoreListWithZeroAttemptsLeft[j].quizname.toString())
            categories.add(category)
            quiztypes.add(quiztype)
            scores.add(quizAndScoreListWithZeroAttemptsLeft[j].score)
        }

        var drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        var navView: NavigationView = findViewById(R.id.nav_view)


        toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.toolbar), R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)




        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.newQuizzes -> {
                    var intent: Intent = Intent(this, NewQuizzes::class.java)
                    startActivity(intent)
                }
                R.id.back -> {
                    onBackPressed()
                }
            }
            true
        }

        val listView:ListView = findViewById(R.id.FinishedQuizListView)
        val myAdaptor = CustomAdaptorFinishedQuiz(this,quizNames,mentorNames,quiztypes,categories,scores)
        listView.adapter = myAdaptor
        listView.setBackgroundColor(Color.parseColor("#81FFFFFF"))
        listView.setOnItemClickListener { parent, view, position, id ->
            quizdetail(username.toString(),(parent.getItemAtPosition(position).toString()),position)
        }

    }
    fun quizdetail(username:String,quizname:String,position:Int){
        var dialog: Dialog = Dialog(this)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.finishedquiz_alertbox)

        var data = scoreDatabase.getScoreAllDetails(username,quizname)


        var userName: TextView = dialog.findViewById(R.id.AlertBoxUserName)
        var quizName: TextView = dialog.findViewById(R.id.AlertBoxQuizName)
        var category: TextView = dialog.findViewById(R.id.AlertBoxCategory)
        var topic: TextView = dialog.findViewById(R.id.AlertBoxTopic)
        var maxmarks: TextView = dialog.findViewById(R.id.AlertBoxMaxMarks)
        var yourScore: TextView = dialog.findViewById(R.id.AlertBoxYourScore)
        var gradeDescription: TextView = dialog.findViewById(R.id.AlertBoxGradeDescription)
        var totalQuestions: TextView = dialog.findViewById(R.id.AlertBoxTotalQuestions)
        var attempted: TextView = dialog.findViewById(R.id.AlertBoxTotalAttempted)
        var correct: TextView = dialog.findViewById(R.id.AlertBoxCorrect)
        var wrong: TextView = dialog.findViewById(R.id.AlertBoxWrong)
        var skipped: TextView = dialog.findViewById(R.id.AlertBoxSkipped)
        var exitButton: TextView = dialog.findViewById(R.id.AlertBoxExitButton)



        userName.setText(""+username)
        quizName.setText(""+quizname)
        category.setText(""+data.category)
        topic.setText(""+data.topic)
        maxmarks.setText(""+data.maxmarks)
        totalQuestions.setText(""+data.totalQuestion)
        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.DOWN
        yourScore.setText(""+df.format(data.score)+"%")
        attempted.setText(""+data.attemptedQuestions)
        correct.setText(""+data.correctAnswer)
        wrong.setText(""+data.wrongAnswer)
        skipped.setText(""+data.skippedQuestions)

        when{
            (data.score)>90 -> {
                gradeDescription.setText("Outstanding")
            }
            ((data.score)>80) and ((data.score)<=90) ->{
                gradeDescription.setText("Excellent")
            }
            ((data.score)>65) and ((data.score)<=80) ->{
                gradeDescription.setText("Very good")
            }
            ((data.score)>50) and ((data.score)<=65) ->{
                gradeDescription.setText("Good")
            }
            ((data.score)>=35) and ((data.score)<=50) ->{
                gradeDescription.setText("Pass")
            }
            ((data.score)<35) ->{
                gradeDescription.setText("Failed")
                gradeDescription.setTextColor(Color.RED)
            }
        }

        exitButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}