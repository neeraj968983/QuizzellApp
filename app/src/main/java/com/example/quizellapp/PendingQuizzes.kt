package com.example.quizellapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class PendingQuizzes(private val context:Activity, var quizname:ArrayList<String>, var mentorName:ArrayList<String>, var quizType:ArrayList<String>,var cateGory:ArrayList<String>, var maxMarks:ArrayList<Int>, var duraTion:ArrayList<Int>, var totalAttempts:ArrayList<Int>)
    : ArrayAdapter<String>(context,R.layout.newquizzes_listview, quizname){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.newquizzes_listview,null,true)

        val quizName:TextView = rowView.findViewById(R.id.QuizName)
        val mentorname:TextView = rowView.findViewById(R.id.MentorName)
        val quiztype:TextView = rowView.findViewById(R.id.QuizType)
        val category:TextView = rowView.findViewById(R.id.Category)
        val maxmarks:TextView = rowView.findViewById(R.id.MaxMarks)
        val duration:TextView = rowView.findViewById(R.id.Duration)
        val attemptleft:TextView = rowView.findViewById(R.id.AttemptLeft)

        if (quizType[position].equals("Paid")){
            quiztype.setBackgroundResource(R.drawable.ic_baseline_bookmark_paid)
        }

        quizName.text = quizname[position]
        mentorname.text = mentorName[position]
        quiztype.text = quizType[position]
        category.text = cateGory[position]
        maxmarks.text = maxMarks[position].toString()
        duration.text = duraTion[position].toString() + " minutes"
        attemptleft.text = totalAttempts[position].toString()

        return rowView

    }
}