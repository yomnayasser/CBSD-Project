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

<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes

    ListView view;
    ArrayAdapter<String> notesAdapt;
    DayPlannerDatabase db;
<<<<<<< Updated upstream
    ArrayList<String> mynotes;
    ArrayAdapter<String> adap;
    ListView lv = (ListView)findViewById(R.id.noteView);
    DayPlannerDatabase dpdb;
=======
=======
    ArrayList<String> mynotes;
    ArrayAdapter<String> adap;
    ListView lv = (ListView)findViewById(R.id.noteList);
    DayPlannerDatabase dpdb;
>>>>>>> Stashed changes
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes
        String name = getIntent().getExtras().getString("username");
        TextView message = (TextView)findViewById(R.id.noteMessage);

        view = (ListView)findViewById(R.id.noteView);
        notesAdapt = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1);
        view.setAdapter(notesAdapt);

        db = new DayPlannerDatabase(getApplicationContext());
        Cursor notes = db.getNotes(name);

        if (notes == null)
            message.setText("No notes yet.");
        else{
            message.setText("");
            while (!notes.isAfterLast()) {
                notesAdapt.add(notes.getString(1));
                notes.moveToNext();
            }
        }
<<<<<<< Updated upstream
=======
=======
        notesTemp note = new notesTemp();
        String[] notes = note.getNote();
>>>>>>> Stashed changes
>>>>>>> Stashed changes

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
=======

>>>>>>> Stashed changes
>>>>>>> Stashed changes

        FloatingActionButton addNote = (FloatingActionButton)findViewById(R.id.addButton);
        addNote.setOnClickListener(v -> {
            Intent i = new Intent(NotesActivity.this, AddNoteActivity.class);
            i.putExtra("username", name);
            startActivity(i);
        });
    }
}