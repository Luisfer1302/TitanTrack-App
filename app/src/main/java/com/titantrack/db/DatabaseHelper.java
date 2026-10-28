package com.titantrack.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes del esquema
    private static final String DATABASE_NAME = "TitanTrack.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "tabla_historial";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXERCISE = "ejercicio";
    public static final String COLUMN_SUMMARY = "resumen";
    public static final String COLUMN_DATE = "fecha";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DDL: Sentencia SQL para crear la tabla
        String CREATE_TABLE = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE + " TEXT, " +
                COLUMN_SUMMARY + " TEXT, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // CRUD: Método INSERT
    public void insertWorkout(String exercise, String summary, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, exercise);
        values.put(COLUMN_SUMMARY, summary);
        values.put(COLUMN_DATE, date);

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    // CRUD: Método SELECT
    public Cursor getAllWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + COLUMN_ID + " DESC", null);
    }
}
