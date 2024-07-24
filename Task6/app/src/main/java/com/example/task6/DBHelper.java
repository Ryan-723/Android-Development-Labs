package com.example.task6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class is a helper for managing the SQLite database
public class DBHelper extends SQLiteOpenHelper {

    // Database-related constants
    static String DBNAME = "Company.db";
    static int VERSION = 1;
    static String TABLE_NAME = "Employee";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    static String COL1 = "id";
    static String COL2 = "name";
    static String COL3 = "destination";
    static String COL4 = "department";
    static String COL5 = "emailid";
    static String COL6 = "salary";
    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT NOT NULL, "
            + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER); ";

    // Constructor for creating the database
    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    // Method called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // Method called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table and recreate it
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // Method to insert an employee record into the database
    public boolean insertEmployee(Employee empObj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, empObj.getName());
        cv.put(COL3, empObj.getDesig());
        cv.put(COL4, empObj.getDept());
        cv.put(COL5, empObj.getEmailid());
        cv.put(COL6, empObj.getSalary());
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1; // Return true if insertion is successful
    }

    // Method to retrieve all employee records from the database
    public Cursor readEmployees() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorObj;
        cursorObj = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursorObj != null) {
            cursorObj.moveToFirst();
        }
        return cursorObj;
    }

    // Method to delete an employee record from the database
    public Integer DeleteEmployee(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "id=" + id, null);
    }
}
