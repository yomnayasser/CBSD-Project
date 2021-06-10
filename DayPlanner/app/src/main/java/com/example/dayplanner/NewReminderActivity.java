package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
    String selectedDate;
    boolean goCalender=false;
    DayPlannerDatabase db;
    int cID;
    boolean found=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        username=getIntent().getExtras().getString("username");
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnCreateReminder = (FloatingActionButton) findViewById(R.id.fsaverem);
        btnDeleteReminder = (FloatingActionButton) findViewById(R.id.fdelrem);

        txtName=(EditText)findViewById(R.id.reminderNameBox);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        CheckBox addToCalendar = (CheckBox)findViewById(R.id.addTRoCalender) ;
        addToCalendar.setVisibility(View.INVISIBLE);

        AddReminder = getIntent().getExtras().getBoolean("AddReminder");
        selectedDate= getIntent().getExtras().getString("date");

         if(selectedDate.length()!=0)
            {
                txtDate.setText(selectedDate);
                btnDatePicker.setVisibility(View.INVISIBLE);
                goCalender=true;
            }

        if(!AddReminder)
        {
            rID = getIntent().getExtras().getString("rID");
            txtDate.setText(getIntent().getExtras().getString("rdate"));
            txtTime.setText(getIntent().getExtras().getString("rtime"));
            txtName.setText(getIntent().getExtras().getString("rname"));
            btnDeleteReminder.setVisibility(View.VISIBLE);
            db = new DayPlannerDatabase(getApplicationContext());
            Cursor removedIDCursor=db.getRemovedEvents(username);
            while (!removedIDCursor.isAfterLast() && removedIDCursor != null)
            {
                int RemovId=removedIDCursor.getInt(0);
                String RemovType=removedIDCursor.getString(1);
                cID=removedIDCursor.getInt(2);
                if(RemovId==Integer.parseInt(rID) && RemovType.equals("Reminder"))
                {
                    found=true;
                }
                if(found)
                {
                    addToCalendar.setVisibility(View.VISIBLE);
                }
                removedIDCursor.moveToNext();
            }
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

                                String dd = String.valueOf(dayOfMonth);
                                if(dd.length() == 1)
                                    dd = "0" + dd;


                                String mm = String.valueOf(monthOfYear+1);
                                if(mm.length() == 1)
                                    mm = "0" + mm;

                                dateStr = dd + "/" + mm + "/" + String.valueOf(year);
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
                                String hr = String.valueOf(hourOfDay);
                                if(hr.length() == 1)
                                    hr = "0" + hr;

                                String min = String.valueOf(minute);
                                if(min.length() == 1)
                                    min = "0" + min;

                                timeStr = hr + ":" + min;
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
                {
                    db.UpdateReminder(username, rID, txtName.getText().toString(), txtDate.getText().toString(), txtTime.getText().toString());
                    if(addToCalendar.isChecked() && found)
                    {
                        db.deleteREvent(cID);
                        found=false;
                        addToCalendar.setVisibility(View.INVISIBLE);

                    }
                }
                if(goCalender)
                {
                    Intent i2 = new Intent(NewReminderActivity.this, calenderActivity.class);
                    i2.putExtra("username", username);
                    startActivity(i2);
                    goCalender=false;
                }
                else
                {
                    RemindersActivity.b.finish();
                    Intent i3 = new Intent(NewReminderActivity.this, RemindersActivity.class);
                    i3.putExtra("username", username);
                    startActivity(i3);
                }

                finish();
            }
        });

        btnDeleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindersActivity.b.finish();
                db = new DayPlannerDatabase(getApplicationContext());
                db.DeleteReminder(rID);

                Intent i = new Intent(NewReminderActivity.this, RemindersActivity.class);
                i.putExtra("username", username);
                startActivity(i);

//                finish();
            }
        });
    }
}