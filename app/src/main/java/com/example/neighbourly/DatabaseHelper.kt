package com.example.neighbourly

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.database.getStringOrNull
import com.example.neighbourly.data.LoginDataSource
import com.example.neighbourly.ui.contact.ContactFragment

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, dbname, factory, dbversion) {

    companion object {
        const val dbname = "Neighbourly.db"
        val factory = null
        const val dbversion = 5
        var cursorCount = 0
    }

    //DO NOT EDIT ANYTHING ABOVE LINE 43, DON'T EDIT THE TABLES, ASK RYAN FIRST PLEASE I BEG

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Errors", "Creating database")
        db?.execSQL("create table Account(ID_account integer primary key autoincrement, Email varchar(100) not null, Password varchar(25) not null)")
        db?.execSQL("create table Address(ID_address integer primary key autoincrement, HouseNum integer not null, Road varchar(50) not null, City varchar(50) not null, Postcode varchar(10) not null)")
        db?.execSQL("create table User(ID_user integer primary key autoincrement, Name varchar(50) not null, Is_worker integer not null, Verified integer not null, Rating Real not null, Account_Id integer, Address_Id integer, foreign key (Account_Id) references Account(ID_account), foreign key (Address_Id) references Address(ID_address))")
        db?.execSQL("create table Task(ID_task integer primary key autoincrement, Description varchar(100) not null, Job_type varchar(50) not null, Status varchar(50) not null, Additional_requirements varchar(100) not null, Customer_Account_Id integer, Worker_Account_Id integer, foreign key (Customer_Account_Id) references Account(ID_account), foreign key (Worker_Account_Id) references Account(ID_account))")
        db?.execSQL("create table Review(ID_review integer primary key autoincrement, Rating integer not null, Description varchar(100) not null, Task_Id integer, foreign key (Task_Id) references Task(ID_task))")
        db?.execSQL("create table Feedback(ID_feedback integer primary key autoincrement, Contact_Message varchar(250) not null, Email varchar(100), foreign key (Email) references Account(Email))")
    }

    fun onUpgrade(db: SQLiteDatabase?) { // I added this and the database started working, it isn't ever called but I'm too scared to remove it
        Log.d("Errors", "Upgrading database")
        db?.execSQL("create table Account(ID_account integer primary key autoincrement, Email varchar(100) not null, Password varchar(25) not null)")
        db?.execSQL("create table Address(ID_address integer primary key autoincrement, HouseNum integer not null, Road varchar(50) not null, City varchar(50) not null, Postcode varchar(10) not null)")
        db?.execSQL("create table User(ID_user integer primary key autoincrement, Name varchar(50) not null, Is_worker integer not null, Verified integer not null, Rating Real not null, Account_Id integer, Address_Id integer, foreign key (Account_Id) references Account(ID_account), foreign key (Address_Id) references Address(ID_address))")
        db?.execSQL("create table Task(ID_task integer primary key autoincrement, Description varchar(100) not null, Job_type varchar(50) not null, Status varchar(50) not null, Additional_requirements varchar(100) not null, Customer_Account_Id integer, Worker_Account_Id integer, foreign key (Customer_Account_Id) references Account(ID_account), foreign key (Worker_Account_Id) references Account(ID_account))")
        db?.execSQL("create table Review(ID_review integer primary key autoincrement, Rating integer not null, Description varchar(100) not null, Task_Id integer, foreign key (Task_Id) references Task(ID_task))")
        db?.execSQL("create table Feedback(ID_feedback integer primary key autoincrement, Contact_Message varchar(250) not null, Email varchar(100), foreign key (Email) references Account(Email))")
    }

    fun insertUserData(email: String, password: String) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("Email", email)
        values.put("Password", password)

        db.insert("Account", null, values)
        db.close()
    }

    fun insertContactMessage(email: String, message: String) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("Email", email)
        values.put("Contact_Message", message)
        Log.d("Errors", "HELLO LINE 52")

        db.insert("Feedback", null, values)
        db.close()
    }

    fun findUser(email: String, password: String): Boolean {
        val db = writableDatabase
        val query = "SELECT * FROM Account WHERE email = \"$email\" AND password = \"$password\""
        val cursor = db.rawQuery(query, null)
        cursorCount = cursor.count
        return if (cursor.count<=0) {
            Log.d("Errors", "No account found")
            false
        }
        else {
            Log.d("Errors", "Account found")
            true
        }
        cursor.close()
    }

    fun findUserExists(email: String, password: String): Boolean {
        val db = writableDatabase
        val query = "SELECT * FROM Account WHERE email = \"$email\""
        val cursor = db.rawQuery(query, null)
        cursorCount = cursor.count
        return if (cursor.count<=0) {
            Log.d("Errors", "Account doesn't exist yet")
            false
        }
        else {
            Log.d("Errors", "Account exists")
            true
        }
        cursor.close()
    }

    fun fetchResult(): Int {
        return cursorCount
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Account")
        onCreate(db)
    }
}