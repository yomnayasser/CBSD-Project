package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class NewReminderActivity extends AppCompatActivity {
    Button btnDatePicker, btnTimePicker;
    FloatingActionButton btnCreateReminder, btnDeleteReminder;
    EditText txtDate, txtTime, txtName;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String dateStr, timeStr, username;
    String rID;
    boolean AddReminder;
    DayPlannerDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

//        username = getIntent().getExtras().getString("username");
        username=getIntent().getExtras().getString("username");
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnCreateReminder = (FloatingActionButton) findViewById(R.id.fsaverem);
        btnDeleteReminder = (FloatingActionButton) findViewById(R.id.fdelrem);

        txtName=(EditText)findViewById(R.id.reminderNameBox);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        AddReminder = getIntent().getExtras().getBoolean("AddReminder");

        if(!AddReminder)
        {
            rID = getIntent().getExtras().getString("rID");
            txtDate.setText(getIntent().getExtras().getString("rdate"));
            txtTime.setText(getIntent().getExtras().getString("rtime"));
            txtName.setText(getIntent().getExtras().getString("rname"));
            btnDeleteReminder.setVisibility(View.VISIBLE);
        }
        else
        {
            btnDeleteReminder.setVisibility(View.GONE);
        }

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);




                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReminderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateStr = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                                txtDate.setText(dateStr);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewReminderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                timeStr = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                                txtTime.setText(timeStr);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        btnCreateReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DayPlannerDatabase(getApplicationContext());
                if(AddReminder)
                    db.AddReminder(username, txtName.getText().toString(), txtDate.getText().toString(), txtTime.getText().toString());
                else
                    db.UpdateReminder(username, rID, txtName.getText().toString(), txtDate.getText().toString(), txtTime.getText().toString());

                Intent i = new Intent(NewReminderActivity.this, RemindersActivity.class);
                startActivity(i);
            }
        });

        btnDeleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DayPlannerDatabase(getApplicationContext());
                db.DeleteReminder(rID);

                Intent i = new Intent(NewReminderActivity.this, RemindersActivity.class);
                startActivity(i);
            }
        });
    }
}