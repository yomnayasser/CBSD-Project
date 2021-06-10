package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class singleEventActivity extends AppCompatActivity {
String username; String type;
String name; String time;
float price; String date;
int id; DayPlannerDatabase dp;
Calendar calendar = Calendar.getInstance();
private SimpleDateFormat _12HourFormatter = new SimpleDateFormat("hh:mm a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        username = getIntent().getExtras().getString("username");
        type = getIntent().getExtras().getString("eventType");
        name = getIntent().getExtras().getString("eventName");
        date=getIntent().getExtras().getString("eventDate");
        time = getIntent().getExtras().getString("eventTime");
        id = getIntent().getExtras().getInt("eventID");
        price = getIntent().getExtras().getFloat("expensesPrice");



        Button save = (Button)findViewById(R.id.Eedit);
        Button edit = (Button)findViewById(R.id.EditTime_btn) ;
        TextView Type = (TextView) findViewById(R.id.EType);
        EditText Time = (EditText) findViewById(R.id.ETime);
        EditText Name = (EditText) findViewById(R.id.EName);
        EditText Price = (EditText) findViewById(R.id.EMoney);
        TextView priceTextView = (TextView) findViewById(R.id.EMoneytextView);
        priceTextView.setVisibility(View.INVISIBLE);
        Price.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        Time.setEnabled(false);
        Name.setEnabled(false);
        Price.setEnabled(false);

        if(type.equals("Expenses"))
        {

            Price.setVisibility(View.VISIBLE);
            priceTextView.setVisibility(View.VISIBLE);
            Price.setText(String.valueOf(price));
            Type.setTextColor(Color.BLUE);
        }
        else if(type.equals("Event"))
        {
            Type.setTextColor(Color.GREEN);
            save.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            Time.setEnabled(true);
            Name.setEnabled(true);
            Price.setEnabled(true);
        }
        Type.setText(type);
        Name.setText(name);
        Time.setText(time);

        TimePickerDialog.OnTimeSetListener TimePicked = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                Time.setText(_12HourFormatter.format(calendar.getTime()));
            }
        };
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(singleEventActivity.this,TimePicked,calendar
                        .get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp = new DayPlannerDatabase(getApplicationContext());
                dp.updateEvent(Name.getText().toString(),date,Time.getText().toString(),username,id);
                Toast.makeText(singleEventActivity.this,"Changes Saved",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(singleEventActivity.this, calenderActivity.class);
                i.putExtra("username",username);
                startActivity(i);

            }
        });

    }
}