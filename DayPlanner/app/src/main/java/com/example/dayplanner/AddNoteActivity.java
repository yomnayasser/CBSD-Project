package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< Updated upstream
import android.widget.Toast;
=======
>>>>>>> Stashed changes

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

<<<<<<< Updated upstream
        DayPlannerDatabase db = new DayPlannerDatabase(this);

        EditText newNote = (EditText)findViewById(R.id.newNote);
        Button saveNewNote = (Button)findViewById(R.id.saveNewNote);
=======
        Button saveNewNote = (Button)findViewById(R.id.saveNewNote);
        EditText newNote = (EditText)findViewById(R.id.newNote);
        notesTemp tempObj = new notesTemp();
>>>>>>> Stashed changes

        saveNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< Updated upstream
                String note = newNote.getText().toString();
                String username = getIntent().getExtras().getString("username");

                db.addNewNote(username, note);
                Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_LONG).show();
            }
        });

=======
                String newNoteText = newNote.getText().toString();
                tempObj.add(newNoteText);
            }
        });


>>>>>>> Stashed changes
    }
}