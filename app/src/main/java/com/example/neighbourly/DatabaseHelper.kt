package com.example.neighbourly

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteCursor
import android.util.Log
import androidx.core.database.getStringOrNull
import java.util.*

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, dbname, factory, dbversion) {

    companion object {
        const val dbname = "Neighbourly.db"
        val factory = null
        const val dbversion = 5
        var cursorCount = 0
    }

    // Do not edit the onCreate function
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Errors", "Creating database")
        db?.execSQL("create table Account(ID_account integer primary key autoincrement, Email varchar(100) not null, Password varchar(25) not null)")
        db?.execSQL("create table Address(ID_address integer primary key autoincrement, HouseNum integer not null, Road varchar(50) not null, City varchar(50) not null, Postcode varchar(10) not null)")
        db?.execSQL("create table User(ID_user integer primary key autoincrement, Name varchar(50) not null, Is_worker integer not null, Verified integer not null, Rating Real not null, Account_Id integer, Address_Id integer, foreign key (Account_Id) references Account(ID_account), foreign key (Address_Id) references Address(ID_address))")
        db?.execSQL("create table Task(ID_task integer primary key autoincrement, Description varchar(100) not null, Job_type varchar(50) not null, Status varchar(50) not null, Additional_requirements varchar(100) not null, Customer_Account_Id integer, Worker_Account_Id integer, foreign key (Customer_Account_Id) references Account(ID_account), foreign key (Worker_Account_Id) references Account(ID_account))")
        db?.execSQL("create table Review(ID_review integer primary key autoincrement, Rating integer not null, Description varchar(100) not null, User_Name varchar(50), foreign key (User_Name) references User(Name))")
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

    fun findCustomerName(customerAccountId: Int): String? {
        val db = writableDatabase
        val query = "SELECT Name FROM User WHERE ID_user = \"$customerAccountId\""
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        val customerName = cursor.getString(0)

        cursor.close()
        return customerName
    }

    fun insertContactMessage(email: String, message: String) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("Email", email)
        values.put("Contact_Message", message)

        db.insert("Feedback", null, values)
        db.close()
    }

    fun findRating(name: String): Boolean{
        val db = writableDatabase
        val query = "SELECT * FROM Review WHERE User_Name = \"$name\""
        val cursor = db.rawQuery(query, null)
        cursorCount = cursor.count
        return if (cursor.count<=0){
            println("Review not found")
            false
        }
        else {
            println("Review found")
            true
        }
        cursor.close()
    }

    fun insertRating(rating: String, description: String, name: String) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("Rating", rating)
        values.put("Description", description)
        values.put("User_Name", name)

        db.insert("Review", null, values)
        println("Successfully added $rating and $description to table Review under $name")
        db.close()
    }

    fun createJob(Description: String, Job_type: String, Status: String, Additional_requirements: String, Customer_Account_Id: String, Worker_Account_Id: String) {
        val db = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("Description", Description)
        values.put("Job_type", Job_type)
        values.put("Status", Status)
        values.put("Additional_requirements", Additional_requirements)
        values.put("Customer_Account_Id", Customer_Account_Id)
        values.put("Worker_Account_Id", Worker_Account_Id)

        db.insert("Task", null, values)
        db.close()
    }

    fun findJobDetails(jobID: String): Array<String> {
        val db = writableDatabase
        val query = "SELECT * FROM Task WHERE ID_task = \"$jobID\""
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        val jobDescription = cursor.getString(1)
        val jobType = cursor.getString(2)
        val status = cursor.getString(3)
        val additionalRequirements = cursor.getString(4)
        val customerAccountId = cursor.getString(5)

        cursor.close()
        return arrayOf(jobDescription, jobType, status, additionalRequirements, customerAccountId)
    }

    fun findAllJobs(): Array<String> {
        val db = writableDatabase
        val query = "SELECT * FROM Task"
        val cursor = db.rawQuery(query, null)

        val descriptions: MutableList<String> = ArrayList()
        cursor.moveToFirst()
        var i = 0
        while (i < cursor.count) {
            val jobDescription = cursor.getString(1)
            descriptions.add(i, jobDescription)
            i++
            cursor.moveToNext()
        }

        cursor.close()
        return descriptions.toTypedArray()
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