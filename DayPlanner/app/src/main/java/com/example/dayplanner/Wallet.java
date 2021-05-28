package com.example.dayplanner;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Wallet
{
    private int id;
    private float totalBudget;
    private float currentBudget;
    private String Repeat;
    private String date;
    private String username;
    DayPlannerDatabase db;
    public Wallet()
    {
    }

    public Wallet(float totalBudget, float currentBudget, String repeat, String start_date) {
        this.totalBudget = totalBudget;
        this.currentBudget=currentBudget;
        this.Repeat = repeat;
        this.date = start_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(float budget) {
        this.totalBudget = budget;
    }

    public float getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(float currentBudget) {
        this.currentBudget = currentBudget;
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
        this.totalBudget= Float.parseFloat(cursor.getString(1));
        this.currentBudget= Float.parseFloat(cursor.getString(2));
        this.Repeat= cursor.getString(3);
        this.date= cursor.getString(4);

        return this;
    }

    public void AddWallet(String username,float currentBudget,float totalBudget,String repeat,String start_date,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.AddNewWallet(currentBudget,totalBudget,repeat,username,start_date);
        getWallet(username,context);
    }

    public void EditWallet(String username,float totalBudget,String repeat,String start_date,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.EditWallet(totalBudget,repeat,username,start_date);
        getWallet(username,context);
    }
    public void BudgetRefill(String username,float currentBudget,String new_date,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.UpdateBudget(username,currentBudget,new_date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RepeatWeekly(Context context)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate activeDate= LocalDate.now();
        LocalDate dateBudgetEntered = LocalDate.parse(date,formatter);
        long differenceBetweenDates = ChronoUnit.WEEKS.between(dateBudgetEntered,activeDate);
        int difference=(int)differenceBetweenDates;

        if(difference==1)
        {
            date=activeDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            currentBudget = currentBudget+totalBudget;
            BudgetRefill(username,currentBudget,date,context);
        }
        else if(difference>1)
        {
            date=activeDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            currentBudget = currentBudget+(totalBudget*difference);
            BudgetRefill(username,currentBudget,date,context);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RepeatMonthly(Context context)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate activeDate= LocalDate.now();
        LocalDate dateBudgetEntered = LocalDate.parse(date,formatter);
        long differenceBetweenDates = ChronoUnit.MONTHS.between(dateBudgetEntered,activeDate);
        int difference=(int)differenceBetweenDates;
        if(difference==1)
        {
            date=activeDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            currentBudget = currentBudget+totalBudget;
            BudgetRefill(username,currentBudget,date,context);
        }
        else if(difference>1)
        {
            date=activeDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            currentBudget = currentBudget+(totalBudget*difference);
            BudgetRefill(username,currentBudget,date,context);
        }
    }
}
