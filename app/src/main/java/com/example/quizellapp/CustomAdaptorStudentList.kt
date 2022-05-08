package com.example.quizellapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdaptorStudentList(private val context: Activity, private var candidateName:List<String>, private var attemptsGiven:List<Int>, private var score:List<Double>)
    : ArrayAdapter<String>(context,R.layout.mentor_quiz_students_cardview, candidateName){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.mentor_quiz_students_cardview,null,true)

        val serialno: TextView = rowView.findViewById(R.id.SerialNum)
        val candidatename: TextView = rowView.findViewById(R.id.Candidate)
        val attemptGiven: TextView = rowView.findViewById(R.id.AttemptsGiven)
        val Score: TextView = rowView.findViewById(R.id.Score)

        serialno.text = (position+1).toString()
        candidatename.text = candidateName[position]
        attemptGiven.text = attemptsGiven[position].toString()
        Score.text = score[position].toString()

        return rowView
    }
}