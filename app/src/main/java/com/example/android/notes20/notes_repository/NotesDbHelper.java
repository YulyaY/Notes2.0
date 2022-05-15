package com.example.android.notes20.notes_repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDbHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase GetWritableDatabase(Context context) {
        NotesDbHelper helper = new NotesDbHelper(context);
        return helper.getWritableDatabase();
    }

    public NotesDbHelper(Context context) {
        super(context, "notesdb", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG_TAG", "--- onCreate database ---");
        db.execSQL("create table notes ("
                + "_id integer primary key autoincrement,"
                + "body text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            Log.d("LOG_TAG", "--- migrate:: rename to _id ---");
            db.execSQL("alter table notes rename column id to _id");
        }
    }
}