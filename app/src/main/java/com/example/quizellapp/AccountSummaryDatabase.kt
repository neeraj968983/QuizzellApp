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
        values.put(COL1, accountSummaryDataClass.userName)
        values.put(COL2, accountSummaryDataClass.category)
        values.put(COL4,accountSummaryDataClass.freeQuiz)
        values.put(COL5,accountSummaryDataClass.paidQuiz)
        values.put(COL6,0)
        values.put(COL7,0)
        values.put(COL8,0)
        values.put(COL9,accountSummaryDataClass.cash)
        values.put(COL10,accountSummaryDataClass.coin)
        values.put(COL11, accountSummaryDataClass.totalQuestions)
        values.put(COL12, accountSummaryDataClass.quizType)
        values.put(COL13, accountSummaryDataClass.quizname)

        db.insert(tablename,null,values)
        db.close()
    }
    fun getTotalQuizList(username:String?):Array<Int>{
        var totalfreequiz:Int = 0
        var totalpaidquiz:Int = 0
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(username)
        var cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            totalfreequiz += cursor.getInt(2)
            System.out.println("Free quiz: $totalfreequiz ............//")
            totalpaidquiz += cursor.getInt(3)
        }
        return arrayOf(totalfreequiz,totalpaidquiz,(totalfreequiz+totalpaidquiz))
        db.close()
    }

    fun getTotalCashCoinQuizName(mentorname:String):cashCoin{
        var cash:ArrayList<Double> = ArrayList()
        var quizname:ArrayList<String> = ArrayList()
        var coin:Int = 0
        var db = this.readableDatabase
        var selection = "$COL1 = ?"
        var selectionArgs = arrayOf(mentorname)
        var cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            cash.add(cursor.getDouble(7))
            quizname.add(cursor.getString(11))
            coin = cursor.getInt(8)
        }
        System.out.println("Coin is $coin")
        var data= cashCoin(cash,coin,quizname)
        return data
        db.close()
    }

    fun getMentorName(quizname:String?):String{
        var mentorname:String = ""
        val db = this.readableDatabase
        val selection = "$COL12 = ?"
        val selecttionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selecttionArgs,null,null,null)
        if (cursor.moveToNext()){
            mentorname = cursor.getString(11)
        }
        return mentorname
        db.close()
    }

    fun getMentorQuizList(username: String?):ArrayList<mentorQuizListData1>{
        var quizList:ArrayList<mentorQuizListData1> = ArrayList()
        var db = this.readableDatabase
        val selection = "$COL1 = ?"
        var selectionArgs = arrayOf(username)
        var cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            quizList.add(mentorQuizListData1(cursor.getString(11),cursor.getString(1),cursor.getString(10)))
        }
        db.close()
        return quizList
    }

    fun getCashAmount(quizname:String):Double{
        var quizValue:Double = 0.0
        val db = this.readableDatabase
        val selection = "$COL13 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            quizValue = cursor.getDouble(7)
        }
        return quizValue
        db.close()
    }
}