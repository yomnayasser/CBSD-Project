package com.example.dayplanner;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

public class UserClass extends Activity
{
    private String username;
    private String name;
    private String password;
    private String email;
    private Context context;
    DayPlannerDatabase dp;

    public  UserClass(String username,String name, String password,String email,Context context)
    {
        this.username=username;
        this.name=name;
        this.password=password;
        this.email=email;
        this.context=context;
    }

    public  UserClass(String username, String password,Context context)
    {
        this.username=username;
        this.password=password;
        this.context=context;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String Signup()
    {
        dp = new DayPlannerDatabase(context);
        Cursor cursor = dp.fetchAllUser();
        String found = "false";
        while (!cursor.isAfterLast() && cursor!=null)
        {
            System.out.println("Enter");
            System.out.println(cursor.getString(0));
            System.out.println(username);
            if (!cursor.getString(0).equals(username))
            {
                System.out.println("here");
                cursor.moveToNext();
            }
            else
            {
                found = "true";
                System.out.println("there");
                break;
            }
        }


        if (found == "false")
        {
            System.out.println("in");
            dp.Signup(username, name, email, password);
            return  "true";

        }
        else
        {
            return "false";

        }
    }
    public String Login()
    {
        dp = new DayPlannerDatabase(context);
        String Username = dp.checkUsername(username);
        if(Username=="false")
        {
            return "Not found";
        }
        else
        {
            String LoginResult = dp.login(username,password);
            if(LoginResult=="false")
            {
                return "false";
            }
            else
            {
                return "true";
            }


        }
    }


}
