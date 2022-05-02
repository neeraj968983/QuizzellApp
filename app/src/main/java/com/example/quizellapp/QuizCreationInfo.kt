package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class QuizCreationInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_creation_info)
        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)

        val i = intent
        val next:Button = findViewById(R.id.Next)
        val back:Button = findViewById(R.id.Back)

        val username = i.getStringExtra("usernameFromLogin")
        val mentorName:TextView = findViewById(R.id.MentorName)
        mentorName.setText("owner: "+i.getStringExtra("MentorName"))
        val spin: Spinner = findViewById(R.id.spinner)
        val Attempts: Spinner = findViewById(R.id.spinner2)
        val quizType:Spinner = findViewById(R.id.spinner3)
        val quizPurpose:Spinner = findViewById(R.id.spinner4)
        val quizName:EditText = findViewById(R.id.QuizName)
        val subject:EditText = findViewById(R.id.Subject)
        val duration:EditText = findViewById(R.id.Duration)
        val date:EditText = findViewById(R.id.Date)
        val time:EditText = findViewById(R.id.Time)
        val totalQuestion:EditText = findViewById(R.id.TotalQuestion)
        val maximumMarks:EditText = findViewById(R.id.MaxMarks)
        val guideline:EditText = findViewById(R.id.Guideline)

        ArrayAdapter.createFromResource(this,R.array.Category, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin.adapter=adapter
            spin.setSelection(0,false)
        }

        ArrayAdapter.createFromResource(this,R.array.Attempts,android.R.layout.simple_spinner_dropdown_item).also{
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Attempts.adapter = adapter
            Attempts.setSelection(0,false)
        }

        ArrayAdapter.createFromResource(this,R.array.QuizType,android.R.layout.simple_spinner_dropdown_item).also{
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            quizType.adapter = adapter
            quizType.setSelection(0,false)
        }

        ArrayAdapter.createFromResource(this,R.array.QuizPurpose,android.R.layout.simple_spinner_dropdown_item).also{
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            quizPurpose.adapter = adapter
            quizPurpose.setSelection(0,false)
        }

        back.setOnClickListener{
            var intent = Intent(this, MentorDashboard::class.java)
            startActivity(intent)
        }
        next.setOnClickListener(){
            var quizDetail:Quizdetail = Quizdetail(username,mentorName.text.toString(),quizName.text.toString(),spin.selectedItem.toString(),subject.text.toString(),duration.text.toString().toInt(),date.text.toString(),time.text.toString(),Attempts.selectedItem.toString().toInt(),quizType.selectedItem.toString(),quizPurpose.selectedItem.toString(),totalQuestion.text.toString().toInt(),maximumMarks.text.toString().toInt(),guideline.text.toString())
            quizInfoDatabase.addQuizDetail(quizDetail)
            var intent:Intent = Intent(this,QuizQuestionCreation::class.java)
            intent.putExtra("usernameFromLogin",username)
            intent.putExtra("TotalQuestion",totalQuestion.text.toString().toInt())
            intent.putExtra("QuizName",quizName.text.toString())
            startActivity(intent)
        }
    }
}