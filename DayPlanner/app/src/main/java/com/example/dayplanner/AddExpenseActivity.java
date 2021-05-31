package com.example.dayplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.jar.Attributes;

public class AddExpenseActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        String username=getIntent().getExtras().getString("username");
        int id=getIntent().getExtras().getInt("id");
        float Currentbudget=getIntent().getExtras().getFloat("current_budget");
        float Totalbudget=getIntent().getExtras().getFloat("total_budget");
        String repeat=getIntent().getExtras().getString("repeat");
        String BudgetDate=getIntent().getExtras().getString("date");
        boolean new_wallet=getIntent().getExtras().getBoolean("new_wallet");

        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm a");

        final Spinner CategoriesSpinner=(Spinner)findViewById(R.id.CategoriesSpinner);
        EditText otherCategory=(EditText)findViewById(R.id.OtherCategoryTxt);
        EditText ExpenseName=(EditText)findViewById(R.id.ExpenseNameTxt);
        EditText Price=(EditText)findViewById(R.id.PriceText);
        EditText ExpenseDate=(EditText)findViewById(R.id.ExpenseDateEditTxt);
        ExpenseDate.setFocusable(false);
        ExpenseDate.setKeyListener(null);

        LocalDateTime timeNow=LocalDateTime.now();
        String timeNowStr=formatter.format(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        ExpenseDate.setText(timeNowStr);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                ExpenseDate.setText(formatter.format(myCalendar.getTime()));
            }
        };
        //LocalDateTime DetailedDate = LocalDateTime.parse(myCalendar.getTime().toString(),formatter);

        ExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(AddExpenseActivity.this,time,myCalendar
                        .get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),false).show();

                new DatePickerDialog(AddExpenseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        CategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(CategoriesSpinner.getSelectedItem().toString().equals("Other"))
                    otherCategory.setVisibility(otherCategory.VISIBLE);
                else
                    otherCategory.setVisibility(otherCategory.INVISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button save=(Button)findViewById(R.id.SaveExpenseBtn);
        Expenses e=new Expenses();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ExpenseName.getText().toString().matches(""))
                    ExpenseName.setError("Enter Name please");
                else if(CategoriesSpinner.getSelectedItem().toString().matches("Select Category"))
                    Toast.makeText(AddExpenseActivity.this,"Please Choose Category",Toast.LENGTH_LONG).show();
                else if(Price.getText().toString().matches(""))
                    Price.setError("Enter Price please");
                else
                {
                    String Name=ExpenseName.getText().toString();
                    float ExpPrice=Float.parseFloat(Price.getText().toString());
                    String category=CategoriesSpinner.getSelectedItem().toString();
                    if(category.equals("Other") && !(otherCategory.getText().toString().matches("")))
                        category=otherCategory.getText().toString();
                    if(ExpPrice>Currentbudget)
                    {
                        Toast.makeText(AddExpenseActivity.this,"You have no budget",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Wallet wallet=new Wallet();
                        float new_budget=wallet.EditCurrentBudget(id,Currentbudget,ExpPrice,AddExpenseActivity.this);
                        e.AddNewExpense(id, Name, category, ExpPrice, ExpenseDate.getText().toString(), AddExpenseActivity.this);
                        Intent i=new Intent(AddExpenseActivity.this,MyWalletActivity.class);
                        otherCategory.setVisibility(otherCategory.INVISIBLE);
                        i.putExtra("username",username);
                        i.putExtra("new_wallet",new_wallet);
                        i.putExtra("id",id);
                        i.putExtra("total_budget",Totalbudget);
                        i.putExtra("current_budget",new_budget);
                        i.putExtra("repeat",repeat);
                        i.putExtra("date",BudgetDate);
                        MyWalletActivity.a.finish();
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }
}