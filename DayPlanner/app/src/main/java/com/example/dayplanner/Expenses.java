package com.example.dayplanner;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

public class Expenses
{

    private String Category;
    private float price;
    private String time;
    private String name;
    private int Wallet_ID;
    private int Expense_ID;
    DayPlannerDatabase db;

    public Expenses()
    {}
    public Expenses(int ExpenseID,String name,String Category,float price,String time)
    {
        this.Expense_ID=ExpenseID;
        this.price=price;
        this.name=name;
        this.Category=Category;
        this.time=time;
//        this.Wallet_ID=WalletID;
    }

    public int getExpense_ID() {
        return Expense_ID;
    }

    public void setExpense_ID(int expense_ID) {
        Expense_ID = expense_ID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWallet_ID() {
        return Wallet_ID;
    }

    public void setWallet_ID(int wallet_ID) {
        Wallet_ID = wallet_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void AddNewExpense(int Wallet_ID, String name, String category, float amount, String time, Context context)
    {
        db=new DayPlannerDatabase(context);
        db.AddExpense(Wallet_ID,name,category,amount,time);
    }
    public void getExpenses(int Wallet_ID,Context context,ArrayList<Expenses>all)
    {
        db=new DayPlannerDatabase(context);
        Cursor c=db.getAllExpenses(Wallet_ID);
        while(!c.isAfterLast() && c!=null)
        {
            Expenses e=new Expenses();
            e.Expense_ID=Integer.parseInt(c.getString(0));
            e.name=c.getString(1);
            e.Category=c.getString(2);
            e.price=Float.parseFloat(c.getString(3));
            e.time=c.getString(4);
            all.add(e);
            c.moveToNext();
        }
    }
    public void DeleteExpense(int Expense_id,Context context)
    {
        db=new DayPlannerDatabase(context);
        db.deleteExpense(Expense_id);
    }
}
