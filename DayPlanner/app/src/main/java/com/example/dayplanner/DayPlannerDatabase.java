package com.example.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("create table Wallet(Wallet_ID integer primary key autoincrement ,Budget integer not null,user_username integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoItem(TodoItem_ID integer primary key autoincrement ,name text not null,status text not null,TodoItemID integer,ReminderID integer,constraint TodoItemID FOREIGN KEY(TodoItemID) REFERENCES TodoList(TodoList_ID),constraint ReminderID FOREIGN KEY(ReminderID) REFERENCES Reminder(Reminder_ID))");
        db.execSQL("create table Expenses(Expenses_ID integer primary key autoincrement ,Category text not null,Amount integer not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,WalletID integer,FOREIGN KEY(WalletID) REFERENCES Wallet(Wallet_ID) )");
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
}
