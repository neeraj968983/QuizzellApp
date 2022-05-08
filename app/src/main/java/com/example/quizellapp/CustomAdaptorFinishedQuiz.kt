package com.example.quizellapp


import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.math.RoundingMode
import java.text.DecimalFormat

class CustomAdaptorFinishedQuiz(private val context:Activity, var quizname:ArrayList<String>, var mentorName:ArrayList<String>, var quizType:ArrayList<String>,var cateGory:ArrayList<String>, var score:ArrayList<Double>)
    : ArrayAdapter<String>(context,R.layout.finishedquiz_cardview, quizname){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.finishedquiz_cardview,null,true)

        val quizName:TextView = rowView.findViewById(R.id.QuizName)
        val mentorname:TextView = rowView.findViewById(R.id.MentorName)
        val quiztype:TextView = rowView.findViewById(R.id.QuizType)
        val category:TextView = rowView.findViewById(R.id.Category)
        val Score:TextView = rowView.findViewById(R.id.Score)

        quizName.text = quizname[position]
        mentorname.text = mentorName[position]
        quiztype.text = quizType[position]
        category.text = cateGory[position]
        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.DOWN
        if ((score[position])<35.00){
            Score.setTextColor(Color.RED)
        }
        Score.text = df.format(score[position]).toString()+"%"

        return rowView

    }
}