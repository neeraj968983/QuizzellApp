package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NotesView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_view)

        val notesDatabase = NotesDatabase(this)

        val i = intent

        val quizname:TextView = findViewById(R.id.QuizName)
        val category:TextView = findViewById(R.id.Category)
        val mentorUserName:TextView = findViewById(R.id.MentorUserName)
        val notes:TextView = findViewById(R.id.Notes)
        val ready: Button = findViewById(R.id.Ready)

        var quizName = i.getStringExtra("QuizName")
        var AttemptsLeft = i.getIntExtra("AttemptsLeft",0)

        var pageDetails:NotesDataClass = notesDatabase.getNotesWithDetails(quizName.toString())

        quizname.setText(""+quizName)
        category.setText(""+pageDetails.Category)
        mentorUserName.setText("-"+pageDetails.MentorName)
        notes.setText(""+pageDetails.Notes)

        ready.setOnClickListener{
            onBackPressed()
        }



    }
}