package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabase(context: Context):SQLiteOpenHelper(context, database,null, databaseversion) {

    private val CreateTable = ("Create table $tablename(" +
            "$COL1 Text," +
            "$COL2 Text," +
            "$COL3 Text," +
            "$COL4 Text) ")

    private val DropTable = "Drop table IF EXISTS $tablename"

    companion object{
        private var database = "Notes"
        private var databaseversion = 1
        private var tablename = "NotesTable"
        private var COL1 = "MentorName"
        private var COL2 = "QuizName"
        private var COL3 = "Category"
        private var COL4 = "Notes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DropTable)
        onCreate(db)
    }

    fun addNotes(notesDataClass: NotesDataClass){

        System.out.println("Mentor Name: ${notesDataClass.MentorName}, Quiz Name: ${notesDataClass.QuizName}, Category: ${notesDataClass.Category}, Notes: ${notesDataClass.Notes}")
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,notesDataClass.MentorName)
        values.put(COL2,notesDataClass.QuizName)
        values.put(COL3,notesDataClass.Category)
        values.put(COL4,notesDataClass.Notes)
        db.insert(tablename,null,values)
        db.close()
    }

    fun checkNotesAvailable(quizname:String):Boolean{
        val db = this.readableDatabase
        val selection = "$COL2 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        return cursor.moveToNext()
        db.close()
    }

    fun getNotesWithDetails(quizname: String):NotesDataClass{
        val db = this.readableDatabase
        val selection = "$COL2 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tablename,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            return NotesDataClass(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3))
        }
        else
            return NotesDataClass("","","","")

        db.close()
    }
}