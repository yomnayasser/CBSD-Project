package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        String username=getIntent().getExtras().getString("username");

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
            Wallet w=new Wallet();
            boolean newWallet = w.checkWallet(username,HomeActivity.this);
            if(newWallet==false)
            {
                Wallet UserWallet=w.getWallet(username,HomeActivity.this);
                Intent i = new Intent(HomeActivity.this, MyWalletActivity.class);
                i.putExtra("username",UserWallet.getUsername());
                i.putExtra("id",UserWallet.getId());
                i.putExtra("budget",UserWallet.getBudget());
                i.putExtra("repeat",UserWallet.getRepeat());
                i.putExtra("date",UserWallet.getDate());
                startActivity(i);
            }
            else
            {
                Intent i = new Intent(HomeActivity.this, AddBudgetActivity.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
    }
}