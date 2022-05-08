package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class QuizCreationInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_creation_info)

        var cashrate:Double
        var coinrate:Int
        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        val accountSummaryDatabase:AccountSummaryDatabase = AccountSummaryDatabase(this)
        var accountDataFromQuizCreation:AccountSummaryDataClass

        val i = intent
        val next:Button = findViewById(R.id.Next)
        val back:Button = findViewById(R.id.Back)

        val username = i.getStringExtra("usernameFromLogin")
        val mentorName:TextView = findViewById(R.id.MentorName)
        mentorName.setText(""+i.getStringExtra("MentorName"))
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
            onBackPressed()
        }
        next.setOnClickListener(){
            var quizDetail:Quizdetail = Quizdetail(username,mentorName.text.toString(),quizName.text.toString(),spin.selectedItem.toString(),subject.text.toString(),duration.text.toString().toInt(),date.text.toString(),time.text.toString(),Attempts.selectedItem.toString().toInt(),quizType.selectedItem.toString(),quizPurpose.selectedItem.toString(),totalQuestion.text.toString().toInt(),maximumMarks.text.toString().toInt(),guideline.text.toString())
            quizInfoDatabase.addQuizDetail(quizDetail)
            if (quizType.selectedItem.equals("Free")){
                accountDataFromQuizCreation = AccountSummaryDataClass(i.getStringExtra("usernameFromLogin").toString(),quizName.text.toString(),spin.selectedItem.toString(), quizType.selectedItem.toString(),totalQuestion.text.toString().toInt(),1,0,0.0,0)

            }
            else{
                when(spin.selectedItem.toString()){
                    "Aptitude" -> {
                        cashrate = 2.0
                        coinrate = 10
                    }
                    "Banking Exam" -> {
                        cashrate = 4.0
                        coinrate = 16
                    }
                    "Biology" ->{
                        cashrate = 3.0
                        coinrate = 5
                    }
                    "CDS Related" -> {
                        cashrate = 4.0
                        coinrate = 8
                    }
                    "Chemistry" -> {
                        cashrate = 6.0
                        coinrate = 12
                    }
                    "Civil Services" -> {
                        cashrate = 10.0
                        coinrate = 20
                    }
                    "Coding" -> {
                        cashrate = 5.0
                        coinrate = 15
                    }
                    "Data Structure" -> {
                        cashrate = 14.0
                        coinrate = 30
                    }
                    "General Knowledge" -> {
                        cashrate = 4.0
                        coinrate = 10
                    }
                    "Interview Related" -> {
                        cashrate = 7.0
                        coinrate = 28
                    }
                    "Literature and Grammar" -> {
                        cashrate = 2.0
                        coinrate = 6
                    }
                    "Logical Reasoning" -> {
                        cashrate = 6.0
                        coinrate = 14
                    }
                    "Mathematics" -> {
                        cashrate = 5.0
                        coinrate = 10
                    }
                    "NDA Related" -> {
                        cashrate = 8.0
                        coinrate = 20
                    }
                    "Physics" -> {
                        cashrate = 4.0
                        coinrate = 6
                    }
                    "SSC" -> {
                        cashrate = 20.0
                        coinrate = 30
                    }
                    else -> {
                        cashrate = 0.0
                        coinrate = 0
                    }
                }
                cashrate *= totalQuestion.text.toString().toDouble()
                accountDataFromQuizCreation = AccountSummaryDataClass(username.toString(),quizName.text.toString(),spin.selectedItem.toString(), quizType.selectedItem.toString(),totalQuestion.text.toString().toInt(),0,1,cashrate,coinrate)
            }

            accountSummaryDatabase.addDataToAccountSummary(accountDataFromQuizCreation)
            var intent:Intent = Intent(this,QuizQuestionCreation::class.java)
            intent.putExtra("usernameFromLogin",username)
            intent.putExtra("TotalQuestion",totalQuestion.text.toString().toInt())
            intent.putExtra("QuizName",quizName.text.toString())
            intent.putExtra("Category",spin.selectedItem.toString())
            intent.putExtra("Count",1)
            startActivity(intent)
        }
    }
}