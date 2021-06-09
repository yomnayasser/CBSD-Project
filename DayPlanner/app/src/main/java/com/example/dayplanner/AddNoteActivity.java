package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        DayPlannerDatabase db = new DayPlannerDatabase(this);

        Button saveNewNote = (Button) findViewById(R.id.saveNewNote);
        EditText newNote = (EditText) findViewById(R.id.newNote);
        notesTemp tempObj = new notesTemp();

        saveNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = newNote.getText().toString();
                String username = getIntent().getExtras().getString("username");

                db.addNewNote(username, note);
                Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_LONG).show();
            }
        });

        String newNoteText = newNote.getText().toString();
        tempObj.add(newNoteText);
    }
}