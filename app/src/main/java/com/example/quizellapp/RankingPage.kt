package com.example.quizellapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat

class RankingPage : AppCompatActivity() {
    private val Rank = ArrayList<Int>()
    private val UserName = ArrayList<String>()
    private val Score = ArrayList<Double>()
    private lateinit var customAdaptor:CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking_page)

        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.DOWN

        val quizInfoDatabase:QuizInfoDatabase = QuizInfoDatabase(this)
        var scoreDatabase = ScoreDatabase(this)

        val i = intent
        var username = i.getStringExtra("usernameFromLogin")

        var quizname:TextView = findViewById(R.id.QuizNameLabel)
        quizname.setText("Quiz Name")

        val recyclerView:RecyclerView = findViewById(R.id.ScoreBoard)
        customAdaptor = CustomAdapter(Rank,UserName,Score)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdaptor

        var count = scoreDatabase.countTotalRow()

        var studentsList:List<ListOfStudent> = scoreDatabase.scoreBoard()

        var scoreDecimalConvert:Double

        for (i in 0..(count-1)){
            Rank.add(i+1)
            UserName.add(studentsList[i].username)
            System.out.println(df.format(studentsList[i].score))
            Score.add((df.format(studentsList[i].score)).toDouble())
        }
        customAdaptor.notifyDataSetChanged()

    }
}