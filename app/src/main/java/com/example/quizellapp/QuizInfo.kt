package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
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
        var accounntSummaryDatabase2 = AccounntSummaryDatabase2(this)
        var notesDatabase = NotesDatabase(this)
        var dataFromQuizInfo:AccountSummaryDataClass2
        var scoreDatabase = ScoreDatabase(this)

        var (check,attemptsLefts) = scoreDatabase.checkDetail(username,quizname)

        if(check==0){
            attemptsLefts = quizInfoDatabase.totalAttempts(quizname)
        }

        var paidquiztotalattempt:Int = 0



        var quizName:TextView = findViewById(R.id.QuizName)
        quizName.setText(""+quizname)
        var mentorName:TextView = findViewById(R.id.mentorname)
        var category:TextView = findViewById(R.id.category)
        var attemptLeft:TextView = findViewById(R.id.Attemptsleft)
        var quizType:TextView = findViewById(R.id.quiztype)
        var maxmarks:TextView = findViewById(R.id.maxmarks)
        var totalQuestion:TextView = findViewById(R.id.totalQuestions)
        var guideline:TextView = findViewById(R.id.guidelinebox)

        var start:Button = findViewById(R.id.buttonstart)
        val back:Button = findViewById(R.id.buttonback)
        val seeNotes:Button = findViewById(R.id.SeeNotes)

        var checkNotes:Boolean = notesDatabase.checkNotesAvailable(quizname.toString())

        if(checkNotes){
            seeNotes.isEnabled = true
            seeNotes.setTextColor(Color.WHITE)
            seeNotes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.view_eye_enabled,0,0,0)
        }
        else{
            seeNotes.isEnabled = false
            seeNotes.setTextColor(Color.parseColor("#434343"))
            seeNotes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.view_eye,0,0,0)
        }



        var totalQuestions = quizInfoDatabase.totalQuestions(quizname)

        var mentorUserName:String = quizInfoDatabase.getMentorUserName(quizname)

        var (a,b,c,d,e) = quizInfoDatabase.quizInformation(quizname)
        mentorName.setText(""+a)
        category.setText(""+b)
        attemptLeft.setText(""+attemptsLefts)
        quizType.setText(""+c)
        maxmarks.setText(""+d)
        guideline.setText(""+e)
        totalQuestion.setText(""+totalQuestions)

        seeNotes.setOnClickListener{
            var intent = Intent(this,NotesView::class.java)
            intent.putExtra("QuizName",quizname)
            intent.putExtra("username", username)
            intent.putExtra("AttemptsLeft",attemptsLefts)
            startActivity(intent)
        }

        System.out.println("Attempts Left: $attemptsLefts and Quiz Type: $c")

        start.setOnClickListener{
            if(attemptsLefts==0){
                Toast.makeText(this, "Your Attempts exhausted! You can't give this quiz",Toast.LENGTH_LONG).show()
//                dataFromQuizInfo = AccountSummaryDataClass2(username,a.toString(),b.toString(),1,0,c.toString(),quizname)
            }
            else{
                System.out.println("Still Attempts left.......................//")
                if (c.toString().equals("Free")){
                    System.out.println("Free quiz")
                    if(accounntSummaryDatabase2.checkCandidate(username,quizname)){
                        System.out.println("User Already there! ${accounntSummaryDatabase2.checkCandidate(username,quizname)}")
                    }
                    else {
                        System.out.println("New User.......")
                        dataFromQuizInfo = AccountSummaryDataClass2(username, mentorUserName, b.toString(), 1, 0, c.toString(), quizname)
                        accounntSummaryDatabase2.addDetailsToAcoountSummary2(dataFromQuizInfo)
                        System.out.println(dataFromQuizInfo)
                    }
                    var freequizattemp: Int = accounntSummaryDatabase2.getFreeQuizTotalAttempt(username,quizname)
                    freequizattemp = freequizattemp+1
                    System.out.println("Free quiz Attempt: $freequizattemp")
                    accounntSummaryDatabase2.addFreeAttempt(username,quizname,freequizattemp)
                }
                else{
                    if(accounntSummaryDatabase2.checkCandidate(username,quizname)){

                    }
                    else{
                        dataFromQuizInfo = AccountSummaryDataClass2(username,mentorUserName,b.toString(),0,1,c.toString(),quizname)
                        accounntSummaryDatabase2.addDetailsToAcoountSummary2(dataFromQuizInfo)
                    }
                    var paidquizattemp: Int = accounntSummaryDatabase2.getPaidQuizTotalAttempt(username,quizname)
                    paidquizattemp = paidquizattemp+1
                    accounntSummaryDatabase2.addAttempt(username,quizname,paidquizattemp)
                }
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


