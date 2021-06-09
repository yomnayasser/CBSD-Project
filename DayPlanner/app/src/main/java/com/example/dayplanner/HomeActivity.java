package com.example.dayplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        String username=getIntent().getExtras().getString("username");

        Button remindersBtn = (Button)findViewById(R.id.remindersBtn);
        Button todoBtn = (Button)findViewById(R.id.todoBtn);
        Button notesBtn = (Button)findViewById(R.id.notesBtn);
        Button WalletBtn=(Button)findViewById(R.id.WalletBtn);
        Button CalenderBtn=(Button)findViewById(R.id.CalenderBtn);

        remindersBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, RemindersActivity.class);
            i.putExtra("username",username);
            startActivity(i);
        });

        todoBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, ToDoActivity.class);
            startActivity(i);
        });
        notesBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, NotesActivity.class);
            i.putExtra("username",username);
            startActivity(i);
        });
        WalletBtn.setOnClickListener(v -> {
            Wallet w=new Wallet();
            boolean newWallet = w.checkWallet(username,HomeActivity.this);
            if(newWallet==false)
            {
                Wallet UserWallet=w.getWallet(username,HomeActivity.this);
                String repeatOn=UserWallet.getRepeat();
                if(repeatOn.equals("Weekly"))
                {
                    UserWallet.RepeatWeekly(HomeActivity.this);
                }
                else if(repeatOn.equals("Monthly"))
                {
                    UserWallet.RepeatMonthly(HomeActivity.this);
                }
                Intent i = new Intent(HomeActivity.this, MyWalletActivity.class);
                i.putExtra("username",username);
                i.putExtra("id",UserWallet.getId());
                i.putExtra("total_budget",UserWallet.getTotalBudget());
                i.putExtra("current_budget",UserWallet.getCurrentBudget());
                i.putExtra("repeat",UserWallet.getRepeat());
                i.putExtra("date",UserWallet.getDate());
                i.putExtra("new_wallet",newWallet);
                startActivity(i);
            }
            else
            {
                Intent i = new Intent(HomeActivity.this, AddBudgetActivity.class);
                i.putExtra("username",username);
                //
                i.putExtra("new_wallet",newWallet);
                //
                startActivity(i);
            }
        });

        CalenderBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, calenderActivity.class);
            i.putExtra("username",username);
            startActivity(i);
        });


    }
}