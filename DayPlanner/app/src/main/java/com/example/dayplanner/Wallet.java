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
    UserClass user;
    Context context;
    DayPlannerDatabase db;
    public Wallet(){
        this.id = 0;
        this.budget = 0;
        this.Repeat = null;
        this.date = null;
    }
    public Wallet(int id, float budget, String repeat, Date date,Context context) {
        this.id = id;
        this.budget = budget;
        this.Repeat = repeat;
        this.date = date;
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

    public boolean getWallet(UserClass user) throws ParseException {
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        db=new DayPlannerDatabase(context);
        Cursor cursor=db.getWallet(user.getUsername());
        if(cursor!=null)
        {
            this.id= Integer.parseInt(cursor.getString(0));
            this.budget= Float.parseFloat(cursor.getString(1));
            this.Repeat= cursor.getString(2);
            this.date= formatter1.parse(cursor.getString(3));
            return true;
            //lw true h5le el home activity twdene l my wallet
        }
        //lw false home hywdene l add budget activity
        return false;
    }

    public void EditWallet(float budget, String repeat, Date date,UserClass user)
    {
        db =new DayPlannerDatabase(context);
//        if(repeat=="weekly")
//        {
//
//        }
        db.ManageWallet(budget,repeat,date,user.getUsername());
    }
//    public Date weeklyRepeat(Date date)
//    {
////        return date.plus
//    }
    public void UpdateDateAutomatically()
    {

    }
//    public boolean CheckIfNew()
//    {
//        String username=user.getUsername();
//        Cursor found=db.getWallet(username);
//        if(found==null)
//            return true;
//        return false;
//    }
}
