package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import java.math.RoundingMode
import java.text.DecimalFormat

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        var scoreDatabase:ScoreDatabase = ScoreDatabase(this)


        var i = intent
        var quizname = i.getStringExtra("QuizName")
        var totalAttempts = quizInfoDatabase.totalAttempts(quizname)
        var marks = i.getDoubleExtra("marksObtained",0.0)
        var userName = i.getStringExtra("username")

        var quizInfo:Quizdetail = quizInfoDatabase.quizAllData(quizname)

        var username:TextView = findViewById(R.id.UserName)
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

        var attemptedQuestion = i.getIntExtra("attempted",0)
        var correctAnswer = i.getIntExtra("correct",0)
        var wrongAnswer = i.getIntExtra("wrong",0)
        var skippedQuestions = i.getIntExtra("skipped", 0)

        username.setText(""+userName)
        quizName.setText(""+quizname)
        category.setText(""+quizInfo.category)
        topic.setText(""+quizInfo.subject)
        maxmarks.setText(""+quizInfo.maxmarks)
        totalQuestions.setText(""+quizInfo.totalquestions)
        System.out.println("marks in result page is $marks")
        var percent:Double = ((marks/quizInfo.maxmarks.toDouble())*100)
        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.DOWN
        yourScore.setText(""+df.format(percent)+"%")
        attempted.setText(""+attemptedQuestion)
        correct.setText(""+correctAnswer)
        wrong.setText(""+wrongAnswer)
        skipped.setText(""+skippedQuestions)


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
            var detail = scoreDatabase.checkDetail(userName, quizname)
            var score:Double = detail.score
            var check:Int = detail.check
            var attemptsLeft:Int = detail.attemptsLeft
            System.out.println("Score : $score \n Check : $check \n Attempts left : $attemptsLeft")
            var updatedScore:Double
            if(check==1){
                if(attemptsLeft>0){
                    if(score>percent){
                        scoreDatabase.updateAttempt(userName,quizname,(attemptsLeft-1))
                    }
                    else{
                        updatedScore = percent
                        scoreDatabase.updateAteempt(userName,quizname,(attemptsLeft-1),updatedScore,attemptedQuestion,correctAnswer,wrongAnswer,skippedQuestions)
                    }

                    Toast.makeText(this,"Your Attempt reduce by 1 and score updated",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"This Attempt is not countable",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                scoreDatabase.addResult(resultDetail(userName,quizname,quizInfo.category,quizInfo.subject,quizInfo.maxmarks,quizInfo.totalquestions,percent,i.getIntExtra("attempted",0),i.getIntExtra("correct",0),i.getIntExtra("wrong",0),i.getIntExtra("skipped",0),(totalAttempts-1)))
                System.out.println("username: $userName \n quizname: $quizname \n category ${quizInfo.category} \n subject ${quizInfo.subject} \n maxmarks: ${quizInfo.maxmarks} \n totalquestions: ${quizInfo.totalquestions} \n percent: $percent, \n Question Attempted: ${i.getIntExtra("attempted",0)} \n Correct: ${i.getIntExtra("correct",0)}  \n Worng: ${i.getIntExtra("wrong",0)} \n Skipped: ${i.getIntExtra("skipped",0)} \n attempts left${(totalAttempts-1)}")
                Toast.makeText(this,"Your Score added for first time in Score Board",Toast.LENGTH_SHORT).show()
            }

            var intent:Intent = Intent(this, StudentHomePage::class.java)
            intent.putExtra("usernameFromLogin",userName)
            startActivity(intent)
        }





    }
}