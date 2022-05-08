package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AccounntSummaryDatabase2(context: Context):SQLiteOpenHelper(context, database,null, databaseversion) {

    private val CreateTable = ("create table "+ tablename + "(" +
            COL1 + " Text , " +
            COL2 + " Text, " +
            COL3 + " Text, " +
            COL4 + " Integer, " +
            COL5 + " Integer, " +
            COL6 + " Integer, " +
            COL7 + " Text, " +
            COL8 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS ${tablename}")

    companion object{
        private var database = "AccountSummary2"
        private var databaseversion = 1
        private var tablename = "TestAttemptSummary"
        private var COL1 = "CandidateName"
        private var COL2 = "MentorName"
        private var COL3 = "Category"
        private var COL4 = "FreeQuizTotalStudentAttempt"
        private var COL5 = "PaidQuizTotalStudentAttempt"
        private var COL6 = "PaidQuizTotalAttempt"
        private var COL7 = "QuizType"
        private var COL8 = "QuizName"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addDetailsToAcoountSummary2(accountSummaryDataClass2: AccountSummaryDataClass2){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,accountSummaryDataClass2.candidatename)
        values.put(COL2,accountSummaryDataClass2.mentorname)
        values.put(COL3,accountSummaryDataClass2.category)
        values.put(COL4,accountSummaryDataClass2.FreeQuizTotalStudentAttempt)
        values.put(COL5,accountSummaryDataClass2.PaidQuizTotalStudentAttempt)
        values.put(COL7,accountSummaryDataClass2.quizType)
        values.put(COL8,accountSummaryDataClass2.quizname)

        db.insert(tablename,null,values)
        db.close()
    }

    fun getPaidQuizTotalAttempt(candidatename:String?,quizname:String?):Int{
        var Attempts:Int = 0
        val db = this.readableDatabase
        val selection = "$COL1 = ? AND $COL8 = ?"
        val selectionArgs = arrayOf(candidatename,quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            Attempts = cursor.getInt(5)
        }
        return Attempts
        db.close()

    }
    fun addAttempt(candidatename:String?,quizname:String?, attempt:Int){

        val db = this.writableDatabase
        val selection = "$COL1 = ? AND $COL8 = ?"
        val selectionArgs = arrayOf(candidatename,quizname)
        val values = ContentValues()
        values.put(COL6,attempt)

        db.update(tablename,values,selection,selectionArgs)
        db.close()
    }

    fun checkCandidate(candidatename: String?, quizname:String?):Boolean{
        val db = this.readableDatabase
        val selection = "$COL1 = ? AND $COL8 = ?"
        val selectionArgs = arrayOf(candidatename,quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        return cursor.moveToNext()
        db.close()
    }

    fun getData(mentorname:String?):Array<Int>{
        var freeQuizTotalStudentAttempted:Int = 0
        var paidQuizTotalStudentAttempted:Int = 0
        var paidTotalAttempts:Int = 0
        val db = this.readableDatabase
        val selection = "$COL2 = ?"
        val selectionArgs = arrayOf(mentorname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            freeQuizTotalStudentAttempted += cursor.getInt(3)
            paidQuizTotalStudentAttempted += cursor.getInt(4)
            paidTotalAttempts += cursor.getInt(5)
        }
        return arrayOf(freeQuizTotalStudentAttempted,paidQuizTotalStudentAttempted,paidTotalAttempts)
        db.close()
    }
    fun getAttemptsByList(quizname: String?):Int{
        var attempts:Int = 0
        val db = this.readableDatabase
        val selection = "$COL8 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            if(cursor.getString(5).equals("Free")){
                attempts += cursor.getInt(3)
            }
            else{
                attempts += cursor.getInt(4)
            }
        }
        db.close()
        return attempts
    }

    fun getCandidateNameAndAttempts(quizname: String?):ArrayList<mentorQuizStudentData>{
        var quizStudentList:ArrayList<mentorQuizStudentData> = ArrayList()
        var attemptsGiven = 0
        val db = this.readableDatabase
        val selection = "$COL8 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            if(cursor.getString(5).equals("Free")){
                attemptsGiven = 0
            }
            else{
                attemptsGiven = cursor.getInt(5)
            }
            quizStudentList.add(mentorQuizStudentData(cursor.getString(0), attemptsGiven))
        }
        db.close()
        return quizStudentList
    }
}