package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RemindersActivity extends AppCompatActivity implements RemindersAdapter.myOnClickListener{
    private RecyclerView recyclerView;
    private RemindersAdapter remindersAdapter;
    private List<Reminder> reminders;
    String username;

    public static Activity b;

    ArrayList<ScheduledExecutorService> execs;

    ScheduledExecutorService exec;
    TimeHelper th;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        b=this;

        execs = new ArrayList<ScheduledExecutorService>();

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
//        currentTime = Calendar.getInstance().getTime().toString().split(" ")[3].replace(" ", "");
//        currentTime = currentTime.split(":")[0] + ":" + currentTime.split(":")[1];

        th = new TimeHelper();

//        String formattedTime = th.convTime("13:00");

//        TextView ff = (TextView)findViewById(R.id.test1);



//        try {
//            ff.setText(th.formatDate("09/06/2021", currentTime).toString() + "zzz\n" + Calendar.getInstance().getTime().toString() + "\n" + String.valueOf(th.getDateDifference(Calendar.getInstance().getTime(), th.formatDate("09/06/2021", currentTime))));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        ArrayList<String> toRemove = new ArrayList<String>();
        ArrayList<Integer> toRemoveidx = new ArrayList<Integer>();
        for(int i=0; i<reminders.size(); i++)
        {
//            String rtime = th.convTime(reminders.get(i).getrTime());
            String rtime = reminders.get(i).getrTime();
            String rdate = reminders.get(i).getrDate();
            String rname = reminders.get(i).getrName();
            int rid = Integer.parseInt(reminders.get(i).getrID());

            String currentDate = th.getCurrentDate();
            String currentTime = th.getCurrentTime();

            Date formattedCurrent, formattedRemDate;

            long timeDiff = 0;

            try {
                formattedCurrent = th.formatDate(currentDate, currentTime);
                formattedRemDate = th.formatDate(rdate, rtime);

//                timeDiff = (long) ((long)th.getDateDifference(formattedRemDate, formattedCurrent) / 60.0f);
                timeDiff = (long) ((long)th.getDateDifference(formattedRemDate, formattedCurrent) / (1000*60));

                if(timeDiff >= 0)
                {
                    execs.add(Executors.newScheduledThreadPool(reminders.size()));
//                exec = Executors.newScheduledThreadPool(1);

                    execs.get(execs.size()-1).schedule(new Runnable(){
                        @Override
                        public void run(){
//                MyMethod();
                            int reqCode = rid*3;
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            showNotification(getApplicationContext(), "Reminder", rname, intent, reqCode);
                        }
                    }, timeDiff, TimeUnit.MINUTES);
                }
                else
                {
                    db.DeleteReminder(reminders.get(i).getrID());
                    toRemove.add(reminders.get(i).getrID());
                    toRemoveidx.add(i);
                    continue;
                }


//                TextView ff = (TextView)findViewById(R.id.test1);
//                ff.setText(ff.getText().toString() + "\n" + "id: " + rid + "\n" + "\n" + String.valueOf(timeDiff));



            } catch (ParseException e) {
//                TextView ff = (TextView)findViewById(R.id.test1);
//                ff.setText(e.getMessage().toString());

            }

        }

        for(int i=0; i<toRemoveidx.size(); i++)
            reminders.remove((int)toRemoveidx.get(i));

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

    /**
     *
     * @param context
     * @param title  --> title to show
     * @param message --> details to show
     * @param intent --> What should happen on clicking the notification
     * @param reqCode --> unique code for the notification
     */

    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {
//        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }
}