package com.example.dayplanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class calenderActivity extends AppCompatActivity {
    CompactCalendarView calender;
    long epoch1;
    long epoch2;
    long epoch3;
    //List<Event> events;
    private SimpleDateFormat dateFromatMonth = new SimpleDateFormat("MMMM,YYYY", Locale.getDefault());
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Button addEventBtn = (Button)findViewById(R.id.AddEvent_btn);
        addEventBtn.setVisibility(View.INVISIBLE);

        Button addReminderBtn = (Button)findViewById(R.id.AddReminder_btn);
        addReminderBtn.setVisibility(View.INVISIBLE);

        EditText addEventName = (EditText)findViewById(R.id.NewEvent_text);
        addEventName.setVisibility(View.INVISIBLE);

        Button saveBtn = (Button)findViewById(R.id.save_btn);
        saveBtn.setVisibility(View.INVISIBLE);

        ListView eventsList =(ListView)findViewById(R.id.EventsList);
        eventsList.setVisibility(View.INVISIBLE);


        ArrayAdapter<String> eventListAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        eventsList.setAdapter(eventListAdapter);
        String Expenses="Expenses: ";
        String Reminder="Reminder: ";
        String Event="Event: ";
        eventListAdapter.add(Expenses+"Shopping");
        eventListAdapter.add(Reminder+"Reminder");
        eventListAdapter.add(Expenses+"Food");


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        calender= (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calender.setUseThreeLetterAbbreviation(true);
        String todayDate="28/May/2021";
        String todayDate2="29/May/2021";
        String todayDate3="29/June/2021";
        try {
            Date date = formatter.parse(todayDate);
            Date date2 = formatter.parse(todayDate2);
            Date date3 = formatter.parse(todayDate3);
            Calendar c =Calendar.getInstance();
            c.setTime(date);
            epoch1=c.getTimeInMillis();
            Calendar c2 =Calendar.getInstance();
            c2.setTime(date2);
            epoch2=c2.getTimeInMillis();
            System.out.println(epoch2);
            Calendar c3 =Calendar.getInstance();
            c3.setTime(date3);
            epoch3=c3.getTimeInMillis();
            System.out.println(epoch3);

//            System.out.println("dddd");
        } catch (ParseException e) {
            e.printStackTrace();
//            System.out.println("sssssss");
        }


        com.github.sundeepk.compactcalendarview.domain.Event ev1 = new Event(Color.RED,epoch1,"event");
        calender.addEvent(ev1);
        Event ev2 = new Event(Color.GREEN,epoch2,"event2");
        calender.addEvent(ev2);
        Event ev3 = new Event(Color.YELLOW,epoch3,"event3");
        calender.addEvent(ev3);

        calender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                eventsList.setVisibility(View.INVISIBLE);
                String test []=dateClicked.toString().split(" ");
                String compareString = test[2]+"/"+test[1]+"/"+test[5];
                System.out.println(compareString);
                if(compareString.compareTo(todayDate)==0 || compareString.compareTo(todayDate2)==0)
                {
                    eventsList.setVisibility(View.VISIBLE);

                }
                addEventBtn.setVisibility(View.VISIBLE);
                addReminderBtn.setVisibility(View.VISIBLE);


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFromatMonth.format(firstDayOfNewMonth));
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventName.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventListAdapter.add(Event+addEventName.getText().toString());

            }
        });
    }
}