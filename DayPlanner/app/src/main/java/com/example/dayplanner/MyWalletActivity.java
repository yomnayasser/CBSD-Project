package com.example.dayplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyWalletActivity extends AppCompatActivity {

    public static Activity a;
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        a=this;
        String username=getIntent().getExtras().getString("username");
        int id=getIntent().getExtras().getInt("id");
        float Currentbudget=getIntent().getExtras().getFloat("current_budget");
        float Totalbudget=getIntent().getExtras().getFloat("total_budget");
        String repeat=getIntent().getExtras().getString("repeat");
        String date=getIntent().getExtras().getString("date");
        boolean new_wallet=getIntent().getExtras().getBoolean("new_wallet");

        ImageButton editBudget=(ImageButton)findViewById(R.id.EditBtn);
        FloatingActionButton addExpense=(FloatingActionButton)findViewById(R.id.AddExpenseBtn);
        TextView BudgetAmount=(TextView)findViewById(R.id.textView5);
        String budgetStr=Float.toString(Currentbudget);
        BudgetAmount.setText(budgetStr);
        ListView listView = (ListView) findViewById(R.id.ExpensesList);

        Expenses e=new Expenses();
        ArrayList<Expenses> UserExpenses=new ArrayList<Expenses>();
        e.getExpenses(id,MyWalletActivity.this,UserExpenses);
        TextView empty=(TextView)findViewById(R.id.messageTxt);

        if(UserExpenses.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
        }
        else
        {
            // Create the adapter to convert the array to views
            empty.setVisibility(View.INVISIBLE);
            ExpensesAdapter adapter = new ExpensesAdapter(MyWalletActivity.this, UserExpenses);
            // Attach the adapter to a ListView
            listView.setAdapter(adapter);
        }
        editBudget.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddBudgetActivity.class);
            i.putExtra("username",username);
            i.putExtra("new_wallet",new_wallet);
            i.putExtra("id",id);
            i.putExtra("total_budget",Totalbudget);
            i.putExtra("current_budget",Currentbudget);
            i.putExtra("repeat",repeat);
            i.putExtra("date",date);
            startActivity(i);
        });

        addExpense.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddExpenseActivity.class);
            i.putExtra("username",username);
            i.putExtra("new_wallet",new_wallet);
            i.putExtra("id",id);
            i.putExtra("total_budget",Totalbudget);
            i.putExtra("current_budget",Currentbudget);
            i.putExtra("repeat",repeat);
            i.putExtra("date",date);
            startActivity(i);
        });
    }
}