package com.example.neighbourly

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, dbname, factory, dbversion) {

    companion object {
        const val dbname = "Neighbourly.db"
        val factory = null
        const val dbversion = 6
        var cursorCount = 0

        var globalAccountID = 0 // This needs to be set somewhere upon login
        var globalEmail = "" // This is set in findUser but should be set again with account ID
        var globalUserName = "" // This should be set when Account name is set
        var globalWorkerID = 0 // This probably shouldn't be a thing
    }

    fun getAccountID(): Int {
        return globalAccountID
    }

    fun getEmail(): String {
        return globalEmail
    }

    fun getUsername(): String {
        return globalUserName
    }

    fun getWorkerID(): Int {
        return globalWorkerID
    }

    // Do not edit the onCreate function
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Errors", "Creating database")
        db?.execSQL("create table Account(ID_account integer primary key autoincrement, Email varchar(100) not null, Password varchar(25) not null)")
        db?.execSQL("create table Address(ID_address integer primary key autoincrement, HouseNum integer not null, Road varchar(50) not null, City varchar(50) not null, Postcode varchar(10) not null, Account_ID integer not null, foreign key (Account_ID) references Account(ID_account))")
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
        initNewAddressData(email)
    }

    fun findUserId(email: String): Int {
        val db = writableDatabase
        val query = "SELECT ID_account FROM Account WHERE Email = \"$email\""
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun findAddressId(userId: Int): Int {
        val db = writableDatabase
        val query = "SELECT ID_address FROM Address WHERE Account_ID = \"$userId\""
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun initNewAddressData(email: String) {
        val userId = findUserId(email)
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put("HouseNum", 0)
        values.put("Road", "")
        values.put("City", "")
        values.put("Postcode", "")
        values.put("Account_ID", userId.toInt())

        db.insert("Address", null, values)
        db.close()
        initNewUserData(userId.toInt())
    }

    fun initNewUserData(userId: Int) {
        val addressId = findAddressId(userId)
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put("Name", "name")
        values.put("Is_worker", 1)
        values.put("Verified", 0)
        values.put("Rating", 0)
        values.put("Account_Id", userId)
        values.put("Address_Id", addressId)
        db.insert("User", null, values)
        db.close()
    }

    fun findCustomerName(customerAccountId: Int): String {
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

    fun findUser(email: String, password: String): String {
        val db = writableDatabase
        val query = "SELECT * FROM Account WHERE email = \"$email\" AND password = \"$password\""
        val cursor = db.rawQuery(query, null)
        cursorCount = cursor.count
        return if (cursor.count<=0) {
            "No account"
        } else {
            globalEmail = email
            globalAccountID = findUserId(globalEmail)
            globalUserName = findCustomerName(globalAccountID)
            Log.d("Errors", "EMAIL: $globalEmail       User ID: $globalAccountID       User Name: $globalUserName")
            "Account found"
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
        db.execSQL("DROP TABLE IF EXISTS Account;")
        db.execSQL("DROP TABLE IF EXISTS Address;")
        db.execSQL("DROP TABLE IF EXISTS User;")
        db.execSQL("DROP TABLE IF EXISTS Task;")
        db.execSQL("DROP TABLE IF EXISTS Review;")
        db.execSQL("DROP TABLE IF EXISTS Feedback;")
        onCreate(db)
    }
}