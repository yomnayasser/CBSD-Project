package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class NotesActivity extends AppCompatActivity {
    ListView view;
    ArrayAdapter<String> notesAdapt;
    DayPlannerDatabase db;
    String name;
    TextView message;
    ArrayList<String> mynotes;
    @Override
    protected void onResume() {
        super.onResume();

        notesAdapt.clear();
        view.setAdapter(notesAdapt);
        db = new DayPlannerDatabase(getApplicationContext());
        Cursor notes = db.getNotes(name);
        if (notes.getCount() == 0)
            message.setText("No notes yet.");
        else{
            message.setText("");
            while (!notes.isAfterLast()) {
                notesAdapt.add(notes.getString(1));
                notes.moveToNext();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        name = getIntent().getExtras().getString("username");
        message = (TextView)findViewById(R.id.noteMessage);

        view = (ListView)findViewById(R.id.noteView);
        notesAdapt = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1);
        view.setAdapter(notesAdapt);

        db = new DayPlannerDatabase(getApplicationContext());
        Cursor notes = db.getNotes(name);

        if (notes.getCount() == 0)
            message.setText("No notes yet.");
        else{
            message.setText("");
            while (!notes.isAfterLast()) {
                notesAdapt.add(notes.getString(1));
                notes.moveToNext();
            }
        }
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String note = ((TextView)view).getText().toString();
                Cursor selectedNote = db.getNote(note, name);
                String noteID = selectedNote.getString(0);

                Note noteObject = new Note();
                noteObject.setNoteID(noteID);
                noteObject.setNoteContent(note);

                Intent i = new Intent(NotesActivity.this, EditNotesActivity.class);

                i.putExtra("username", name);
                i.putExtra("ID", noteID);
                i.putExtra("note", note);

                startActivity(i);
            }
        });

        FloatingActionButton addNote = (FloatingActionButton)findViewById(R.id.addButton);
        addNote.setOnClickListener(v -> {
            Intent i = new Intent(NotesActivity.this, AddNoteActivity.class);
            i.putExtra("username", name);
            startActivity(i);
        });
    }
}