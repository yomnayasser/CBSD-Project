package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        ArrayList<String> mynotes;
        ArrayAdapter<String> adap;


        ListView lv = (ListView)findViewById(R.id.noteList);
        notesTemp note = new notesTemp();
        String[] notes = note.getNote();

        mynotes = new ArrayList<>(Arrays.asList(notes));
        adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mynotes);
        lv.setAdapter(adap);

        FloatingActionButton addNote = (FloatingActionButton)findViewById(R.id.addButton);

        addNote.setOnClickListener(v -> {
            Intent i = new Intent(NotesActivity.this, AddNoteActivity.class);
            startActivity(i);
        });


    }
}