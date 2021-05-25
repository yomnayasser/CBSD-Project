package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button remindersBtn = (Button)findViewById(R.id.remindersBtn);
        Button todoBtn = (Button)findViewById(R.id.todoBtn);
        Button notesBtn = (Button)findViewById(R.id.notesBtn);

        remindersBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RemindersActivity.class);
            startActivity(i);
        });

        todoBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ToDoActivity.class);
            startActivity(i);
        });

        notesBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(i);
        });
    }
}