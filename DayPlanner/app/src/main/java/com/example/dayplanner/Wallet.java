package com.example.dayplanner;

import android.app.AlarmManager;
import android.content.Context;
import android.database.Cursor;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Wallet
{
    private int id;
    private float budget;
    private String Repeat;
    private Date date;
    private String username;
    Context context;
    DayPlannerDatabase db;
    public Wallet(String username,Context context){
       this.username=username;
        this.context=context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Wallet(float budget, String repeat, String username, Context context) {
        this.budget = budget;
        this.Repeat = repeat;
//        this.date = date;
        this.context=context;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getWallet(){
//        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        db=new DayPlannerDatabase(context);
        Cursor cursor=db.getWallet(username);
        if(cursor!=null && cursor.getCount() != 0)
        {
            this.id= Integer.parseInt(cursor.getString(0));
            this.budget= Float.parseFloat(cursor.getString(1));
            this.Repeat= cursor.getString(2);
//          this.date= formatter1.parse(cursor.getString(3));
//            cursor.close();
            return false;
        }
        return true;
    }

    public void EditWallet()
    {
        db =new DayPlannerDatabase(this.context);
        db.ManageWallet(this.budget,this.Repeat,this.username);
    }
//    public Date weeklyRepeat(Date date)
//    {
////        return date.plus
//    }
    public void UpdateDateAutomatically()
    {

    }
}
