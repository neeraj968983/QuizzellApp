package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class NotePad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_pad)

        val notesDatabase = NotesDatabase(this)

        val save:Button = findViewById(R.id.Save)
        val QuizName:TextView = findViewById(R.id.QuizName)
        val MentorName:TextView = findViewById(R.id.MentorName)
        val Category:TextView = findViewById(R.id.category)
        val notes:EditText = findViewById(R.id.Notes)

        val i = intent
        QuizName.setText(""+i.getStringExtra("QuizName"))
        MentorName.setText(""+i.getStringExtra("usernameFromLogin"))
        Category.setText(""+i.getStringExtra("Category"))

        save.setOnClickListener{
            notesDatabase.addNotes(NotesDataClass(i.getStringExtra("usernameFromLogin").toString(),i.getStringExtra("QuizName").toString(),i.getStringExtra("Category").toString(),notes.text.toString()))
            Toast.makeText(this,"Notes Added!", Toast.LENGTH_SHORT).show()

            var intent = Intent(this,QuizQuestionCreation::class.java)
            intent.putExtra("usernameFromLogin",i.getStringExtra("usernameFromLogin"))
            intent.putExtra("TotalQuestion",i.getIntExtra("TotalQuestion",0))
            intent.putExtra("QuizName",i.getStringExtra("QuizName"))
            intent.putExtra("Category",i.getStringExtra("Category"))
            intent.putExtra("Count",i.getIntExtra("Count",1))
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        Toast.makeText(this,"Quiz Information Added! Can't go back", Toast.LENGTH_SHORT).show()
    }
}