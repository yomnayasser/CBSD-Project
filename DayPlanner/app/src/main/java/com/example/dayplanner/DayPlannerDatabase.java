package com.example.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DayPlannerDatabase extends SQLiteOpenHelper
{
    private static String databaseName="DP_Database";
    SQLiteDatabase DP_Database;
    public DayPlannerDatabase(Context context)
    {
        super(context,databaseName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table User(username text primary key ,name text not null,email text not null, password text not null )");
        db.execSQL("create table Notes(Notes_ID integer primary key autoincrement ,noteText text not null,user_username integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoList(TodoList_ID integer primary key autoincrement ,name text not null,user_username integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Reminder(Reminder_ID integer primary key autoincrement ,name text not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,user_username integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Wallet(Wallet_ID integer primary key autoincrement ,Budget float not null,Repeat text,start_date Text,user_username integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoItem(TodoItem_ID integer primary key autoincrement ,name text not null,status text not null,TodoItemID integer,ReminderID integer,constraint TodoItemID FOREIGN KEY(TodoItemID) REFERENCES TodoList(TodoList_ID),constraint ReminderID FOREIGN KEY(ReminderID) REFERENCES Reminder(Reminder_ID))");
        db.execSQL("create table Expenses(Expenses_ID integer primary key autoincrement ,Category text not null,Amount float not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,WalletID integer,FOREIGN KEY(WalletID) REFERENCES Wallet(Wallet_ID) )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Notes");
        db.execSQL("drop table if exists TodoList");
        db.execSQL("drop table if exists Reminder");
        db.execSQL("drop table if exists Wallet");
        db.execSQL("drop table if exists TodoItem");
        db.execSQL("drop table if exists Expenses");
        onCreate(db);
    }

    public Cursor fetchAllUser()
    {
        DP_Database=getReadableDatabase();
        String [] rowDetails={"username","name","email","password"};
        Cursor cursor= DP_Database.query("User",rowDetails,null,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        DP_Database.close();
        return cursor;
    }

    public void Signup(String name, String username,String email,String password)
    {
        ContentValues row = new ContentValues();
        row.put("username",username);
        row.put("name",name);
        row.put("email",email);
        row.put("password",password);
        DP_Database=getWritableDatabase();
        DP_Database.insert("User",null,row);
        DP_Database.close();

    }
    public boolean checkUsername(String username)
    {
        DP_Database=getReadableDatabase();
        String [] arg ={username};
        Cursor cursor = DP_Database.rawQuery("select name from User where username like ?",arg);
        cursor.moveToFirst();
        if(cursor==null)
        {
            DP_Database.close();
            return false;
        }
        else
        {
            DP_Database.close();
            return true;
        }
    }

    public String login(String username, String password)
    {
        DP_Database=getReadableDatabase();
//        String [] arg ={username,password};
        Cursor cursor = DP_Database.rawQuery("select username, password from User where username='"+ username +"' and password='"+ password+"'",null);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            DP_Database.close();
            return "false";
        }
        else
        {
            DP_Database.close();
            return cursor.getString(0);
        }

    }
    public void AddNewWallet(float Budget, String Repeat, Date start_date, String username)
    {
        ContentValues row = new ContentValues();
        row.put("username",username);
        row.put("Budget",Budget);
        row.put("Repeat",Repeat);
        row.put("start_date", String.valueOf(start_date));
        DP_Database=getWritableDatabase();
        DP_Database.insert("Wallet",null,row);
        DP_Database.close();
    }
    public void EditWallet(float Budget,String Repeat,Date start_date,String username)
    {
        ContentValues row = new ContentValues();
        row.put("Budget",Budget);
        row.put("Repeat",Repeat);
        row.put("start_date", String.valueOf(start_date));
        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"username like ?",new String []{username});
        DP_Database.close();
    }
    public Cursor getWallet(String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select * from Wallet where username='"+username+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;
    }
    public void ManageWallet(float Budget, String Repeat, Date start_date, String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor =DP_Database.rawQuery("select * from wallet where username= ?",new String []{username});
        if(cursor==null)
            AddNewWallet(Budget,Repeat,start_date,username);
        else
            EditWallet(Budget,Repeat,start_date,username);
    }
    public void UpdateBudget(String username,float new_budget)
    {
        ContentValues row = new ContentValues();
        row.put("Budget",new_budget);

        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"username like ?",new String []{username});
        DP_Database.close();
    }
    public float getBudget(String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select Budget from Wallet where username='"+ username +"'",null);

        DP_Database.close();
        return Float.parseFloat(cursor.getString(0));
    }
    public void AddExpense(int walletID, String category, float amount, DateTimeFormatter date)
    {
        ContentValues row = new ContentValues();
        row.put("Wallet_ID",walletID);
        row.put("Category",category);
        row.put("Amount",amount);
        row.put("time", String.valueOf(date));
        DP_Database=getWritableDatabase();
        DP_Database.insert("Expenses",null,row);
        DP_Database.close();
    }
    public Cursor getAllExpenses(int walletID)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select amount,category,time from expenses where wallet_id='"+walletID+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;
    }
    public void deleteExpense(int ExpenseID)
    {
        DP_Database=getWritableDatabase();
        DP_Database.delete("Expenses","expenses_id='"+ExpenseID+"'",null);
        DP_Database.close();
    }
}
