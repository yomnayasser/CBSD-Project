package com.example.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
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
        db.execSQL("create table Notes(Notes_ID integer primary key autoincrement ,noteText text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoList(TodoList_ID integer primary key autoincrement ,name text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Reminder(Reminder_ID integer primary key autoincrement ,name text not null,time  DATETIME DEFAULT CURRENT_TIMESTAMP,user_username text,FOREIGN KEY(user_username) REFERENCES User(username))");
        db.execSQL("create table Wallet(Wallet_ID integer primary key autoincrement ,totalBudget float not null,currentBudget float not null,Repeat text,start_date text,user_username text,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoItem(TodoItem_ID integer primary key autoincrement ,name text not null,status text not null,TodoItemID integer,ReminderID integer,constraint TodoItemID FOREIGN KEY(TodoItemID) REFERENCES TodoList(TodoList_ID),constraint ReminderID FOREIGN KEY(ReminderID) REFERENCES Reminder(Reminder_ID))");
        db.execSQL("create table Expenses(Expenses_ID integer primary key autoincrement ,Category text not null,Amount float not null,time text,WalletID integer,FOREIGN KEY(WalletID) REFERENCES Wallet(Wallet_ID) )");
        db.execSQL("create table CalenderEvents(Calender_ID integer primary key autoincrement ,eventName text not null,eventDate text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username))");
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
    public String checkUsername(String Username)
    {
        DP_Database=getReadableDatabase();
        String [] arg ={Username};
        Cursor cursor = DP_Database.rawQuery("select username from User where username like ?",arg);
        cursor.moveToFirst();
        if(cursor.getCount()==0)
        {
            DP_Database.close();
            return "false";
        }
        else
        {
            DP_Database.close();
            return "true";
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

    public void AddNewWallet(float currentBudget,float totalBudget, String Repeat, String username,String start_date)
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ContentValues row = new ContentValues();
        row.put("user_username",username);
        row.put("totalBudget",totalBudget);
        row.put("currentBudget",currentBudget);
        row.put("Repeat",Repeat);
        row.put("start_date", start_date);
        DP_Database=getWritableDatabase();
        DP_Database.insert("Wallet",null,row);
        DP_Database.close();
    }
    public void EditWallet(float totalBudget,String Repeat,String username,String start_date)
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ContentValues row = new ContentValues();
        row.put("totalBudget",totalBudget);
        row.put("Repeat",Repeat);
        row.put("start_date", start_date);
        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"user_username like ?",new String []{username});
        DP_Database.close();
    }
    public Cursor getWallet(String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select  wallet_ID,totalBudget,currentBudget,repeat,start_date from Wallet where user_username='"+username+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;
    }
    public void UpdateBudget(String username,float new_budget,String new_date)
    {
        ContentValues row = new ContentValues();
        row.put("currentBudget",new_budget);
        row.put("start_date",new_date);
        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"user_username like ?",new String []{username});
        DP_Database.close();
    }
//    public float getBudget(String username)
//    {
//        DP_Database=getReadableDatabase();
//        Cursor cursor = DP_Database.rawQuery("select currentBudget from Wallet where username='"+ username +"'",null);
//
//        DP_Database.close();
//        return Float.parseFloat(cursor.getString(0));
//    }
    public void AddExpense(int walletID, String category, float amount, String time)
    {
        ContentValues row = new ContentValues();
        row.put("Wallet_ID",walletID);
        row.put("Category",category);
        row.put("Amount",amount);
        row.put("time", time);
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

    public void addEvent(String Name,String Date,String username)
    {
        ContentValues row = new ContentValues();

        row.put("eventName",Name);
        row.put("eventDate",Date);
        row.put("user_username",username);
        DP_Database=getWritableDatabase();
        DP_Database.insert("CalenderEvents",null,row);
        DP_Database.close();
    }

    public Cursor getEvents(String username,String date)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select eventName from CalenderEvents where user_username='"+ username +"' and eventDate='"+ date+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;

    }
}
