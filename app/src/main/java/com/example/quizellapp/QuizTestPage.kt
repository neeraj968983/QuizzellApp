package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.math.RoundingMode
import java.text.DecimalFormat

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

        var minute:Int
        var second:Int = 59
        val df = DecimalFormat("00")
        df.roundingMode = RoundingMode.DOWN
        var durationTime = quizInfo.duration.toLong()
        durationTime = durationTime*1000*60
        minute = (durationTime.toInt()/60000)

        System.out.println(" minute: $minute\n seconds: $second")

        quiztitle.setText(""+quizInfo.quizname)
        category.setText(""+quizInfo.category)
        subject.setText(""+quizInfo.subject)

        object : CountDownTimer(durationTime, 1000){
            override fun onTick(millisUntilFinished: Long) {


                duration.setText("Time remaining: ${df.format(millisUntilFinished/60000)}:" + df.format((millisUntilFinished/1000)%60))
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                duration.setText("done!")
            }

        }.start()

        var ques:TextView = findViewById(R.id.question)
        var op1:Button = findViewById(R.id.Option1)
        var op2:Button = findViewById(R.id.Option2)
        var op3:Button = findViewById(R.id.Option3)
        var op4:Button = findViewById(R.id.Option4)
        var optionSelected = "E"
        var marksGained = 0
        var maxMarks:Int = quizInfo.maxmarks
        var marksPerQuestion = maxMarks/totalQuestion

        System.out.println("maxMarks : $maxMarks \n MarksPerQuestion : $marksPerQuestion")

        var questions:ArrayList<QuestionAndOption> = questionDatabase.getQuestionList(quizname)


        if (totalQuestion>count){
            ques.setText(""+questions[count].quest)
            op1.setText(""+questions[count].option1)
            op2.setText(""+questions[count].option2)
            op3.setText(""+questions[count].option3)
            op4.setText(""+questions[count].option4)
        }

        op1.setOnClickListener{
            op1.setBackgroundColor(Color.GRAY)
            op2.setBackgroundColor(Color.WHITE)
            op3.setBackgroundColor(Color.WHITE)
            op4.setBackgroundColor(Color.WHITE)
            optionSelected = "A"
        }
        op2.setOnClickListener{
            op1.setBackgroundColor(Color.WHITE)
            op2.setBackgroundColor(Color.GRAY)
            op3.setBackgroundColor(Color.WHITE)
            op4.setBackgroundColor(Color.WHITE)
            optionSelected = "B"
        }
        op3.setOnClickListener{
            op1.setBackgroundColor(Color.WHITE)
            op2.setBackgroundColor(Color.WHITE)
            op3.setBackgroundColor(Color.GRAY)
            op4.setBackgroundColor(Color.WHITE)
            optionSelected = "C"
        }
        op4.setOnClickListener{
            op1.setBackgroundColor(Color.WHITE)
            op2.setBackgroundColor(Color.WHITE)
            op3.setBackgroundColor(Color.WHITE)
            op4.setBackgroundColor(Color.GRAY)
            optionSelected = "D"
        }



        next.setOnClickListener{
            if(optionSelected.equals(questions[count].answer)){
                marksGained = marksGained + marksPerQuestion
                when(optionSelected){
                    "A" -> {
                        op1.setBackgroundColor(Color.GREEN)
                        op1.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                    }
                    "B" -> {
                        op2.setBackgroundColor(Color.GREEN)
                        op2.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                    }
                    "C" -> {
                        op3.setBackgroundColor(Color.GREEN)
                        op3.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                    }
                    "D" -> {
                        op4.setBackgroundColor(Color.GREEN)
                        op4.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                    }

                }
            }
            else{
                when(optionSelected){
                    "A" ->{
                        op1.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                    }
                    "B" ->{
                        op2.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                    }
                    "C" ->{
                        op3.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                    }
                    "D" ->{
                        op4.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                    }
                }
            }
            System.out.println("Question $count Marks : $marksPerQuestion and you got total marks till $marksGained")
            if(totalQuestion-1>count){
                count++
                Handler(Looper.getMainLooper()).postDelayed({
                    ques.setText(""+questions[count].quest)
                    op1.setText(""+questions[count].option1)
                    op2.setText(""+questions[count].option2)
                    op3.setText(""+questions[count].option3)
                    op4.setText(""+questions[count].option4)
                    op1.setBackgroundColor(Color.WHITE)
                    op1.setTextColor(Color.BLACK)
                    op2.setBackgroundColor(Color.WHITE)
                    op2.setTextColor(Color.BLACK)
                    op3.setBackgroundColor(Color.WHITE)
                    op3.setTextColor(Color.BLACK)
                    op4.setBackgroundColor(Color.WHITE)
                    op4.setTextColor(Color.BLACK)
                }, 2000)

            }
            else{
                next.setText("Finish")
                System.out.println("Total marks: $marksGained")
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    var intent:Intent = Intent(this,StudentHomePage::class.java)
                    startActivity(intent)
                }, 2000)

            }
        }



    }

    override fun onBackPressed() {
        Toast.makeText(this,"Moving back is not allowed!",Toast.LENGTH_SHORT).show()
    }
}