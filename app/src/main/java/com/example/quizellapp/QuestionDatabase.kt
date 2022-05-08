package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuestionDatabase(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    private val CreateTable = ("create table " + tableName +'(' +
            COL1 + " Text, " +
            COL2 + " Text, " +
            COL3 + " Text, " +
            COL4 + " Text, " +
            COL5 + " Text, " +
            COL6 + " Text, " +
            COL7 + " Text)")

    private val DropTable = ("Drop table IF EXISTS $tableName")


    companion object{
        private var databaseName = "Questions"
        private var tableName = "QuestionsList"
        private var databaseVersion = 1
        private var COL1 = "quizName"
        private var COL2 = "question"
        private var COL3 = "option1"
        private var COL4 = "option2"
        private var COL5 = "option3"
        private var COL6 = "option4"
        private var COL7 = "correctOption"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DropTable)

        onCreate(db)
    }

    fun addQuestion(questin:Question){
        System.out.println("Question data base = question added......//")
        var db = this.writableDatabase
        var values = ContentValues()
        values.put(COL1,questin.quizname)
        values.put(COL2,questin.ques)
        values.put(COL3,questin.option1)
        values.put(COL4,questin.option2)
        values.put(COL5,questin.option3)
        values.put(COL6,questin.option4)
        values.put(COL7,questin.correctOption)

        db.insert(tableName,null,values)

    }

    fun getQuestionList(quizzName: String?):ArrayList<QuestionAndOption>{
        var arrayList:ArrayList<QuestionAndOption> = ArrayList()
        var db = this.readableDatabase
        var selection = "$COL1 = ?"
        var selectioArgs = arrayOf(quizzName)
        var cursor = db.query(tableName,null,selection,selectioArgs,null,null,null)
        while (cursor.moveToNext()){
            arrayList.add(QuestionAndOption(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)))

        }
        return arrayList
        db.close()

    }
}