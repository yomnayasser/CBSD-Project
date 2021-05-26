package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        int id=getIntent().getExtras().getInt("id");
        Float budget=getIntent().getExtras().getFloat("budget");
        String repeat=getIntent().getExtras().getString("repeat");
//        //Date date=getIntent().getExtras().getString("date");
        String username=getIntent().getExtras().getString("username");


        Spinner spinner = findViewById(R.id.RepeatSpinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("None");
        arrayList.add("Weekly");
        arrayList.add("Monthly");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        EditText BudgetText=(EditText)findViewById(R.id.budgetEditTxt);
        EditText StartDate=(EditText)findViewById(R.id.DateEditTxt);

        Wallet w=new Wallet(username,AddBudgetActivity.this);
        boolean new_Wallet=w.getWallet();
        if(new_Wallet==true)
        {
            BudgetText.setHint("0.0");
            spinner.setSelection(0);
            StartDate.setHint("DD/MM/YYYY");
        }
        else
        {
            BudgetText.setText(String.valueOf(budget));
            if(repeat=="None")
                spinner.setSelection(0);
            else if(repeat=="Weekly")
                spinner.setSelection(1);
            else
                spinner.setSelection(2);

            //StartDate.setText(Date.getString());
        }

        Button save=(Button)findViewById(R.id.saveBudgetBtn);
        save.setOnClickListener(new View.OnClickListener() {

            String RepeatON=spinner.getSelectedItem().toString();
            @Override
            public void onClick(View v) {
                float enteredBudget=Float.parseFloat(BudgetText.getText().toString());
                //Wallet w=new Wallet(enteredBudget,RepeatON,username,AddBudgetActivity.this);
                w.setUsername(username);
                w.setBudget(enteredBudget);
                w.setRepeat(RepeatON);
                w.setContext(AddBudgetActivity.this);

                w.EditWallet();

                Intent i=new Intent(AddBudgetActivity.this,MyWalletActivity.class);
                i.putExtra("username",w.getUsername());
                i.putExtra("id",w.getId());
                i.putExtra("budget",w.getBudget());
                i.putExtra("repeat",w.getRepeat());
                // i.putExtra("date","");
                startActivity(i);
                finish();
            }
        });
    }
}