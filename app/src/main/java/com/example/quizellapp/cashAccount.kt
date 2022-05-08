package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class cashAccount(context: Context):SQLiteOpenHelper(context, databaseName,null, databaseVersion) {

    private val createTable = ("create table "+ tablename+ "(" +
            COL1 + " Text, " +
            COL2 + " Text, " +
            COL3 + " Real, " +
            COL4 + " Integer, " +
            COL5 + " Real) ")

    private val dropTable = ("Drop table IF EXISTS $tablename")

    companion object{
        private val databaseName = "cashAccount"
        private val tablename = "mentorcashdata"
        private val databaseVersion = 1
        private val COL1 = "Username"
        private val COL2 = "quizname"
        private val COL3 = "cash"
        private val COL4 = "attempts"
        private val COL5 = "total"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun adddata(cashaccountDataClass: cashaccountDataClass){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,cashaccountDataClass.username)
        values.put(COL2,cashaccountDataClass.quizname)
        values.put(COL3,cashaccountDataClass.cash)
        values.put(COL4,cashaccountDataClass.attempts)
        values.put(COL5,cashaccountDataClass.total)
        System.out.println("data Inserted")

        db.insert(tablename,null,values)
        db.close()
    }
    fun totalCash(username:String):Double{
        var cash = 0.0
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        var selectionArgs = arrayOf(username)
        var cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            cash += cursor.getDouble(4)
        }
        return cash
    }
}