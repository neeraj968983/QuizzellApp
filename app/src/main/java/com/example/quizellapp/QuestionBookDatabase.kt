package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuestionBookDatabase(context: Context):SQLiteOpenHelper(context, database,null, databaseVerion) {

    private val CreateTable = ("create table "+ tablename + "(" +
            COL1 + " Text , " +
            COL2 + " Text, " +
            COL3 + " Text, " +
            COL4 + " Text, " +
            COL5 + " Text, " +
            COL6 + " Text, " +
            COL7 + " Text, " +
            COL8 + " Text, " +
            COL9 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS ${tablename}")

    companion object{
        private val database = "QuestionBook"
        private val tablename = "QuestionBookTable"
        private val databaseVerion = 1
        private val COL1 = "MentorName"
        private val COL2 = "QuizName"
        private val COL3 = "Category"
        private val COL4 = "Question"
        private val COL5 = "OptionA"
        private val COL6 = "OptionB"
        private val COL7 = "OptionC"
        private val COL8 = "OptionD"
        private val COL9 = "CorrectOption"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addQuestionToQuestionBook(questionBookDetailDataClass: QuestionBookDetailDataClass){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,questionBookDetailDataClass.mentorname)
        values.put(COL2,questionBookDetailDataClass.quizname)
        values.put(COL3,questionBookDetailDataClass.category)
        values.put(COL4,questionBookDetailDataClass.question)
        values.put(COL5,questionBookDetailDataClass.optionA)
        values.put(COL6,questionBookDetailDataClass.optionB)
        values.put(COL7,questionBookDetailDataClass.optionC)
        values.put(COL8,questionBookDetailDataClass.optionD)
        values.put(COL9,questionBookDetailDataClass.CorrectOption)

        db.insert(tablename,null,values)
        db.close()
    }
}