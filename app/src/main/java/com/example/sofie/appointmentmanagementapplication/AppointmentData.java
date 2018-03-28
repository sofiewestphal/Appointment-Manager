package com.example.sofie.appointmentmanagementapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentData extends SQLiteOpenHelper {

    // VARIABLES
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;
    public static String TABLE_NAME = "appointments";

    /* Create a helper object for the Appointment database */
    public AppointmentData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "date TEXT," +
                        "title TEXT," +
                        "time TEXT," +
                        "description TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}