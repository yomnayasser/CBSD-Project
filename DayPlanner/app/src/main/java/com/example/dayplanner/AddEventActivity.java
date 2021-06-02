package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {
String username; String selectedDate;
private SimpleDateFormat _12HourFormatter = new SimpleDateFormat("hh:mm a");
Calendar calendar = Calendar.getInstance();
DayPlannerDatabase dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        username=getIntent().getExtras().getString("username");
        selectedDate=getIntent().getExtras().getString("date");

        EditText name = (EditText)findViewById(R.id.newEventName);
        EditText date = (EditText)findViewById(R.id.newEventDate);
        EditText time = (EditText)findViewById(R.id.newEventTime);
        Button pickTime = (Button)findViewById(R.id.PickTime_btn);
        Button save= (Button)findViewById(R.id.save_btn);

        date.setText(selectedDate);

        TimePickerDialog.OnTimeSetListener TimePicked = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                time.setText(_12HourFormatter.format(calendar.getTime()));
            }
        };
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddEventActivity.this,TimePicked,calendar
                        .get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Date = date.getText().toString();
                String Time = time.getText().toString();
                if(Name.length()==0 || Date.length()==0)
                {
                    Toast.makeText(AddEventActivity.this, "Please enter the missing data!",Toast.LENGTH_LONG).show();
                }
                if(Time.length()==0)
                {
                    dp = new DayPlannerDatabase(AddEventActivity.this);
                    String t = "All-day";
                    dp.addEvent(Name,Date,t,username);
                    Intent i = new Intent(AddEventActivity.this, calenderActivity.class);
                    i.putExtra("username",username);
                    startActivity(i);
                }

                else
                {
                    dp = new DayPlannerDatabase(AddEventActivity.this);
                    dp.addEvent(Name,Date,Time,username);
                    Intent i = new Intent(AddEventActivity.this, calenderActivity.class);
                    i.putExtra("username",username);
                    startActivity(i);

                }

            }
        });
    }
}