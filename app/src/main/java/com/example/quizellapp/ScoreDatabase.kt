package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScoreDatabase(context:Context): SQLiteOpenHelper(context, databaseName, null, databaseVerion) {

    private var CreateTable = ("create table "+ tableName + "(" +
            ScoreDatabase.COL1 + " Text, " +
            ScoreDatabase.COL2 + " Text, " +
            ScoreDatabase.COL3 + " Text, " +
            ScoreDatabase.COL4 + " Text, " +
            ScoreDatabase.COL5 + " Integer, " +
            ScoreDatabase.COL6 + " Integer, " +
            ScoreDatabase.COL7 + " Real, " +
            ScoreDatabase.COL8 + " Integer, " +
            ScoreDatabase.COL9 + " Integer, " +
            ScoreDatabase.COL10 + " Integer, " +
            ScoreDatabase.COL11 + " Integer, " +
            ScoreDatabase.COL12 + " Integer)" )

    private val dropTable = ("Drop table IF EXISTS $tableName")

    companion object{
        private var databaseName = "ScoreDatabase"
        private var tableName = "ScoreTable"
        private var databaseVerion = 1
        private var COL1 = "username"
        private var COL2 = "QuizName"
        private var COL3 = "Category"
        private var COL4 = "Topic"
        private var COL5 = "MaxMarks"
        private var COL6 = "TotalQuestions"
        private var COL7 = "Score"
        private var COL8 = "AttemptedQuestions"
        private var COL9 = "CorrectAnswer"
        private var COL10 = "WrongAnswer"
        private var COL11 = "SkippedQuestions"
        private var COL12 = "AttemptsLeft"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addResult(studentResultDetail:resultDetail){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,studentResultDetail.username)
        values.put(COL2,studentResultDetail.quizname)
        values.put(COL3,studentResultDetail.category)
        values.put(COL4,studentResultDetail.topic)
        values.put(COL5,studentResultDetail.maxmarks)
        values.put(COL6,studentResultDetail.totalQuestion)
        values.put(COL7,studentResultDetail.score)
        values.put(COL8,studentResultDetail.attemptedQuestions)
        values.put(COL9,studentResultDetail.correctAnswer)
        values.put(COL10,studentResultDetail.wrongAnswer)
        values.put(COL11,studentResultDetail.skippedQuestions)
        values.put(COL12,studentResultDetail.attemptsLeft)

        db.insert(tableName,null,values)
        db.close()
    }

    fun checkDetail(username:String?, quizname:String?):updateAttemptsAndMarks{
        var attempts = 0
        var score:Double = 0.0
        var flag = 0
        val db = this.readableDatabase
        val selection = "$COL1 = ? AND $COL2 = ?"
        val selectionArgs = arrayOf(username, quizname)

        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            flag = 1
            attempts = cursor.getInt(11)
            score = cursor.getDouble(6)
        }
        else{
            flag = 0
        }
        var details = updateAttemptsAndMarks(flag,attempts,score)
        db.close()
        return details
    }

    fun updateAteempt(username:String?, quizname:String?, attemptsleft:Int, UpdatedScore:Double, updatedQuestionAttempts:Int, updatedCorrectAnswer:Int, updatedWrongAnswer:Int, updatedSkippedQuestion:Int){
        val db = this.writableDatabase
        val selection = "$COL1 = ? AND $COL2 = ?"
        val selectionArgs = arrayOf(username, quizname)
        val values = ContentValues()
        values.put(COL12,attemptsleft)
        values.put(COL7,UpdatedScore)
        values.put(COL8,updatedQuestionAttempts)
        values.put(COL9,updatedCorrectAnswer)
        values.put(COL10,updatedWrongAnswer)
        values.put(COL11,updatedSkippedQuestion)

        db.update(tableName,values,selection,selectionArgs)
        db.close()

    }

    fun updateAttempt(username:String?, quizname:String?, attemptsleft:Int){
        val db = this.writableDatabase
        val selection = "$COL1 = ? AND $COL2 = ?"
        val selectionArgs = arrayOf(username, quizname)
        val values = ContentValues()
        values.put(COL12,attemptsleft)

        db.update(tableName,values,selection,selectionArgs)
        db.close()

    }

    fun countTotalRow(quizname:String?):Int{
        var count = 0
        val db = this.readableDatabase
        val selection = "$COL2 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            count++
        }
        db.close()
        return count
    }

    fun scoreBoard(quizname:String?):List<ListOfStudent>{
        var studentList = arrayListOf<ListOfStudent>()
        val db = this.readableDatabase
        val selectionArgs = arrayOf(quizname)
        val cursor = db.rawQuery("Select * from $tableName where $COL2 = ? ORDER BY $COL7 DESC",selectionArgs,null)
        while (cursor.moveToNext()){
            studentList.add(ListOfStudent(cursor.getString(0),cursor.getDouble(6)))
        }
        db.close()
        return studentList
    }

    fun getScore(candidatename:String?, quizname:String?):Double{
        var score:Double = 0.0
        val db = this.readableDatabase
        val selection = "$COL1 = ? AND $COL2 = ?"
        val selectionArgs = arrayOf(candidatename,quizname)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            score = cursor.getDouble(6)
        }
        db.close()
        return score
    }

    fun getQuizNameAndScoreWithZeroAttemptsLeft(username: String?):ArrayList<finishedQuizDataClass>{
        var quizNameAndScoreList:ArrayList<finishedQuizDataClass> = ArrayList()
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(username)
        var cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            if(cursor.getInt(11)==0){
                quizNameAndScoreList.add(finishedQuizDataClass(cursor.getString(1),cursor.getDouble(6)))
            }
        }
        db.close()
        return quizNameAndScoreList
    }

    fun getScoreAllDetails(username: String?,quizname: String?):resultDetail{
        var scoreData:resultDetail = resultDetail("","","","",0,0,0.0,0,0,0,0,0)
        val db = this.readableDatabase
        val selection = "$COL1 = ? AND $COL2 = ?"
        val selectionArgs = arrayOf(username,quizname)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            scoreData = resultDetail(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getDouble(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getInt(11))
        }
        return scoreData
    }

    fun getQuizNameWithAttemptsLeft(username: String?):ArrayList<NewQuizzesDataClass>{
        var quizNameList:ArrayList<NewQuizzesDataClass> = ArrayList()
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(username)
        var cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            if(cursor.getInt(11)>0){
                quizNameList.add(NewQuizzesDataClass(cursor.getString(1),cursor.getInt(11)))
            }
        }
        db.close()
        return quizNameList
    }

    fun getStudentNamesOfPaidQuiz(quizname:String):ArrayList<String>{
        var studentList:ArrayList<String> = ArrayList()
        val db = this.readableDatabase
        val selection = "$COL2 = ?"
        val selectionArgs = arrayOf(quizname)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        while (cursor.moveToNext()){
            studentList.add(cursor.getString(0))
        }
        return studentList
    }

}