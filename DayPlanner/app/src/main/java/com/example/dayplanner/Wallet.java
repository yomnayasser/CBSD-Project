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
    private String date;
    private String username;
    DayPlannerDatabase db;
    public Wallet()
    {
    }

    public Wallet(float budget, String repeat, String username,String start_date) {
        this.budget = budget;
        this.Repeat = repeat;
        this.date = start_date;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean checkWallet(String username,Context context)
    {
        db=new DayPlannerDatabase(context);
        Cursor cursor=db.getWallet(username);
        if(cursor!=null && cursor.getCount() != 0)
            return false;
        return true;

    }

    public Wallet getWallet(String username,Context context)
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        db=new DayPlannerDatabase(context);
        Cursor cursor=db.getWallet(username);
        this.username=username;
        this.id= Integer.parseInt(cursor.getString(0));
        this.budget= Float.parseFloat(cursor.getString(1));
        this.Repeat= cursor.getString(2);
        this.date= cursor.getString(3);

        return this;
    }

    public void AddWallet(String username,float budget,String repeat,String start_date,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.AddNewWallet(budget,repeat,username,start_date);
    }

    public void EditWallet(String username,float budget,String repeat,String start_date,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.EditWallet(budget,repeat,username,start_date);
    }

////    public Date weeklyRepeat(Date date)
//    {
////        return date.plus
//    }
//    public void UpdateDateAutomatically()
//    {
//
//    }
}
