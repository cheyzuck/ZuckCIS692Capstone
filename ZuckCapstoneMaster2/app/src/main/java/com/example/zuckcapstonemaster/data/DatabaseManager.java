package com.example.zuckcapstonemaster.data;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "weightEntryDB";
    private static final String TABLE_ENTRIES = "entries";
    private static final String ID = "id";
    private static final String WEIGHT_ENTRY_INPUT = "weight_entry_input";
    private static final String WEIGHT_ENTRY_INPUT_DATE = "weight_entry_input_date";

    private static final String TABLE_PERSON = "person";
    private static final String COLUMN_START_WEIGHT = "start_weight";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_TARGET_WEIGHT = "target_weight";
    private static final String COLUMN_TARGET_DATE = "target_date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_HEIGHT = "height";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateEntiresTable = "create table " + TABLE_ENTRIES + "( " + ID;
        sqlCreateEntiresTable += " integer primary key autoincrement, " + WEIGHT_ENTRY_INPUT;
        sqlCreateEntiresTable += " text, " + WEIGHT_ENTRY_INPUT_DATE + " text )";

        db.execSQL(sqlCreateEntiresTable);

        String sqlCreatePersonTable = "create table " + TABLE_PERSON + "(" + ID;
        sqlCreatePersonTable += " integer primary key autoincrement, " + COLUMN_START_WEIGHT;
        sqlCreatePersonTable += " text, " + COLUMN_START_DATE + " text, " + COLUMN_TARGET_WEIGHT;
        sqlCreatePersonTable += " text, " + COLUMN_TARGET_DATE + " text, " + COLUMN_GENDER;
        sqlCreatePersonTable += " text, " + COLUMN_HEIGHT + " text )";

        db.execSQL(sqlCreatePersonTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ENTRIES);
        onCreate(db);
    }

    public void insertWeightEntry(NewEntry newEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_ENTRIES;
        sqlInsert += " values( null, '" + newEntry.getWeight();
        sqlInsert += "', '" + newEntry.getDate() + "' )";

        db.execSQL(sqlInsert);
        db.close();
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_ENTRIES;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    public ArrayList<NewEntry> selectAllEntries() {
        String sqlQuery = "select * from " + TABLE_ENTRIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<NewEntry> entries = new ArrayList<NewEntry>();
        while (cursor.moveToNext()) {
            NewEntry currentEntry
                    = new NewEntry(Integer.parseInt(cursor.getString(0)),
                    cursor.getFloat(1), cursor.getString(2));
            entries.add(currentEntry);
        }

        db.close();
        return entries;
    }

    public NewEntry selectById(int id) {
        String sqlQuery = "select * from " + TABLE_ENTRIES;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        NewEntry entry = null;
        if (cursor.moveToFirst())
            entry = new NewEntry(Integer.parseInt(cursor.getString(0)),
                    cursor.getFloat(1), cursor.getString(2));
        return entry;
    }

    public String getAverageWeightLossPerWeek() {
        String sqlQuery = "select avg(" + WEIGHT_ENTRY_INPUT + ") from " + TABLE_ENTRIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        int avg = 0;

        if (cursor.moveToFirst()) {
            avg = (int) Float.parseFloat(cursor.getString(0));
        }

        db.close();

        return String.valueOf(avg/4);
    }

    public void insertPerson (Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_PERSON;
        sqlInsert += " values( null, '" + person.getStartWeight();
        sqlInsert += "', '" + person.getStartDate();
        sqlInsert += "', '" + person.getTargetWeight();
        sqlInsert += "', '" + person.getTargetDate();
        sqlInsert += "', '" + person.getGender();
        sqlInsert += "', '" + person.getHeight() + "' )";

        db.execSQL(sqlInsert);
        db.close();
    }

    public Person selectPersonById(int id) {
        String sqlQuery = "select * from " + TABLE_PERSON;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        Person person = null;
        if (cursor.moveToFirst())
            person = new Person(Integer.parseInt(cursor.getString(0)),
                    cursor.getFloat(1), cursor.getString(2),
                    cursor.getFloat(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));
        return person;
    }
}