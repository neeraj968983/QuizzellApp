package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class QuizInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_info)
        var i = intent

        var quizname = i.getStringExtra("QuizName")
        var username = i.getStringExtra("username")

        var quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        var scoreDatabase = ScoreDatabase(this)

        var (check,attemptsLefts) = scoreDatabase.checkDetail(username,quizname)

        if(check==0){
            attemptsLefts = quizInfoDatabase.totalAttempts(quizname)
        }


        var quizName:TextView = findViewById(R.id.QuizName)
        quizName.setText(""+quizname)
        var mentorName:TextView = findViewById(R.id.mentorname)
        var category:TextView = findViewById(R.id.category)
        var attemptLeft:TextView = findViewById(R.id.Attemptsleft)
        var quizType:TextView = findViewById(R.id.quiztype)
        var maxmarks:TextView = findViewById(R.id.maxmarks)
        var guideline:TextView = findViewById(R.id.guidelinebox)

        var start:Button = findViewById(R.id.buttonstart)
        val back:Button = findViewById(R.id.buttonback)



        var (a,b,c,d,e) = quizInfoDatabase.quizInformation(quizname)
        mentorName.setText(""+a)
        category.setText(""+b)
        attemptLeft.setText(""+attemptsLefts)
        quizType.setText(""+c)
        maxmarks.setText(""+d)
        guideline.setText(""+e)

        start.setOnClickListener{
            if(attemptsLefts==0){
                Toast.makeText(this, "Your Attempts exhausted! You can't give this quiz",Toast.LENGTH_LONG).show()
            }
            else{
                var intent:Intent = Intent(this,QuizTestPage::class.java)
                intent.putExtra("QuizName",quizname)
                intent.putExtra("username", username)
                startActivity(intent)
            }

        }


        back.setOnClickListener{
            onBackPressed()
        }
    }
}


