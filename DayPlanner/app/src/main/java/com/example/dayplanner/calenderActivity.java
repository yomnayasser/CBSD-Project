package com.example.dayplanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class calenderActivity extends AppCompatActivity {

    CompactCalendarView calender;
    String username ; String name;
    String time; String date;
    String type; float price;
    String month;  String dateSplit[];
    int walletID; String temp [];
    String selectedDate; long epoch;
    Date covertDate;

    ArrayList<CalenderEventsClass> events=new ArrayList<CalenderEventsClass>();
    ArrayList<CalenderEventsClass> showenEvents;
    private SimpleDateFormat dateFromatMonth = new SimpleDateFormat("MMMM,YYYY", Locale.getDefault());
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
    private SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12
    private SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

    private SimpleDateFormat _24HourFormatter = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat _12HourFormatter = new SimpleDateFormat("hh:mm a");
    CalenderAdapter eventListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calender = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calender.setUseThreeLetterAbbreviation(false);
        username = getIntent().getExtras().getString("username");
        DayPlannerDatabase dp = new DayPlannerDatabase(this);
        Cursor cursor = dp.fetchAllReminders(username);

        //Get Reminders
        while (!cursor.isAfterLast()) {
            name = cursor.getString(0);
            date = cursor.getString(1);
            time = cursor.getString(2);
            type = "Reminder";
            String d[] = date.split("/");
            if (Integer.parseInt(d[0]) < 10) {
                d[0] = "0" + d[0];
            }
            if (Integer.parseInt(d[1]) < 10) {
                d[1] = "0" + d[1];
            }
            date = d[0] + "/" + d[1] + "/" + d[2];
            try {
                Date _24HourDt = _24HourFormatter.parse(time);
                time = _12HourFormatter.format(_24HourDt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CalenderEventsClass classObj = new CalenderEventsClass(username, name, date, time, type);
//            classObj.addNewEvent();
            events.add(classObj);
            String dateSplit[] = date.split("/");
            try {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                String month_name = month_date.format(c.getTime());
//                System.out.println(month_name);
                String DateFormat = dateSplit[0] + "/" + month_name + "/" + dateSplit[2];
//                System.out.println(DateFormat);
                Date date = formatter.parse(DateFormat);
                c.setTime(date);
                epoch = c.getTimeInMillis();
                Event ev2 = new Event(Color.RED, epoch, "event");
                calender.addEvent(ev2);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }

        Cursor cursor3 = dp.getWallet(username);
        while (!cursor3.isAfterLast()) {
            walletID = cursor3.getInt(0);
            cursor3.moveToNext();
        }

        //Get Expenses
        Cursor cursor2 = dp.getAllExpenses(walletID);
        while (!cursor2.isAfterLast()) {

            name = cursor2.getString(1);
            price = Float.parseFloat(cursor2.getString(3));
            temp = cursor2.getString(4).split(" at");
            date = temp[0];
            time = temp[1];
            type = "Expenses";
            CalenderEventsClass eventObj = new CalenderEventsClass(username, name, date, time, type, price);
//            eventObj.addNewEvent();
            events.add(eventObj);
            String dateSplit[] = date.split("/");
            try {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                String month_name = month_date.format(c.getTime());
                String DateFormat = dateSplit[0] + "/" + month_name + "/" + dateSplit[2];
                Date date = formatter.parse(DateFormat);
                c.setTime(date);
                epoch = c.getTimeInMillis();
                Event ev2 = new Event(Color.BLUE, epoch, "event");
                calender.addEvent(ev2);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursor2.moveToNext();
        }

        //Get Events
        Cursor cursor4 = dp.getEvents(username);
        while(!cursor4.isAfterLast())
        {
            name = cursor4.getString(0);
            date =  cursor4.getString(1);
            time = cursor4.getString(2);
            System.out.println(name);
            System.out.println(date);
            System.out.println(time);
            type = "Event";
            CalenderEventsClass eventObj = new CalenderEventsClass(username, name, date, time, type);
            events.add(eventObj);

            String dateSplit[] = date.split("/");
            try {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                String month_name = month_date.format(c.getTime());
                System.out.println(month_name);
                String DateFormat = dateSplit[0] + "/" + month_name + "/" + dateSplit[2];
                System.out.println(DateFormat);
                Date date = formatter.parse(DateFormat);
                c.setTime(date);
                epoch = c.getTimeInMillis();
                Event ev = new Event(Color.CYAN, epoch, "event");
                calender.addEvent(ev);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor4.moveToNext();
        }



//        Cursor cursor4=dp.getEvents(username);
//        while(!cursor4.isAfterLast())
//        {
//            System.out.println(cursor4.getString(0));
//            System.out.println(cursor4.getString(1));
//            System.out.println(cursor4.getString(2));
//            System.out.println(cursor4.getString(3));
//            System.out.println(cursor4.getString(4));
//            String date=cursor4.getString(1);
//            String dateSplit[]=date.split("/");
//            Calendar c =Calendar.getInstance();
//            try {
//                c.set(Calendar.MONTH,Integer.parseInt(dateSplit[1])-1);
//                String month_name = month_date.format(c.getTime());
////                System.out.println(month_name);
//                String DatFormat=dateSplit[0]+"/"+month_name+"/"+dateSplit[2];
//                Date finalDateFormat = formatter.parse(DatFormat);
//                c.setTime(finalDateFormat);
//                epoch=c.getTimeInMillis();
//            String dateSplit[]=date.split("/");
//            try {
//                Calendar c =Calendar.getInstance();
//                c.set(Calendar.MONTH,Integer.parseInt(dateSplit[1])-1);
//                String month_name = month_date.format(c.getTime());
//                System.out.println(month_name);
//                String DateFormat=dateSplit[0]+"/"+month_name+"/"+dateSplit[2];
//                System.out.println(DateFormat);
//                Date finalDateFormat = formatter.parse(DateFormat);
//                c.setTime(finalDateFormat);
//                epoch=c.getTimeInMillis();
//                if(cursor4.getString(3)=="Reminder")
//                {
//                    color=Color.RED;
//                }
//                else if (cursor4.getString(3)=="Expenses")
//                {
//                    color=Color.GREEN;
//                }
//                else if (cursor4.getString(3)=="Event")
//                {
//                    color=Color.YELLOW;
//                }
//                Event ev2 = new Event(color,epoch,"event");
//                calender.addEvent(ev2);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//            cursor4.moveToNext();
//        }
//        String todayDate="02/06/2021";
//        String datee[]=todayDate.split("/");
//        try {
//            Calendar c =Calendar.getInstance();
//            c.set(Calendar.MONTH,Integer.parseInt(datee[1])-1);
//            String month_name = month_date.format(c.getTime());
//            System.out.println(month_name);
//            String dateee=datee[0]+"/"+month_name+"/"+datee[2];
//            System.out.println(dateee);
//            Date date = formatter.parse(dateee);
//            c.setTime(date);
//          epoch=c.getTimeInMillis();
//            Event ev2 = new Event(Color.GREEN,epoch,"event2");
//            calender.addEvent(ev2);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Event ev2 = new Event(Color.GREEN,epoch2,"event2");
//        calender.addEvent(ev2);
//        Event ev3 = new Event(Color.YELLOW,epoch3,"event3");
//



        Button addEventBtn = (Button)findViewById(R.id.AddEvent_btn);
        addEventBtn.setVisibility(View.INVISIBLE);

        Button addReminderBtn = (Button)findViewById(R.id.AddReminder_btn);
        addReminderBtn.setVisibility(View.INVISIBLE);


        ListView eventsList =(ListView)findViewById(R.id.EventsList);
        eventsList.setVisibility(View.INVISIBLE);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);


        calender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                eventsList.setVisibility(View.VISIBLE);
            String test []=dateClicked.toString().split(" ");
            month= outputFormat.format(dateClicked);
            selectedDate = test[2]+"/"+month+"/"+test[5];
            showenEvents=(ArrayList<CalenderEventsClass>) events.clone();
            int iterator=events.size();
            for(int i =0;i<iterator;i++)
            {
                if(!events.get(i).getDate().equals(selectedDate))
                {
                    events.remove(i);
                    iterator-=1;
                    i=-1;
                }
            }
//                for(int i =0;i<events.size();i++)
//                {
//                    System.out.println("************");
//                    System.out.println("name: "+ events.get(i).getName());
//                    System.out.println("date: "+ events.get(i).getDate());
//                }
        eventListAdapter= new CalenderAdapter(calenderActivity.this,events);

        if(events!=null)
        {
            eventsList.setAdapter(eventListAdapter);
            registerForContextMenu(eventsList);
        }
        events=showenEvents;
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
                Intent i = new Intent(calenderActivity.this, AddEventActivity.class);
                i.putExtra("username",username);
                i.putExtra("date",selectedDate);
                startActivity(i);
            }
        });

        addReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(calenderActivity.this, NewReminderActivity.class);
                i.putExtra("AddReminder", true);
                i.putExtra("username",username);
                String dateSplit[] = selectedDate.split("/");
                if (Integer.parseInt(dateSplit[0]) < 10) {
                    dateSplit[0] = dateSplit[0].substring(1);
                }
                if (Integer.parseInt(dateSplit[1]) < 10) {
                    dateSplit[1] = dateSplit[1].substring(1);
                }
                selectedDate=dateSplit[0]+"/"+dateSplit[1]+"/"+dateSplit[2];
                i.putExtra("date",selectedDate);
                startActivity(i);
            }
        });

    }




}







