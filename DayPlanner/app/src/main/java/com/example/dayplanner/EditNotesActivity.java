package com.example.dayplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNotesActivity extends AppCompatActivity {

    EditText note;
    DayPlannerDatabase db;
    Note noteObject;
    String noteID;
    String currentNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        String name = getIntent().getExtras().getString("username");
        currentNote = getIntent().getExtras().getString("note");
        noteID = getIntent().getExtras().getString("ID");


        Button delete = (Button)findViewById(R.id.deleteNote);
        Button edit = (Button)findViewById(R.id.editNote);
        edit.setVisibility(View.INVISIBLE);

        note = (EditText)findViewById(R.id.noteContent);
        note.setText(currentNote, TextView.BufferType.EDITABLE);



        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = note.getText().toString();
                if (currentNote != newNote)
                {
                    db = new DayPlannerDatabase(getApplicationContext());
                    db.editNote(noteID, name, newNote);
                    Toast.makeText(getApplicationContext(), "Note changed", Toast.LENGTH_LONG)
                            .show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Nothing changed", Toast.LENGTH_LONG)
                            .show();
                edit.setVisibility(View.INVISIBLE);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DayPlannerDatabase(getApplicationContext());
                db.deleteNote(noteID);
                Toast.makeText(getApplicationContext(), "Note deleted",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}