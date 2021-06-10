package com.example.dayplanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class calenderActivity extends AppCompatActivity{

CompactCalendarView calender;
    public static Activity c;
String username ; String name;
String time; String date;
String type; float price;
String month;  String dateSplit[];
int walletID; String temp [];
String selectedDate; long epoch;
int id; DayPlannerDatabase dp;
int Listindex=0; boolean found=false;

ArrayList<CalenderEventsClass> events=new ArrayList<CalenderEventsClass>();
ArrayList<CalenderEventsClass> showenEvents;
ArrayList<CalenderEventsClass> foundList;
List<Integer> Rlist=new ArrayList<Integer>();
List<Integer> Elist=new ArrayList<Integer>();


    private SimpleDateFormat dateFromatMonth = new SimpleDateFormat("MMMM,YYYY", Locale.getDefault());
private SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
private SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12
private SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
private SimpleDateFormat _24HourFormatter = new SimpleDateFormat("HH:mm");
private SimpleDateFormat _12HourFormatter = new SimpleDateFormat("hh:mm a");

CalenderAdapter eventListAdapter;
HashMap<String, ArrayList<CalenderEventsClass>> eventHashMap = new HashMap<String, ArrayList<CalenderEventsClass>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        c=this;
        calender = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calender.setUseThreeLetterAbbreviation(false);
        username = getIntent().getExtras().getString("username");
        dp = new DayPlannerDatabase(this);

        //Check for deleted id fom previous times
        Cursor removedIDCursor=dp.getRemovedEvents(username);
        System.out.println("count"+removedIDCursor.getCount());
        while (!removedIDCursor.isAfterLast() && removedIDCursor != null) {
            int RemovId=removedIDCursor.getInt(0);
            String RemovType=removedIDCursor.getString(1);

            if(RemovType.equals("Reminder"))
            {
                Rlist.add(RemovId);
            }
            else if(RemovType.equals("Expenses"))
            {
                Elist.add(RemovId);
            }

            removedIDCursor.moveToNext();
        }
        Cursor cursor = dp.fetchAllReminders(username);

        //Get Reminders
        while (!cursor.isAfterLast() && cursor != null) {

            id=cursor.getInt(3);
            for(int i=0;i<Rlist.size();i++)
            {
                System.out.println("id in loop"+i);
                if(Rlist.get(i)==id)
                {
                    found=true;
                    break;
                }
            }

            if(!found)
            {
                name = cursor.getString(0);
                date = cursor.getString(1);
                time = cursor.getString(2);
                type = "Reminder";
                CalenderEventsClass classObj = new CalenderEventsClass(username, name, date, time, type,id,Listindex);
                Listindex++;
                events.add(classObj);
                String dateSplit[] = date.split("/");
                try {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                    String month_name = month_date.format(c.getTime());
                    String DateFormat = dateSplit[0] + "/" + month_name + "/" + dateSplit[2];
                    Date date = formatter.parse(DateFormat);
                    c.setTime(date);
                    epoch = c.getTimeInMillis();
                    Event ev2 = new Event(Color.RED, epoch, "event");
                    calender.addEvent(ev2);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                found=false;
            }

            cursor.moveToNext();
        }

        //Get Wallet ID
        Cursor cursor3 = dp.getWallet(username);
        while (!cursor3.isAfterLast() && cursor != null) {
            walletID = cursor3.getInt(0);
            cursor3.moveToNext();
        }

        //Get Expenses
        Cursor cursor2 = dp.getAllExpenses(walletID);
        while (!cursor2.isAfterLast() && cursor != null) {

            id= cursor2.getInt(0);
            for(int i=0;i<Elist.size();i++)
            {
                if(Elist.get(i)==id)
                {
                    found=true;
                    break;
                }
            }

            if(!found)
            {
                name = cursor2.getString(1);
                price = Float.parseFloat(cursor2.getString(3));
                temp = cursor2.getString(4).split(" at");
                date = temp[0];
                time = temp[1];
                type = "Expenses";
                CalenderEventsClass expensesObj = new CalenderEventsClass(username, name, date, time, type, price, id,Listindex);
                Listindex++;
                events.add(expensesObj);
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
            }
            else
            {
                found=false;
            }
            cursor2.moveToNext();
        }

        //Get Events
        Cursor cursor4 = dp.getEvents(username);
        while (!cursor4.isAfterLast() && cursor != null) {
            name = cursor4.getString(0);
            date = cursor4.getString(1);
            time = cursor4.getString(2);
            id = cursor4.getInt(3);
            type = "Event";
            CalenderEventsClass eventObj = new CalenderEventsClass(username, name, date, time, type, id,Listindex);
            Listindex++;
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
                Event ev = new Event(Color.GREEN, epoch, "event");
                calender.addEvent(ev);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor4.moveToNext();
        }

        Button addEventBtn = (Button) findViewById(R.id.AddEvent_btn);
        addEventBtn.setVisibility(View.INVISIBLE);

        Button addReminderBtn = (Button) findViewById(R.id.AddReminder_btn);
        addReminderBtn.setVisibility(View.INVISIBLE);


        ListView eventsList = (ListView) findViewById(R.id.EventsList);
        eventsList.setVisibility(View.INVISIBLE);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);


        calender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                eventsList.setVisibility(View.VISIBLE);
                String test[] = dateClicked.toString().split(" ");
                month = outputFormat.format(dateClicked);
                selectedDate = test[2] + "/" + month + "/" + test[5];
                showenEvents = (ArrayList<CalenderEventsClass>) events.clone();
                int iterator = events.size();
                for (int i = 0; i < iterator; i++) {
                    if (!events.get(i).getDate().equals(selectedDate)) {
                        events.remove(i);
                        iterator -= 1;
                        i = -1;
                    }
                }


                if (events != null) {
                    eventListAdapter = new CalenderAdapter(calenderActivity.this, events);
                    eventsList.setAdapter(eventListAdapter);
                    registerForContextMenu(eventsList);
                    eventHashMap.put(selectedDate, events);
                    events = showenEvents;
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
                Intent i = new Intent(calenderActivity.this, AddEventActivity.class);
                i.putExtra("username", username);
                i.putExtra("date", selectedDate);
                startActivity(i);
                finish();
            }
        });

        addReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(calenderActivity.this, NewReminderActivity.class);
                i.putExtra("AddReminder", true);
                i.putExtra("username", username);
                i.putExtra("date", selectedDate);
                startActivity(i);
            }
        });


        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (String i : eventHashMap.keySet()) {
                    if (i.equals(selectedDate)) {
                        foundList = eventHashMap.get(i);
                        break;
                    }
                }


                Intent i = new Intent(calenderActivity.this, singleEventActivity.class);
                i.putExtra("username", username);
                i.putExtra("eventType", foundList.get(position).getType());
                i.putExtra("eventName", foundList.get(position).getName());
                i.putExtra("eventDate", foundList.get(position).getDate());
                i.putExtra("eventTime", foundList.get(position).getTime());
                i.putExtra("eventID", foundList.get(position).getId());
                i.putExtra("expensesPrice", foundList.get(position).getExpenssesPrice());
                startActivity(i);
                
            }
        });



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.calender_view_edit,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index=(int)eventListAdapter.getItemId(info.position);

        for (String i : eventHashMap.keySet()) {
            if (i.equals(selectedDate)) {
                foundList = eventHashMap.get(i);
                break;
            }
        }

        String type=foundList.get(index).getType();
        if(id==R.id.removeEvent)
        {

            if(type.equals("Reminder") || type.equals("Expenses") )
            {
                int indexFound=foundList.get(index).getIndex();
                int idFound=foundList.get(index).getId();
                System.out.println("found"+idFound);
                dp.addRemovedEvent(idFound,type,username);
                foundList.remove(index);
                events.remove(indexFound);
                eventListAdapter.notifyDataSetChanged();
                return true;
            }
            else
            {
                int indexFound=foundList.get(index).getIndex();
                int idFound=foundList.get(index).getId();
                dp.deleteEvent(idFound);
                events.remove(indexFound);
                foundList.remove(index);
                eventListAdapter.notifyDataSetChanged();
                return true;
            }

        }
        finish();

        return super.onContextItemSelected(item);
    }


}








