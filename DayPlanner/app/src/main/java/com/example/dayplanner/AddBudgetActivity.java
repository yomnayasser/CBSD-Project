package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

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

        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Wallet w=new Wallet();
        boolean new_Wallet=w.checkWallet(username,AddBudgetActivity.this);
        if(new_Wallet==true)
        {
            BudgetText.setHint("0.0");
            spinner.setSelection(0);
            StartDate.setHint("DD/MM/YYYY");
        }
        else
        {
            Wallet myWallet=w.getWallet(username,AddBudgetActivity.this);
            BudgetText.setText(String.valueOf(myWallet.getBudget()));
            if(myWallet.getRepeat()=="None")
                spinner.setSelection(0);
            else if(myWallet.getRepeat()=="Weekly")
                spinner.setSelection(1);
            else
                spinner.setSelection(2);
            StartDate.setText(myWallet.getDate());
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                StartDate.setText(formatter.format(myCalendar.getTime()));
            }
        };

        StartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddBudgetActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button save=(Button)findViewById(R.id.saveBudgetBtn);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String RepeatON=spinner.getSelectedItem().toString();
                float enteredBudget=Float.parseFloat(BudgetText.getText().toString());

                String enteredDate=StartDate.getText().toString();

                Wallet U_Wallet=new Wallet();

                if(new_Wallet==true)
                    w.AddWallet(username,enteredBudget,RepeatON,enteredDate,AddBudgetActivity.this);
                else
                    w.EditWallet(username,enteredBudget,RepeatON,enteredDate,AddBudgetActivity.this);

                U_Wallet.getWallet(username,AddBudgetActivity.this);

                Intent i=new Intent(AddBudgetActivity.this,MyWalletActivity.class);
                i.putExtra("username",username);
                i.putExtra("id",U_Wallet.getId());
                i.putExtra("budget",U_Wallet.getBudget());
                i.putExtra("repeat",U_Wallet.getRepeat());
                i.putExtra("date",U_Wallet.getDate());
                startActivity(i);
                finish();
            }
        });
    }
}