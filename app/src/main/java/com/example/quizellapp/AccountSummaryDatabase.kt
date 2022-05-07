package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AccountSummaryDatabase(context:Context):SQLiteOpenHelper(context, database, null, databaseversion) {

    private val CreateTable = ("create table "+ tablename + "(" +
            COL1 + " Text , " +
            COL2 + " Text, " +
            COL4 + " Integer, " +
            COL5 + " Integer, " +
            COL6 + " Integer, " +
            COL7 + " Integer, " +
            COL8 + " Integer, " +
            COL9 + " Real, " +
            COL10 + " Integer, " +
            COL11 + " Integer, " +
            COL12 + " Text, " +
            COL13 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS ${tablename}")

    companion object{
        private var database = "AccountSummary"
        private var databaseversion = 1
        private var tablename = "MentorAccountSummary"
        private var COL1 = "MentorName"
        private var COL2 = "Category"
        private var COL4 = "FreeQuizzes"
        private var COL5 = "PaidQuizzes"
        private var COL6 = "FreeQuizTotalStudentAttempt"
        private var COL7 = "PaidQuizTotalStudentAttempt"
        private var COL8 = "PaidQuizTotalAttempt"
        private var COL9 = "Cash"
        private var COL10 = "Coin"
        private var COL11 = "TotalQuestions"
        private var COL12 = "QuizType"
        private var COL13 = "QuizName"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addDataToAccountSummary(accountSummaryDataClass: AccountSummaryDataClass){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1, accountSummaryDataClass.mentorName)
        values.put(COL2, accountSummaryDataClass.category)
        values.put(COL4,accountSummaryDataClass.freeQuiz)
        values.put(COL5,accountSummaryDataClass.paidQuiz)
        values.put(COL6,0)
        values.put(COL7,0)
        values.put(COL8,0)
        values.put(COL9,0)
        values.put(COL10,0)
        values.put(COL11, accountSummaryDataClass.totalQuestions)
        values.put(COL12, accountSummaryDataClass.quizType)
        values.put(COL13, accountSummaryDataClass.quizname)

        db.insert(tablename,null,values)
        db.close()
    }
    fun quizCreationPageUpdate(){

    }
}