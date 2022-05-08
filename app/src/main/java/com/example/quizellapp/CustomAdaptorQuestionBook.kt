package com.example.quizellapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class CustomAdaptorQuestionBook (private val context: Activity, private var mentorname:List<String>, private var question:List<String>, private var optionA:List<String>, private var optionB:List<String>,private var optionC:List<String>, private var optionD:List<String>, private var correctOption:List<String>)
    : ArrayAdapter<String>(context,R.layout.questionbook_cardview, question){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var flag = 0
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.questionbook_cardview,null,true)

        val mentorName: TextView = rowView.findViewById(R.id.MentorName)
        val Question: TextView = rowView.findViewById(R.id.Question)
        val OptionA: TextView = rowView.findViewById(R.id.OptionAValue)
        val OptionB: TextView = rowView.findViewById(R.id.OptionBValue)
        val OptionC: TextView = rowView.findViewById(R.id.OptionCValue)
        val OptionD: TextView = rowView.findViewById(R.id.OptionDValue)
        val CorrectOption: TextView = rowView.findViewById(R.id.CorrectOptionValue)


        mentorName.text = mentorname[position]
        Question.text = question[position]
        OptionA.text = optionA[position]
        OptionB.text = optionB[position]
        OptionC.text = optionC[position]
        OptionD.text = optionD[position]
        CorrectOption.text = correctOption[position]

        return rowView
    }
}