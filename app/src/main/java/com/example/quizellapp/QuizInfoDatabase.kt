package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizInfoDatabase(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVerion) {

    private var CreateTable = ("create table "+ tableName + "(" +
            COL1 + " Text, " +
            COL2 + " Text, " +
            COL3 + " Text Primary key, " +
            COL4 + " Text, " +
            COL5 + " Text, " +
            COL6 + " Integer, " +
            COL7 + " Text, " +
            COL8 + " Text, " +
            COL9 + " Integer, " +
            COL10 + " Text, " +
            COL11 + " Text, " +
            COL12 + " Integer, " +
            COL13 + " Integer, " +
            COL14 + " Text)" )

    private val dropTable = ("Drop table IF EXISTS $tableName")

    companion object{
        private var databaseName = "QuizInformation"
        private var tableName = "InformationOfQuiz"
        private var databaseVerion = 1
        private var COL1 = "username"
        private var COL2 = "mentorName"
        private var COL3 = "quizName"
        private var COL4 = "category"
        private var COL5 = "subject"
        private var COL6 = "duration"
        private var COL7 = "date"
        private var COL8 = "time"
        private var COL9 = "attempts"
        private var COL10 = "quizType"
        private var COL11 = "quizPurpose"
        private var COL12 = "totalQuestion"
        private var COL13 = "maxMarks"
        private var COL14 = "guideline"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addQuizDetail(quizdetail:Quizdetail){
        var db = this.writableDatabase
        var values = ContentValues()
        values.put(COL1,quizdetail.username)
        values.put(COL2,quizdetail.mentorname)
        values.put(COL3,quizdetail.quizname)
        values.put(COL4,quizdetail.category)
        values.put(COL5,quizdetail.subject)
        values.put(COL6,quizdetail.duration)
        values.put(COL7,quizdetail.date)
        values.put(COL8,quizdetail.time)
        values.put(COL9,quizdetail.attempts)
        values.put(COL10,quizdetail.quiztype)
        values.put(COL11,quizdetail.quizPurpose)
        values.put(COL12,quizdetail.totalquestions)
        values.put(COL13,quizdetail.maxmarks)
        values.put(COL14,quizdetail.guidelines)

        db.insert(tableName,null,values)
        db.close()
    }

    fun quizNameList(): ArrayList<String> {
        var i = 0
        var array = arrayListOf<String>()
        var db = this.readableDatabase
        var cursor = db.query(tableName,null,null,null,null,null,null)
        while (cursor.moveToNext()){
            array.add(cursor.getString(2))
            i++
        }
        return array
        db.close()
    }

    fun quizInformation(quizzName:String?):Array<Any>{
        var mentorname = ""
        var category = ""
        var attemptLeft = 0
        var quizType = ""
        var maxmarks = 0
        var guideline = ""
        var db = this.readableDatabase
        var selection = "$COL3 = ?"
        var selectionArgs = arrayOf(quizzName)

        var cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            mentorname = cursor.getString(1)
            category = cursor.getString(3)
            attemptLeft = cursor.getInt(8)
            quizType = cursor.getString(9)
            maxmarks = cursor.getInt(12)
            guideline = cursor.getString(13)
        }
        return arrayOf(mentorname,category,quizType,maxmarks,guideline)
        db.close()
    }

    fun totalQuestions(quizname:String?):Int{
        var db = this.readableDatabase
        var selection = "$COL3 = ?"
        var selectionArgs = arrayOf(quizname)

        var cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            return cursor.getInt(11)
        }
        return 0
    }

    fun quizAllData(quizzName: String?):Quizdetail{
        var username = ""
        var mentorname = ""
        var quizname = ""
        var category = ""
        var subject = ""
        var duration = 0
        var date = ""
        var time = ""
        var attempts = 1
        var quiztype = ""
        var quizPurpose = ""
        var totalquestions = 0
        var maxmarks = 0
        var guidelines  = ""
        val db = this.readableDatabase
        val selection = "$COL3 = ?"
        val selectionArgs = arrayOf(quizzName)

        var cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            username = cursor.getString(0)
            mentorname = cursor.getString(1)
            quizname = cursor.getString(2)
            category = cursor.getString(3)
            subject = cursor.getString(4)
            duration = cursor.getInt(5)
            date = cursor.getString(6)
            time = cursor.getString(7)
            attempts = cursor.getInt(8)
            quiztype = cursor.getString(9)
            quizPurpose = cursor.getString(10)
            totalquestions = cursor.getInt(11)
            maxmarks = cursor.getInt(12)
            guidelines  = cursor.getString(13)

        }
        var dataOfQuiz:Quizdetail = Quizdetail(username,mentorname,quizname,category,subject,duration,date,time,attempts,quiztype,quizPurpose,totalquestions,maxmarks,guidelines)
        return dataOfQuiz
    }

    fun totalAttempts(quizname: String?):Int{
        var totalattempts:Int = 0
        val db = this.readableDatabase
        val selection = "$COL3 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            totalattempts = cursor.getInt(8)
        }
        return totalattempts
    }

    fun getQuizNameByCategory(category:String):List<String>{
        var quizNameList = arrayListOf<String>()
        val db = this.readableDatabase
        val selection = "$COL4 = ?"
        val selectionArgs = arrayOf(category)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        while(cursor.moveToNext()){
            quizNameList.add(cursor.getString(2))
        }
        db.close()
        return quizNameList
    }

}