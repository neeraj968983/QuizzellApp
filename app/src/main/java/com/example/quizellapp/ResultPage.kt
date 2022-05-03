package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.math.RoundingMode
import java.text.DecimalFormat

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)

        var i = intent
        var quizname = i.getStringExtra("QuizName")
        var marks = i.getIntExtra("marksObtained",0)

        var quizInfo:Quizdetail = quizInfoDatabase.quizAllData(quizname)

        var quizName:TextView = findViewById(R.id.QuizName)
        var category:TextView = findViewById(R.id.Category)
        var topic:TextView = findViewById(R.id.Topic)
        var maxmarks:TextView = findViewById(R.id.MaxMarks)
        var yourScore:TextView = findViewById(R.id.YourScore)
        var gradeDescription:TextView = findViewById(R.id.GradeDescription)
        var totalQuestions:TextView = findViewById(R.id.TotalQuestions)
        var attempted:TextView = findViewById(R.id.TotalAttempted)
        var correct:TextView = findViewById(R.id.Correct)
        var wrong:TextView = findViewById(R.id.Wrong)
        var skipped:TextView = findViewById(R.id.Skipped)
        var exitButton:TextView = findViewById(R.id.ExitButton)

        quizName.setText(""+quizname)
        category.setText(""+quizInfo.category)
        topic.setText(""+quizInfo.subject)
        maxmarks.setText(""+quizInfo.maxmarks)
        totalQuestions.setText(""+quizInfo.totalquestions)
        var percent:Double = ((marks.toDouble()/quizInfo.maxmarks.toDouble())*100)
        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.DOWN
        yourScore.setText(""+df.format(percent)+"%")
        attempted.setText(""+i.getIntExtra("attempted",0))
        correct.setText(""+i.getIntExtra("correct",0))
        wrong.setText(""+i.getIntExtra("wrong",0))
        skipped.setText(""+i.getIntExtra("skipped", 0))


        System.out.println("Marks = $marks \n MaxMarks = ${quizInfo.maxmarks} \n percntage is $percent")


        when{
            percent>90 -> {
                gradeDescription.setText("Outstanding")
            }
            (percent>80) and (percent<=90) ->{
                gradeDescription.setText("Excellent")
            }
            (percent>65) and (percent<=80) ->{
                gradeDescription.setText("Very good")
            }
            (percent>50) and (percent<=65) ->{
                gradeDescription.setText("Good")
            }
            (percent>=35) and (percent<=50) ->{
                gradeDescription.setText("Pass")
            }
            (percent<35) ->{
                gradeDescription.setText("Failed")
                gradeDescription.setTextColor(Color.RED)
            }
        }







        exitButton.setOnClickListener {
            var intent:Intent = Intent(this, StudentHomePage::class.java)
            startActivity(intent)
        }





    }
}