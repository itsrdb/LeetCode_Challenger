package com.itsrdb.lcchallenger

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {  //static variables
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user.db" // Name of the DATABASE
        private const val TABLE_USER = "user" // Table Name
        private const val COLUMN_ID = "_id" // Table Name
        private const val COLUMN_USERNAMES = "defaultuser" // Column for Completed Date
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Create table
        val query = ("CREATE TABLE " + TABLE_USER + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY," + COLUMN_USERNAMES + " TEXT)")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IS EXISTS " + TABLE_USER)
        onCreate(db)
    }

    fun addUsername(username : String){
        val values = ContentValues()
        values.put(COLUMN_USERNAMES, username)
        val db = this.writableDatabase
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun checkDb() : Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER", null)
        val check = cursor.moveToFirst()
        db.close()
        return check
    }

    @SuppressLint("Range")
    fun getUser() : String{
        val username : String
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER", null)
        cursor!!.moveToFirst()
        username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAMES))
        //cursor.getString(cursor.getColumnIndex(1))
        cursor.close()
        return username
    }

}