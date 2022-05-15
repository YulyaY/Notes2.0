package com.example.android.notes20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

public class NotesCursorAdapter extends CursorAdapter {
    public NotesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView lvtext = view.findViewById(R.id.dbtext);
//        TextView lvid = view.findViewById(R.id.dbid);
        String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
//        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        lvtext.setText(body);
//        lvid.setText(Integer.toString(id));
    }
}
