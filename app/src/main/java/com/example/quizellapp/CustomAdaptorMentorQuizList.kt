package com.example.quizellapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdaptorMentorQuizList(private val context: Activity, private var quizname:List<String>, private var category:List<String>, private var quiztype:List<String>, private var attempts:List<Int>)
    : ArrayAdapter<String>(context,R.layout.mentor_quiz_cardview, quizname){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.mentor_quiz_cardview,null,true)

        val quizName: TextView = rowView.findViewById(R.id.QuizName)
        val cateGory: TextView = rowView.findViewById(R.id.Category)
        val quizType: TextView = rowView.findViewById(R.id.QuizType)
        val attempt:TextView = rowView.findViewById(R.id.Attempts)

        quizName.text = quizname[position]
        cateGory.text = category[position]
        quizType.text = quiztype[position]
        attempt.text = attempts[position].toString()

        return rowView
    }
}