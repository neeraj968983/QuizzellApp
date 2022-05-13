package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class NewQuizzes : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_quizzes)

        var scoreDatabase = ScoreDatabase(this)
        var quizInfoDatabase = QuizInfoDatabase(this)
        var i = intent



        var listOfQuizWithAttemptsLeft:ArrayList<NewQuizzesDataClass> = scoreDatabase.getQuizNameWithAttemptsLeft(i.getStringExtra("username").toString())

        var mentorNames:ArrayList<String> = ArrayList()
        var categories:ArrayList<String> = ArrayList()
        var quiztypes:ArrayList<String> = ArrayList()
        var durations:ArrayList<Int> = ArrayList()
        var maxMarks:ArrayList<Int> = ArrayList()
        var quizNamesWithAttemptLeft:ArrayList<String> = ArrayList()
        var quizAttemptsLeft:ArrayList<Int> = ArrayList()


        for(j in 0..(listOfQuizWithAttemptsLeft.size-1)){

            var (mentorname,category,quiztype) = quizInfoDatabase.getDetailOfNewQuizWithAttemptsLeft(listOfQuizWithAttemptsLeft[j].quizname)
            var (duration,maxmarks) = quizInfoDatabase.getMaxmmarksAndDurationOfNewQuizWithAttemptsLeft(listOfQuizWithAttemptsLeft[j].quizname)

            mentorNames.add(mentorname)
            categories.add(category)
            quiztypes.add(quiztype)

            durations.add(duration)
            maxMarks.add(maxmarks)

            quizNamesWithAttemptLeft.add(listOfQuizWithAttemptsLeft[j].quizname)
            quizAttemptsLeft.add(listOfQuizWithAttemptsLeft[j].attemptsleft)

        }



        var listView:ListView = findViewById(R.id.NewQuizListView)
        val myAdaptor = QuizListCustomAdaptor(this,quizNamesWithAttemptLeft,mentorNames,quiztypes,categories,maxMarks,durations,quizAttemptsLeft)
        listView.adapter = myAdaptor
        listView.setBackgroundColor(Color.parseColor("#81FFFFFF"))
        listView.setOnItemClickListener { parent, view, position, id ->
            System.out.println(parent.getItemAtPosition(position).toString())
        }

    }

}