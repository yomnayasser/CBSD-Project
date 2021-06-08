package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RemindersActivity extends AppCompatActivity implements RemindersAdapter.myOnClickListener{
    private RecyclerView recyclerView;
    private RemindersAdapter remindersAdapter;
    private List<Reminder> reminders;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        username=getIntent().getExtras().getString("username");

        getSupportActionBar().hide();
        reminders = new ArrayList<>();
        recyclerView = findViewById(R.id.remindersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        remindersAdapter = new RemindersAdapter(this, this);
        recyclerView.setAdapter(remindersAdapter);

        DayPlannerDatabase db = new DayPlannerDatabase(this);
        Cursor cursor = db.fetchAllReminders(username);

        while(!cursor.isAfterLast())
        {
            Reminder rem = new Reminder();
            rem.setrName(cursor.getString(0));
            rem.setrDate(cursor.getString(1));
            rem.setrTime(cursor.getString(2));
            rem.setrID(String.valueOf(cursor.getInt(3)));
            reminders.add(rem);
            cursor.moveToNext();
        }

        remindersAdapter.setReminder(reminders);


        FloatingActionButton newReminderBtn = (FloatingActionButton)findViewById(R.id.newrems);

        newReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RemindersActivity.this, NewReminderActivity.class);
                i.putExtra("AddReminder", true);
                i.putExtra("username", username);
                i.putExtra("date","");
                startActivity(i);
            }
        });


    }

    @Override
    public void myOnClick(int position) {
        Intent i = new Intent(RemindersActivity.this, NewReminderActivity.class);
        i.putExtra("username",username);
        i.putExtra("rname", reminders.get(position).getrName());
        i.putExtra("rID", String.valueOf(reminders.get(position).getrID()));
        i.putExtra("rdate", reminders.get(position).getrDate());
        i.putExtra("rtime", reminders.get(position).getrTime());
        i.putExtra("AddReminder", false);
        i.putExtra("date","");

        startActivity(i);
    }
}