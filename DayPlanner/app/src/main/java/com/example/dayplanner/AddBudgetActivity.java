package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;
import java.util.Date;

public class AddBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);


        Bundle bundle = getIntent().getExtras();
        UserClass user = bundle.getParcelable("user");

        Bundle bundle2 = getIntent().getExtras();
        UserClass wallet = bundle2.getParcelable("wallet");

        //final String[] RepeatTime = {new String[1]};
        Spinner spinner = findViewById(R.id.RepeatSpinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("None");
        arrayList.add("Weekly");
        arrayList.add("Monthly");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // RepeatTime[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // RepeatTime[0] ="None";
            }
        });
        EditText BudgetText=(EditText)findViewById(R.id.budgetEditTxt);
        EditText Date=(EditText)findViewById(R.id.DateEditTxt);

        Button save=(Button)findViewById(R.id.saveBudgetBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wallet==null)
                {
                    //Wallet new_Wallet=new Wallet(Float.parseFloat(BudgetText.toString()), RepeatTime[0], Date,AddBudgetActivity.this);
                }
                Intent i=new Intent(AddBudgetActivity.this,MyWalletActivity.class);
                startActivity(i);
            }
        });
    }
}