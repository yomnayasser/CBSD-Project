package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Button remindersBtn = (Button)findViewById(R.id.remindersBtn);
        Button todoBtn = (Button)findViewById(R.id.todoBtn);

        remindersBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, RemindersActivity.class);
            startActivity(i);
        });

        todoBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, ToDoActivity.class);
            startActivity(i);
        });
    }
}