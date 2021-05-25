package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        ImageButton edit=(ImageButton)findViewById(R.id.EditBtn);
        FloatingActionButton AddExpense=(FloatingActionButton)findViewById(R.id.AddExpenseBtn);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyWalletActivity.this,AddBudgetActivity.class);
                startActivity(i);
            }
        });
        AddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyWalletActivity.this,AddExpenseActivity.class);
                startActivity(i);
            }
        });
    }
}