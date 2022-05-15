package com.example.android.notes20;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notes20.notes_repository.NotesDbHelper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private SQLiteDatabase db;
    private NotesCursorAdapter adapter;

    private void initialize() {
        db = NotesDbHelper.GetWritableDatabase(this);
        Cursor todoCursor = newToDoCursor(db);
        adapter = new NotesCursorAdapter(this, todoCursor);
    }

    protected Cursor newToDoCursor(SQLiteDatabase db) {
        Cursor ctxTodoCursor = db.rawQuery("SELECT * FROM notes ORDER BY _id DESC", null);
        return ctxTodoCursor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        EditText editText = (EditText) findViewById(R.id.editText);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);

        editText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ContentValues cv = new ContentValues();
                    cv.put("body", editText.getText().toString());
                    db.insert("notes", null, cv);
                    adapter.changeCursor(newToDoCursor(db));
                    editText.setText("");
                    return true;
                }
            return false;
        });


        ActivityResultLauncher<Intent> noteEditorResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        String name =  data.getStringExtra("newNote");
                        int ind =  data.getIntExtra("indexOfNote",0);
                        db.delete("notes", "_id = ?", new String[]{String.valueOf(ind)});
                        ContentValues cv = new ContentValues();
                        cv.put("body", name);
                        cv.put("_id", ind);
                        db.insert("notes", null, cv);
                        adapter.changeCursor(newToDoCursor(db));
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            TextView lvtext = itemClicked.findViewById(R.id.dbtext);
//            TextView lvid = itemClicked.findViewById(R.id.dbid);
//            CharSequence charStrId = lvid.getText();
            int strId = Integer.parseInt(String.valueOf(adapter.getItemId(position)));
            String strText = lvtext.getText().toString();
            Log.d("strText", strText);
            Intent noteEditor = new Intent(
                    MainActivity.this,
                    NoteEditorActivity.class);
            noteEditor.putExtra("textOfNote", strText);
            noteEditor.putExtra("indexOfNote", strId);
            noteEditorResultLauncher.launch(noteEditor);
        });
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        TextView lvid = view.findViewById(R.id.dbid);
        TextView lvtext = view.findViewById(R.id.dbtext);

        db.delete("notes", "_id = ?",
                new String[]{String.valueOf(adapter.getItemId(position))});
        adapter.changeCursor(newToDoCursor(db));
        adapter.notifyDataSetChanged();

        Toast.makeText(
                getApplicationContext(),
                ToasTextBuilder.NoteDeletedText(lvtext.getText()),
                Toast.LENGTH_SHORT
        ).show();
        return true;
    }
}