package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat


class QuizTestPage : AppCompatActivity() {
    lateinit var quizname:String
    var marksGained:Double = 0.0
    var attempted:Int = 0
    var correct:Int = 0
    var wrong:Int = 0
    var skipped:Int = 0
    lateinit var username:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_test_page)

        val questionDatabase:QuestionDatabase = QuestionDatabase(this)
        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)

        var quiztitle:TextView = findViewById(R.id.TestQuizName)
        var category:TextView = findViewById(R.id.Testcategory)
        var subject:TextView = findViewById(R.id.TestSubject)
        var MM:TextView = findViewById(R.id.Maxmarks)
        var duration:TextView = findViewById(R.id.TestDuration)
        var totalQuestions:TextView = findViewById(R.id.TotalQuestions)




        var next:Button = findViewById(R.id.next)

        var i = intent
        var count = 0
        quizname = i.getStringExtra("QuizName").toString()
        username = i.getStringExtra("username").toString()

        var totalQuestion = quizInfoDatabase.totalQuestions(quizname)
        var quizInfo:Quizdetail = quizInfoDatabase.quizAllData(quizname)

        val df = DecimalFormat("00")
        df.roundingMode = RoundingMode.DOWN
        var durationTime = quizInfo.duration.toLong()
        durationTime = durationTime*1000*60


        quiztitle.setText("" + quizInfo.quizname)
        category.setText("" + quizInfo.category)
        subject.setText("Topic: " + quizInfo.subject)
        MM.setText("Max. Marks: "+ quizInfo.maxmarks)
        totalQuestions.setText("Total Questions: "+totalQuestion)

        var timer = object : CountDownTimer(durationTime, 1000){
            override fun onTick(millisUntilFinished: Long) {
                if ((millisUntilFinished.toInt()<10000)&&(millisUntilFinished.toInt()>9000)){
                    duration.setTextColor(Color.RED)
                    val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val mp: MediaPlayer = MediaPlayer.create(applicationContext, alarmSound)
                    mp.start()
                }

                duration.setText("Time remaining: ${df.format(millisUntilFinished / 60000)}:" + df.format((millisUntilFinished / 1000) % 60))
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                duration.setText("Time's Up")
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    goStudentHomePage(marksGained,attempted,correct,wrong,skipped,username)
                }, 2000)
            }

        }.start()

        var ques:TextView = findViewById(R.id.question)
        var op1:Button = findViewById(R.id.Option1)
        var op2:Button = findViewById(R.id.Option2)
        var op3:Button = findViewById(R.id.Option3)
        var op4:Button = findViewById(R.id.Option4)
        var optionSelected = "E"
        marksGained = 0.0
        var maxMarks:Int = quizInfo.maxmarks
        var marksPerQuestion:Double = maxMarks.toDouble()/totalQuestion.toDouble()


        var questions:ArrayList<QuestionAndOption> = questionDatabase.getQuestionList(quizname)


        if (totalQuestion>count){
            ques.setText("Question ${count+1}: \n" + questions[count].quest)
            op1.setText("" + questions[count].option1)
            op2.setText("" + questions[count].option2)
            op3.setText("" + questions[count].option3)
            op4.setText("" + questions[count].option4)
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
                        attempted = attempted+1
                        correct = correct+1
                        op1.setBackgroundColor(Color.GREEN)
                        op1.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                        optionSelected="E"
                    }
                    "B" -> {
                        attempted = attempted+1
                        correct = correct+1
                        op2.setBackgroundColor(Color.GREEN)
                        op2.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                        optionSelected="E"
                    }
                    "C" -> {
                        attempted = attempted+1
                        correct = correct+1
                        op3.setBackgroundColor(Color.GREEN)
                        op3.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        op4.setTextColor(Color.WHITE)
                        optionSelected="E"
                    }
                    "D" -> {
                        attempted = attempted+1
                        correct = correct+1
                        op4.setBackgroundColor(Color.GREEN)
                        op4.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        op2.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        op3.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        op1.setTextColor(Color.WHITE)
                        optionSelected="E"
                    }

                }
            }
            else{
                when(optionSelected){
                    "A" -> {
                        attempted = attempted+1
                        wrong = wrong+1
                        op1.setTextColor(Color.WHITE)
                        op1.setBackgroundColor(Color.RED)
                        optionSelected="E"
                    }
                    "B" -> {
                        attempted = attempted+1
                        wrong = wrong+1
                        op2.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.RED)
                        optionSelected="E"
                    }
                    "C" -> {
                        attempted = attempted+1
                        wrong = wrong+1
                        op3.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.RED)
                        optionSelected="E"
                    }
                    "D" -> {
                        attempted = attempted+1
                        wrong = wrong+1
                        op4.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.RED)
                        optionSelected="E"
                    }
                    "E" ->{
                        skipped = skipped+1
                        op1.setBackgroundColor(Color.rgb(255,165,0))
                        op1.setTextColor(Color.WHITE)
                        op2.setBackgroundColor(Color.rgb(255,165,0))
                        op2.setTextColor(Color.WHITE)
                        op3.setBackgroundColor(Color.rgb(255,165,0))
                        op3.setTextColor(Color.WHITE)
                        op4.setBackgroundColor(Color.rgb(255,165,0))
                        op4.setTextColor(Color.WHITE)
                    }
                }
            }
            if(totalQuestion-1>count){
                count++
                Handler(Looper.getMainLooper()).postDelayed({
                    ques.setText("Question ${count+1}: \n" + questions[count].quest)
                    op1.setText("" + questions[count].option1)
                    op2.setText("" + questions[count].option2)
                    op3.setText("" + questions[count].option3)
                    op4.setText("" + questions[count].option4)
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
                timer.cancel()
                System.out.println("Total marks: $marksGained")
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    goStudentHomePage(marksGained,attempted,correct,wrong,skipped,username)
                }, 2000)

            }
        }



    }

    private fun goStudentHomePage(marksObtained:Double, attempted:Int, correct:Int, wrong:Int, skipped:Int, username:String) {
        System.out.println("Marks obtained in quiz test page is: $marksObtained")
        var intent:Intent = Intent(this, ResultPage::class.java)
        intent.putExtra("QuizName", quizname)
        intent.putExtra("attempted", attempted)
        intent.putExtra("correct",correct)
        intent.putExtra("wrong", wrong)
        intent.putExtra("skipped", skipped)
        intent.putExtra("marksObtained",marksObtained)
        intent.putExtra("username", username)
        System.out.println("Attempted = $attempted \n Correct = $correct \n Wrong = $wrong \n Skipped = $skipped")
        startActivity(intent)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Moving back is not allowed!", Toast.LENGTH_SHORT).show()
    }
}