package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ListView lv = (ListView)findViewById(R.id.noteList);
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        notesTemp note = new notesTemp();
        ArrayList mynotes = note.getNote();

        lv.setAdapter(adap);

        for (int i = 0; i < mynotes.size(); i++){
            adap.add(String.valueOf(mynotes.get(i)));
        }


    }
}