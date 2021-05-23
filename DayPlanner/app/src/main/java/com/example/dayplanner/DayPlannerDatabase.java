package com.example.dayplanner;

import android.content.Context;
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
        db.execSQL("create table User(username text primary key ,name text not null )");
        db.execSQL("create table Notes(Notes_ID integer primary key autoincrement ,noteText text not null,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoList(TodoList_ID integer primary key autoincrement ,name text not null,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Reminder(Reminder_ID integer primary key autoincrement ,name text not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Wallet(Wallet_ID integer primary key autoincrement ,Budget integer not null,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoItem(TodoItem_ID integer primary key autoincrement ,name text not null,status text not null,FOREIGN KEY(TodoItemID) REFERENCES TodoList(TodoList_ID),FOREIGN KEY(ReminderID) REFERENCES Reminder(Reminder_ID))");
        db.execSQL("create table Expenses(Expenses_ID integer primary key autoincrement ,Category text not null,Amount integer not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(WalletID) REFERENCES Wallet(Wallet_ID) )");
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
}