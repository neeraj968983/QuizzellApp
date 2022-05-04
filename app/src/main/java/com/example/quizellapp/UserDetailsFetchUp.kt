package com.example.quizellapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDetailsFetchUp(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    companion object{
        private val databaseName = "Quizzell"
        private val tableName = "UserData"
        private val databaseVersion = 1
        private val COL1 = "Username"
        private val COL2 = "EmailId"
        private val COL3 = "DOB"
        private val COL4 = "Contact"
        private val COL5 = "Password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun getDetails(userdata:String?):Array<Any> {
        var emailid:String = ""
        var contact:Long = 0
        var dateOfBirth:Long = 0
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(userdata)
        val cursor = db.query(tableName, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToNext()) {
            emailid = cursor.getString(1)
            dateOfBirth = cursor.getLong(2)
            contact = cursor.getLong(3)
        }
        return arrayOf(emailid,dateOfBirth,contact)
        db.close()
    }
}