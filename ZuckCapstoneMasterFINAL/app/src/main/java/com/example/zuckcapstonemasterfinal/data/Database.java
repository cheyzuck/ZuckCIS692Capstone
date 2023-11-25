package com.example.zuckcapstonemasterfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper { /* Checked code against Shradda and Rima*/
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "weight_entries.db";
    private static final String TABLE_USER_SETTINGS = "user_settings";
    public static final String TABLE_WEIGHT_ENTRIES = "weight_entries";


    public static final String _ID = "_id";
    public static final String WEIGHT_ENTRY_INPUT = "weight";
    public static final String WEIGHT_ENTRY_INPUT_DATE = "date";
    public static final String PROGRESS_PHOTO_PATH = "progressPhotoPath";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_START_WEIGHT = "startWeight";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_TARGET_WEIGHT = "targetWeight";
    public static final String COLUMN_TARGET_DATE = "targetDate";
    public static final String COLUMN_GENDER = "gender" ;


    // Constructor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the table for weight entries
        String createWeightEntriesTableQuery = "CREATE TABLE " + TABLE_WEIGHT_ENTRIES + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WEIGHT_ENTRY_INPUT + " REAL NOT NULL, " +
                WEIGHT_ENTRY_INPUT_DATE + " TEXT NOT NULL," +
                PROGRESS_PHOTO_PATH + " TEXT)";

        // SQL query to create the user_settings table
        String CREATE_USER_SETTINGS_TABLE = "CREATE TABLE " + TABLE_USER_SETTINGS + "(" +
                COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_START_WEIGHT + " REAL, "
                + COLUMN_START_DATE + "REAL, "
                + COLUMN_HEIGHT + " REAL, "
                + COLUMN_TARGET_WEIGHT+ " REAL, "
                + COLUMN_TARGET_DATE + " TEXT, "
                + COLUMN_GENDER + " TEXT);";

        // Create the user_settings table
        db.execSQL(CREATE_USER_SETTINGS_TABLE);
        db.execSQL(createWeightEntriesTableQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SETTINGS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT_ENTRIES);
            onCreate(db); // Recreate the tables with the correct schema
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database downgrade from version 6 to 2
        if (oldVersion == 6 && newVersion == 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT_ENTRIES);
            onCreate(db); // Recreate the table with the correct schema for version 2
        }
    }


    public ArrayList<Entry> getAllWeightEntries() {
        ArrayList<Entry> weightEntries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String[] projection = {
                _ID,
                WEIGHT_ENTRY_INPUT_DATE,
                WEIGHT_ENTRY_INPUT,
                PROGRESS_PHOTO_PATH
        };

        Cursor cursor = db.query(
                TABLE_WEIGHT_ENTRIES,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(WEIGHT_ENTRY_INPUT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(WEIGHT_ENTRY_INPUT_DATE));
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(PROGRESS_PHOTO_PATH));
            Entry entry = new Entry(id, weight, date, imagePath);
            weightEntries.add(entry);


        }
        cursor.close();
        db.close();

        return weightEntries;

    }


    public void deleteWeightEntry(long entryId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = _ID ;
        String[] selectionArgs = { String.valueOf(entryId) };

        db.close();

    }

    public Person getUserSettings() {

        SQLiteDatabase db = this.getReadableDatabase();
        Person userSettings = null;

        String[] columns = {
                COLUMN_NAME,
                COLUMN_START_WEIGHT,
                COLUMN_START_DATE,
                COLUMN_HEIGHT,
                COLUMN_GENDER,
                COLUMN_TARGET_WEIGHT,
                COLUMN_TARGET_DATE
        };

        Cursor cursor = db.query(
                TABLE_USER_SETTINGS,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            float startWeight = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_START_WEIGHT));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
            String height = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            float targetWeight = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_TARGET_WEIGHT));
            String targetDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TARGET_DATE));

            userSettings = new Person(name, startWeight, startDate , targetWeight, targetDate, gender, height);
            cursor.close();
        }

        db.close();
        return userSettings;
    }


    public long saveUserSettings(Person userSettings) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, userSettings.getName());
        values.put(COLUMN_START_WEIGHT, userSettings.getStartWeight());
        values.put(COLUMN_START_DATE, userSettings.getStartDate());
        values.put(COLUMN_HEIGHT, userSettings.getHeight());
        values.put(COLUMN_GENDER, userSettings.getGender());
        values.put(COLUMN_TARGET_WEIGHT, userSettings.getTargetWeight());
        values.put(COLUMN_TARGET_DATE, userSettings.getTargetDate());
        long result = db.insertWithOnConflict(TABLE_USER_SETTINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return result;

    }


}