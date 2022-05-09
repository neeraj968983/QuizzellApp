package com.example.quizellapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperClass(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    private val CreateTable = ("create table "+ tableName+ "(" +
            COL1 + " Text Primary key, " +
            COL2 + " Text, " +
            COL3 + " Integer, " +
            COL4 + " Integer, " +
            COL5 + " Text) ")

    private val dropTable = ("Drop table IF EXISTS $tableName")

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
        db!!.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(dropTable)

        onCreate(db)
    }

    fun addUser(userinfo:UserInfo){
        val dbConnect = this.writableDatabase
        var values = ContentValues()
        values.put(COL1,userinfo.userName)
        values.put(COL2,userinfo.emailID)
        values.put(COL3,userinfo.dob)
        values.put(COL4,userinfo.contactNumber)
        values.put(COL5,userinfo.password)

        dbConnect.insert(tableName, null,values)
        dbConnect.close()
    }

    fun loginUser(logindata:LoginData) : Boolean{
        val dbConnect = this.readableDatabase

        val selection = "$COL1 = ? AND $COL5 = ?"
        val selectionArgs = arrayOf(logindata.userName,logindata.password)

        val cursor = dbConnect.query(tableName,null,selection,selectionArgs,null,null,null)

        val cursorCount = cursor.count
        cursor.close()
        dbConnect.close()

        return cursorCount > 0

    }

    fun fetchLocalData(uname:String?):Array<Any>{
        var emID = ""
        var birth:Long = 0
        var contct:Long = 0

        val db = this.readableDatabase
        var selection = "$COL1 = ?"
        val selectionArgs = arrayOf(uname)

        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            emID = cursor.getString(1)
            birth = cursor.getLong(2)
            contct = cursor.getLong(3)

        }
        return arrayOf(emID,birth,contct)
        db.close()
    }

    fun updateUserDetail(userinfo:UserInfoUpdate){
        val dbConnect = this.writableDatabase
        var selection = "$COL1 = ?"
        var selectionArgs = arrayOf(userinfo.username)

        var values = ContentValues()
        values.put(COL2,userinfo.email)
        values.put(COL3,userinfo.dob)
        values.put(COL4,userinfo.contactNumber)

        dbConnect.update(tableName,values,selection,selectionArgs)
        dbConnect.close()


    }

    fun changePassword(passwordData: changePasswordDatabase):Boolean{
        val dbconnect = this.writableDatabase
        val selection = "$COL1 = ? AND $COL5 = ?"
        var selectionArgs = arrayOf(passwordData.uname, passwordData.oldPassword)

        val cursor = dbconnect.query(tableName,null,selection,selectionArgs,null,null,null)
        if(cursor.moveToNext()){
            var values = ContentValues()
            values.put(COL5, passwordData.newPassword)
            dbconnect.update(tableName,values,selection,selectionArgs)
            return true
        }
        else
            return false


        dbconnect.close()
    }

    fun verifyUsernameAndContact(usernameAndContactdataclass: usernameAndContactdataclass):Boolean{
        System.out.println("username: ${usernameAndContactdataclass.username} \n Contact: ${usernameAndContactdataclass.contact}")
        var flag = false
        val db = this.readableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(usernameAndContactdataclass.username)
        val cursor = db.query(tableName,null,selection,selectionArgs,null,null,null)
        if (cursor.moveToNext()){
            System.out.println("Contact in database is ${cursor.getInt(3).toLong()}")
            if (usernameAndContactdataclass.contact.toInt()==(cursor.getInt(3))){
                System.out.println("True")
                flag = true
            }
            else{
                System.out.println("False")
                flag = false
            }
        }
        return flag
    }

    fun changePasswordThroughOTP(username:String,password:String){
        val db = this.writableDatabase
        val selection = "$COL1 = ?"
        val selectionArgs = arrayOf(username)
        val values = ContentValues()
        values.put(COL5,password)
        db.update(tableName,values,selection,selectionArgs)
        db.close()
    }
}