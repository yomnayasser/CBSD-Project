package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Bundle bundle = getIntent().getExtras();
        UserClass user = bundle.getParcelable("user");
        Wallet w=new Wallet();

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
            try {
                boolean NewWallet = w.getWallet(user);
                if(NewWallet==false)
                {
                    Intent i = new Intent(HomeActivity.this, MyWalletActivity.class);
                    i.putExtra("Wallet", (Parcelable) w);
                    startActivity(i);
                    Toast.makeText(HomeActivity.this," Login Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i = new Intent(HomeActivity.this, AddBudgetActivity.class);
                    i.putExtra("user", (Parcelable)user);
                    startActivity(i);
                }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}