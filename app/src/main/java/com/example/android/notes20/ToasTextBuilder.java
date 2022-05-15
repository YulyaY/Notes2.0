package com.example.android.notes20;

public class ToasTextBuilder {
    protected static String NoteDeletedText(CharSequence noteBody) {
        return "Заметка '" + String.valueOf(noteBody) + "' удалена.";
    }
}
