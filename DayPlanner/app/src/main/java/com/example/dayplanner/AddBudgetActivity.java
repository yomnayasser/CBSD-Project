package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        String username=getIntent().getExtras().getString("username");
        boolean new_wallet=getIntent().getExtras().getBoolean("new_wallet");
        int id=getIntent().getExtras().getInt("id");
        float CurrentBudget=getIntent().getExtras().getFloat("current_budget");
        float TotalBudget=getIntent().getExtras().getFloat("total_budget");
        String repeat=getIntent().getExtras().getString("repeat");
        String DateAdded=getIntent().getExtras().getString("date");

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
        StartDate.setFocusable(false);
        StartDate.setKeyListener(null);

        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        if(new_wallet==true)
        {
            BudgetText.setHint("0.0");
            spinner.setSelection(0);
            StartDate.setHint("DD/MM/YYYY");
        }
        else
        {
            BudgetText.setText(String.valueOf(TotalBudget));
            if(repeat.equals("None"))
                spinner.setSelection(0);
            else if(repeat.equals("Weekly"))
                spinner.setSelection(1);
            else if(repeat.equals("Monthly"))
                spinner.setSelection(2);
            StartDate.setText(DateAdded);
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
                if (BudgetText.getText().toString().matches("")) {
                    BudgetText.setError("this field cannot be empty");
                }
                else if (StartDate.getText().toString().matches(""))
                {
                    StartDate.setError("this field cannot be empty");
                    //Toast.makeText(AddBudgetActivity.this, "Please select start date", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String RepeatON = spinner.getSelectedItem().toString();
                    float enteredBudget = Float.parseFloat(BudgetText.getText().toString());
                    float currentBudget = Float.parseFloat(BudgetText.getText().toString());
                    String enteredDate = StartDate.getText().toString();

                    Wallet w=new Wallet();

                    if(new_wallet==true)
                        w.AddWallet(username,currentBudget,enteredBudget,RepeatON,enteredDate,AddBudgetActivity.this);
                    else
                    {
                        w.EditWallet(username  ,enteredBudget,RepeatON,enteredDate,AddBudgetActivity.this);
                        MyWalletActivity.a.finish();
                    }
                    Intent i=new Intent(AddBudgetActivity.this,MyWalletActivity.class);
                    i.putExtra("username",username);
                    i.putExtra("id",w.getId());
                    i.putExtra("total_budget",w.getTotalBudget());
                    i.putExtra("current_budget",w.getCurrentBudget());
                    i.putExtra("repeat",w.getRepeat());
                    i.putExtra("date",w.getDate());

                    boolean NEW_WALLET=false;
                    i.putExtra("new_wallet",NEW_WALLET);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}