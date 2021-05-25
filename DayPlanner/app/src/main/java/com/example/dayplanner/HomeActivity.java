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
        Button notesBtn = (Button)findViewById(R.id.notesBtn);
        Button WalletBtn=(Button)findViewById(R.id.WalletBtn);

        remindersBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, RemindersActivity.class);
            startActivity(i);
        });

        todoBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, ToDoActivity.class);
            startActivity(i);
        });
        notesBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, NotesActivity.class);
            startActivity(i);
        });
        WalletBtn.setOnClickListener(v -> {
//            if user has no wallet
            Intent i = new Intent(HomeActivity.this, AddBudgetActivity.class);
            startActivity(i);
//            else
//            Intent i = new Intent(HomeActivity.this, MyWalletActivity.class);
//            startActivity(i);
        });
    }
}