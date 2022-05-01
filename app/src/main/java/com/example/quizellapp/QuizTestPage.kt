package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView

class QuizTestPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_test_page)

        val questionDatabase:QuestionDatabase = QuestionDatabase(this)
        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)

        var quiztitle:TextView = findViewById(R.id.TestQuizName)
        var category:TextView = findViewById(R.id.Testcategory)
        var subject:TextView = findViewById(R.id.TestSubject)
        var duration:TextView = findViewById(R.id.TestDuration)



        var next:Button = findViewById(R.id.next)

        var i = intent
        var count = 0
        var quizname = i.getStringExtra("QuizName")
        var totalQuestion = quizInfoDatabase.totalQuestions(quizname)
        var quizInfo:Quizdetail = quizInfoDatabase.quizAllData(quizname)

        quiztitle.setText(""+quizInfo.quizname)
        category.setText(""+quizInfo.category)
        subject.setText(""+quizInfo.subject)
        duration.setText("Duration : "+quizInfo.duration+":00 minute")
        var ques:TextView = findViewById(R.id.question)
        var op1:Button = findViewById(R.id.Option1)
        var op2:Button = findViewById(R.id.Option2)
        var op3:Button = findViewById(R.id.Option3)
        var op4:Button = findViewById(R.id.Option4)

        var questions:ArrayList<QuestionAndOption> = questionDatabase.getQuestionList(quizname)
        System.out.println(questions[count].quest+questions[count].option1+questions[count].option2+questions[count].option3+questions[count].option4)


        if (totalQuestion>count){
            ques.setText(""+questions[count].quest)
            op1.setText(""+questions[count].option1)
            op2.setText(""+questions[count].option2)
            op3.setText(""+questions[count].option3)
            op4.setText(""+questions[count].option4)
        }

        next.setOnClickListener{
            if(totalQuestion-1>count){
                count++
                ques.setText(""+questions[count].quest)
                op1.setText(""+questions[count].option1)
                op2.setText(""+questions[count].option2)
                op3.setText(""+questions[count].option3)
                op4.setText(""+questions[count].option4)
            }
            else{
                next.setText("Finish")
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    var intent:Intent = Intent(this,StudentHomePage::class.java)
                    startActivity(intent)
                }, 5000)

            }
        }



    }
}