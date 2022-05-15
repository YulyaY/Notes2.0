package com.example.android.notes20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class NoteEditorActivity extends AppCompatActivity {
    @Override
    public void onBackPressed()
    {
        EditText editText = (EditText) findViewById(R.id.editText2);

        Intent intent = new Intent();
        String txt = editText.getText().toString();
        intent.putExtra("newNote", txt);
        Intent receivedOrderIntent = getIntent();
        Integer indexOfNote = receivedOrderIntent.getIntExtra("indexOfNote][", 0);
        System.out.println(indexOfNote+" -- indexOfNote");
        intent.putExtra("indexOfNote", indexOfNote);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        setTitle("Note Editor");

        Intent receivedOrderIntent = getIntent();
        String strText = receivedOrderIntent.getStringExtra("textOfNote");
        EditText editText = (EditText) findViewById(R.id.editText2);

        editText.setText(strText);

//        Intent intent = new Intent();
//        intent.putExtra("newNote", strText);
//        setResult(123, intent);
//        finish();

    }
}