package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RatingReview(context: Context):SQLiteOpenHelper(context, database,null, databaseVersion) {
    private val CreateTable = ("create table "+ tablename + "(" +
            COL1 + " Text, " +
            COL2 + " Integer, " +
            COL3 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS ${tablename}")

    companion object{
        private var database = "feedback"
        private var databaseVersion = 1
        private var tablename = "feedbackTable"
        private var COL1 = "username"
        private var COL2 = "rating"
        private var COL3 = "review"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addReview(username:String?, rating:Int, Review:String?){
        val db = this.writableDatabase
        val db2 = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db2.query(tablename,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            var values = ContentValues()
            values.put(COL2,rating)
            values.put(COL3,Review)
            db2.update(tablename,values,selection,selectionArgs)
        }
        else{
            var values = ContentValues()
            values.put(COL1,username)
            values.put(COL2,rating)
            values.put(COL3,Review)
            db.insert(tablename,null,values)
        }
        db.close()
        db2.close()
    }
}