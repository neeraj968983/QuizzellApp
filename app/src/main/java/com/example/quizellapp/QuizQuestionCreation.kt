package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class QuizQuestionCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_creation)

        var questionDatabase:QuestionDatabase = QuestionDatabase(this)

        var i = intent
        var totalCount = i.getIntExtra("TotalQuestion",0)
        var count = 1

        var nextques:Button = findViewById(R.id.NextButton)

        var quizname:TextView = findViewById(R.id.quizName)
        var quizName = i.getStringExtra("QuizName")
        quizname.setText(""+quizName)
        var questionNumber:TextView = findViewById(R.id.QuestionNumber)
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

        nextques.setOnClickListener(){

            if(totalCount>=count){
                var question:Question = Question(quizName,ques.text.toString(),opt1.text.toString(),opt2.text.toString(),opt3.text.toString(),opt4.text.toString(),correctOption.selectedItem.toString())
                questionDatabase.addQuestion(question)
                questionNumber.setText("Question "+count+":")
                count++
            }
            else{
                nextques.setText("Done!")
                count=1
                var intent:Intent = Intent(this,MentorDashboard::class.java)
                startActivity(intent)
            }



            }
        }
    }
}