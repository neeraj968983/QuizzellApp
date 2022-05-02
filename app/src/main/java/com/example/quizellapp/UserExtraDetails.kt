package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserExtraDetails(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    private val CreateTable = ("create table "+ tableName + "(" +
            COL1 + " Text Primary key, " +
            COL2 + " Text, " +
            COL3 + " Text, " +
            COL4 + " Text, " +
            COL5 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS $tableName")

    companion object{
        private var databaseName = "UserExtraDetail"
        private var tableName = "UserExtraDetails"
        private var databaseVersion = 1
        private var COL1 = "username"
        private var COL2 = "FirstName"
        private var COL3 = "LastName"
        private var COL4 = "Gender"
        private var COL5 = "Address"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CreateTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)
        onCreate(db)
    }

    fun addUser(uextradetail:extraDetail){
        val dbConnect = this.writableDatabase
        val values = ContentValues()
        values.put(COL1,uextradetail.username)
        values.put(COL2,uextradetail.firstName)
        values.put(COL3,uextradetail.lastName)
        values.put(COL4,uextradetail.gender)
        values.put(COL5,uextradetail.address)

        dbConnect.insert(tableName, null,values)
        dbConnect.close()
    }
    fun checkUserIsThere(username:String?):Boolean{
        val dbConnect = this.readableDatabase
        var selection = "$COL1 = ?"
        var selectionArgs = arrayOf(username)

        val cursor = dbConnect.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            return true
        }
        return false
        dbConnect.close()
    }

    fun updateUserDetail(uextradetail: extraDetail){
        val dbConnect = this.writableDatabase
        var selection = "$COL1 = ?"
        val selectionArgs = arrayOf(uextradetail.username)
        val values = ContentValues()
        values.put(COL1,uextradetail.username)
        values.put(COL2,uextradetail.firstName)
        values.put(COL3,uextradetail.lastName)
        values.put(COL4,uextradetail.gender)
        values.put(COL5,uextradetail.address)

        dbConnect.update(tableName,values, selection,selectionArgs)
        dbConnect.close()
    }

    fun fetchData(username: String?):Array<Any>{
        var fname = ""
        var lname = ""
        var gender = ""
        var address = ""
        val dbConnect = this.readableDatabase
        var selection = "$COL1 = ?"
        var selectionArgs = arrayOf(username)

        val cursor = dbConnect.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            fname = cursor.getString(1)
            lname = cursor.getString(2)
            gender = cursor.getString(3)
            address = cursor.getString(4)
        }
        dbConnect.close()
        return arrayOf(fname,lname,gender,address)

    }

}