package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String username=getIntent().getExtras().getString("username");
        int id=getIntent().getExtras().getInt("id");
        Float budget=getIntent().getExtras().getFloat("budget");
        String repeat=getIntent().getExtras().getString("repeat");
        String date=getIntent().getExtras().getString("date");

        ImageButton editBudget=(ImageButton)findViewById(R.id.EditBtn);
        FloatingActionButton addExpense=(FloatingActionButton)findViewById(R.id.AddExpenseBtn);
        TextView BudgetAmount=(TextView)findViewById(R.id.textView5);

        Wallet w=new Wallet();
        Wallet user_Wallet=w.getWallet(username,MyWalletActivity.this);

        String budgetStr=Float.toString(user_Wallet.getBudget());
        BudgetAmount.setText(budgetStr);

        editBudget.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddBudgetActivity.class);
            i.putExtra("username",username);
            startActivity(i);
        });

        addExpense.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddExpenseActivity.class);
            startActivity(i);
        });
    }
}