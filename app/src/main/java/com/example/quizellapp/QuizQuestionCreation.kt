package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*

class QuizQuestionCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_creation)

        var questionDatabase:QuestionDatabase = QuestionDatabase(this)

        var i = intent
        var totalCount = i.getIntExtra("TotalQuestion",0)
        var username = i.getStringExtra("usernameFromLogin")
        var count:Int = 1
        var flag = 0

        var nextques:Button = findViewById(R.id.NextButton)

        var quizname:TextView = findViewById(R.id.quizName)
        var quizName = i.getStringExtra("QuizName")
        quizname.setText(""+quizName)
        var questionNumber:TextView = findViewById(R.id.QuestionNumber)
        System.out.println("total count is $totalCount")
        var ques:EditText = findViewById(R.id.Question)
        var opt1:EditText = findViewById(R.id.Option1)
        var opt2:EditText = findViewById(R.id.Option2)
        var opt3:EditText = findViewById(R.id.Option3)
        var opt4:EditText = findViewById(R.id.Option4)
        val correctOption:Spinner = findViewById(R.id.CorrectOption)

        ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_dropdown_item).also{
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            correctOption.adapter = adapter
            correctOption.setSelection(0,true)

            questionNumber.setText("Question $count: ")
        nextques.setOnClickListener(){
            if(totalCount>count){
                var question:Question = Question(quizName,ques.text.toString(),opt1.text.toString(),opt2.text.toString(),opt3.text.toString(),opt4.text.toString(),correctOption.selectedItem.toString())
                questionDatabase.addQuestion(question)
                count += 1
                System.out.println("total Count : $totalCount and count : $count")
                questionNumber.setText("Question $count: ")
                ques.setText("")
                opt1.setText("")
                opt2.setText("")
                opt3.setText("")
                opt4.setText("")
                flag = 1
            }
            else{
                Handler(Looper.getMainLooper()).postDelayed({
                    nextques.setText("Done!")
                    var intent:Intent = Intent(this,MentorDashboard::class.java)
                    intent.putExtra("usernameFromLogin", username )
                    startActivity(intent)
                }, 2000)
            }



            }
        }
    }
}