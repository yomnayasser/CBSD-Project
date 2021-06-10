package com.example.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DayPlannerDatabase extends SQLiteOpenHelper
{
    private static String databaseName="DP_Database";
    SQLiteDatabase DP_Database;
    public DayPlannerDatabase(Context context)
    {
        super(context,databaseName,null,5);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table User(username text primary key ,name text not null,email text not null, password text not null )");
        db.execSQL("create table Notes(Notes_ID integer primary key autoincrement ,noteText text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoList(TodoList_ID integer primary key autoincrement ,name text not null,user_username integer, ftime integer,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table Reminder(Reminder_ID integer primary key autoincrement, reminder_name text, reminder_date text, reminder_time text, user_username text, FOREIGN KEY(user_username) REFERENCES User(username))");
        db.execSQL("create table Wallet(Wallet_ID integer primary key autoincrement ,totalBudget float not null,currentBudget float not null,Repeat text,start_date text,user_username text,FOREIGN KEY(user_username) REFERENCES User(username) )");
        db.execSQL("create table TodoItem(TodoItem_ID integer primary key autoincrement, name text not null, status integer not null, TodoListID integer, FOREIGN KEY(TodoListID) REFERENCES TodoList(TodoList_ID))");
        db.execSQL("create table Expenses(Expenses_ID integer primary key autoincrement ,name text not null,Category text not null,Amount float not null,time text,WalletID integer,FOREIGN KEY(WalletID) REFERENCES Wallet(Wallet_ID) )");
        db.execSQL("create table CalenderEvents(Event_ID integer primary key autoincrement ,eventName text not null,eventDate text not null,eventTime text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username))");
        db.execSQL("create table RemovedCalenderEvents(E_ID integer primary key autoincrement ,RemovedID int not null,Rtype text not null,user_username text,FOREIGN KEY(user_username) REFERENCES User(username))");
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
        db.execSQL("drop table if exists CalenderEvents");
        db.execSQL("drop table if exists RemovedCalenderEvents");
        onCreate(db);
    }

    //User Functions
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
    public void EditWallet(float totalBudget,float currentBudget,String Repeat,String username,String start_date)
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ContentValues row = new ContentValues();
        row.put("totalBudget",totalBudget);
        row.put("currentBudget",currentBudget);
        row.put("Repeat",Repeat);
        row.put("start_date", start_date);
        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"user_username like ?",new String []{username});
        DP_Database.close();
    }
    public Cursor getWallet(String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select wallet_ID,totalBudget,currentBudget,repeat,start_date from Wallet where user_username='"+username+"'",null);
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
    public void UpdateCurrentBudget(int Wallet_ID,float new_budget)
    {
        String [] arg ={String.valueOf(Wallet_ID)};
        ContentValues row = new ContentValues();
        row.put("currentBudget",new_budget);
        DP_Database=getWritableDatabase();
        DP_Database.update("Wallet",row,"Wallet_ID like ?",arg);
        DP_Database.close();
    }
    public void AddExpense(int walletID,String name, String category, float amount, String time)
    {
        ContentValues row = new ContentValues();
        row.put("Name",name);
        row.put("Category",category);
        row.put("Amount",amount);
        row.put("time", time);
        row.put("WalletID",walletID);
        DP_Database=getWritableDatabase();
        DP_Database.insert("Expenses",null,row);
        DP_Database.close();
    }
    public Cursor getAllExpenses(int walletID)
    {
        DP_Database=getReadableDatabase();
        String [] arg ={String.valueOf(walletID)};
        Cursor cursor = DP_Database.rawQuery("select Expenses_ID, name, category, amount, time from expenses where walletid like ?",arg);
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
    public void EditExpense(int ExpenseID,String name,String Category,float price,String date)
    {
        String [] arg ={String.valueOf(ExpenseID)};
        ContentValues row = new ContentValues();
        row.put("Name",name);
        row.put("Category",Category);
        row.put("Amount",price);
        row.put("time", date);
        DP_Database=getWritableDatabase();
        DP_Database.update("Expenses",row,"Expenses_ID like ?",arg);
        DP_Database.close();
    }

    //Calender Functions
    public void addEvent(String Name,String Date,String Time,String username)
    {
        ContentValues row = new ContentValues();

        row.put("eventName",Name);
        row.put("eventDate",Date);
        row.put("eventTime",Time);
        row.put("user_username",username);
        DP_Database=getWritableDatabase();
        DP_Database.insert("CalenderEvents",null,row);
        DP_Database.close();
    }

    public Cursor getEvents(String username)
    {
        DP_Database=getReadableDatabase();
        String [] arg ={username};
        Cursor cursor = DP_Database.rawQuery("select eventName,eventDate,eventTime,Event_ID from CalenderEvents where user_username like ?",arg);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;

    }
    public void deleteEvent(int eventID)
    {
        DP_Database=getWritableDatabase();
        DP_Database.delete("CalenderEvents","Event_ID='"+eventID+"'",null);
        DP_Database.close();
    }

    public void updateEvent(String Name,String Date,String Time,String username,int ID)
    {

        ContentValues row = new ContentValues();
        row.put("eventName",Name);
        row.put("eventDate",Date);
        row.put("eventTime",Time);
        row.put("user_username",username);
        DP_Database=getWritableDatabase();
        DP_Database.update("CalenderEvents",row," user_username like ? and Event_ID like ?",new String []{username, String.valueOf(ID)});
        DP_Database.close();
    }

    public Cursor getRemovedEvents(String username)
    {
        DP_Database=getReadableDatabase();
        String [] arg ={username};
        Cursor cursor = DP_Database.rawQuery("select RemovedID,Rtype,E_ID from RemovedCalenderEvents where user_username like ?",arg);
        if(cursor!=null)
            cursor.moveToFirst();
        DP_Database.close();
        return cursor;
    }
    public void addRemovedEvent(int removedID,String type,String Username)
    {
        ContentValues row = new ContentValues();
        row.put("RemovedID",removedID);
        row.put("Rtype",type);
        row.put("user_username",Username);
        DP_Database=getWritableDatabase();
        DP_Database.insert("RemovedCalenderEvents",null,row);
        DP_Database.close();
    }

    public void deleteREvent(int ID)
    {
        DP_Database=getWritableDatabase();
        DP_Database.delete("RemovedCalenderEvents","E_ID='"+ID+"'",null);
        DP_Database.close();
    }
//    public void deleteOuterEventTable()
//    {
//        DP_Database=getWritableDatabase();
//        DP_Database.execSQL("delete from OuterCalenderEvents");
//    }

    // REMINDERS FUNCTIONS
    public Cursor fetchAllReminders(String username)
    {
        DP_Database=getReadableDatabase();
        String [] arg={username};
//        Cursor cursor= DP_Database.query("Reminder",rowDetails,null,null,null,null,null);
        Cursor cursor = DP_Database.rawQuery("SELECT reminder_name, reminder_date, reminder_time, Reminder_ID FROM Reminder where user_username like ?",arg);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        DP_Database.close();
        return cursor;
    }

    public void AddReminder(String username, String reminderName, String date, String time)
    {
        ContentValues row = new ContentValues();
        row.put("user_username", username);
        row.put("reminder_name", reminderName);
        row.put("reminder_date",date);
        row.put("reminder_time",time);
        DP_Database=getWritableDatabase();
        DP_Database.insert("Reminder",null,row);
        DP_Database.close();
    }

    public void UpdateReminder(String username, String rID, String newName, String newDate, String newTime)
    {
        ContentValues row = new ContentValues();
        row.put("reminder_name", newName);
        row.put("reminder_date",newDate);
        row.put("reminder_time",newTime);
        DP_Database=getWritableDatabase();
        DP_Database.update("Reminder",row,"user_username like ? and Reminder_ID like ?", new String []{username, rID});
        DP_Database.close();
    }

    public void DeleteReminder(String rID)
    {
        DP_Database=getWritableDatabase();
        DP_Database.delete("Reminder","Reminder_ID like ?",new String []{rID});
        DP_Database.close();
    }

    public Cursor getNotes(String username){
        DP_Database = getReadableDatabase();

        String[] par = {username};
        Cursor c =DP_Database.rawQuery("SELECT Notes_ID, noteText FROM Notes WHERE user_username like ?", par);
        if (c != null)
            c.moveToFirst();

        DP_Database.close();
        return c;
    }

    public void addNewNote(String username, String note){
        DP_Database = getWritableDatabase();

        ContentValues addedNote = new ContentValues();
        addedNote.put("user_username", username);
        addedNote.put("noteText", note);

        DP_Database.insert("Notes", null, addedNote);
        DP_Database.close();
    }

    public Cursor getNote(String content, String username){
        DP_Database = getWritableDatabase();
        String[] args = {content, username};
        Cursor id = DP_Database.rawQuery("SELECT Notes_ID FROM Notes WHERE noteText like ? and user_username like ?", args);
        if (id != null)
            id.moveToFirst();
        DP_Database.close();
        return id;
    }

    public void deleteNote(String ID){

        DP_Database = getWritableDatabase();
        DP_Database.delete("Notes", "Notes_ID like ?", new String[] {ID});
        DP_Database.close();

    }

    public void editNote(String ID, String username, String newNote){
        DP_Database = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("noteText", newNote);
        DP_Database.update("Notes", cv, "Notes_ID like ? and user_username like ?",
                new String[] {ID, username});

        DP_Database.close();
    }

//    public void openDB(){
//        DP_Database=this.getWritableDatabase();
//    }

    public long insertTask(todo task, String TodoListID){
        DP_Database=this.getWritableDatabase();
        ContentValues Cont=new ContentValues();
        Cont.put("TodoListID",TodoListID);
        Cont.put("name",task.getTask());
        Cont.put("status",0);
//        Log.e(TAG, String.valueOf(DP_Database.insert("TodoItem",null,Cont)));
        long num = DP_Database.insert("TodoItem",null,Cont);
        DP_Database.close();
        return num;
    }
    public List<todo> getTasks(String username, String listname){
        List<todo> tasklist=new ArrayList<>();
//        DP_Database.beginTransaction();
        DP_Database=getReadableDatabase();
        Cursor cursor=null;
        try {
            cursor = DP_Database.rawQuery("select name, status, TodoItem_ID from TodoItem where TodoListID=(select TodoList_ID from TodoList where name='" + listname + "')",null);
            if(cursor!=null){
                if (cursor.moveToFirst()){
                    while (cursor.moveToNext()){
                        todo task=new todo();
                        task.setId(cursor.getInt(2));
                        task.setTask(cursor.getString(0));
                        task.setStatus(cursor.getInt(1));
                        tasklist.add(task);

                    }
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            DP_Database.close();
//            cursor.close();
        }
        try{
//            DP_Database.endTransaction();
            cursor.close();
        }catch (Exception ex)
        {
            Log.e(TAG, "GET TASKS FUNCTION: " + ex.getMessage().toString());
        }
        return tasklist;
    }

    public void updateStatus(int id,int status){
        DP_Database=this.getWritableDatabase();
        ContentValues Cont=new ContentValues();
        Cont.put("status",status);
        DP_Database.update("TodoItem",Cont,"TodoItem_ID=?",new String[] {String.valueOf(id)});
        DP_Database.close();
    }
    public void updateTask(int id,String task){
        DP_Database = getWritableDatabase();
        ContentValues Cont=new ContentValues();
        Cont.put("name",task);
        DP_Database.update("TodoItem",Cont,"TodoItem_ID=?",new String[] {String.valueOf(id)});
        DP_Database.close();
    }
    public void deleteTask(int id){
        DP_Database = getWritableDatabase();
        DP_Database.delete("TodoItem","TodoItem_ID=?",new String[]{String.valueOf(id)});
        DP_Database.close();
    }

    public List<lists_todo> fetchAllLists(String username)
    {
        DP_Database=getReadableDatabase();
        Cursor cursor = DP_Database.rawQuery("select name, TodoList_ID, ftime from TodoList where user_username=?",new String[] {username});
        List<lists_todo> mylists = new ArrayList<>();
        if (cursor!=null)
            cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            lists_todo tempItem = new lists_todo();
            tempItem.name = cursor.getString(0);
            tempItem.id = cursor.getInt(1);
            tempItem.ftime = cursor.getInt(2);
            mylists.add(tempItem);
            cursor.moveToNext();
        }
        DP_Database.close();
        return mylists;
    }

    public void insertList(String username, String listName){
        DP_Database = getWritableDatabase();
        ContentValues Cont=new ContentValues();
        Cont.put("name",listName);
        Cont.put("user_username",username);
        Cont.put("ftime", 1);
        DP_Database.insert("TodoList",null,Cont);
        DP_Database.close();
    }
    public void updateLists(String name,String listname, String username){
        ContentValues Cont=new ContentValues();
        Cont.put("name",listname);
        DP_Database = getWritableDatabase();
        DP_Database.update("TodoList",Cont,"name=? and user_username=?",new String[] {String.valueOf(name), username});
        DP_Database.close();
    }

    public void updatefTime(String listid, String username,int val){
        DP_Database = getWritableDatabase();
        ContentValues Cont=new ContentValues();
        Cont.put("ftime",val);
        DP_Database.update("TodoList",Cont,"TodoList_ID=? and user_username=?",new String[] {String.valueOf(listid),username});
        DP_Database.close();

//        DP_Database = getWritableDatabase();
//        DP_Database.update("TodoList",Cont,"TodoList_ID=? and user_username=?",new String[] {String.valueOf(listid), username});
//        DP_Database.close();
    }
    public void deleteList(String name, String username){
        DP_Database=getWritableDatabase();
        DP_Database.delete("TodoList","name=? and user_username=?",new String[]{String.valueOf(name), username});
        DP_Database.close();
    }
}
