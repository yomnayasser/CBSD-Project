//package com.example.dayplanner;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
////DayPlannerDatabase db;
////ArrayAdapter<String> dpAdapter;
////ListView list;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button signIn_btn = (Button)findViewById(R.id.SignInbutton);
//        Button signUp_btn = (Button)findViewById(R.id.SignUpbutton);
//
//        EditText username = (EditText)findViewById(R.id.editTextUsername);
//        EditText password = (EditText)findViewById(R.id.editTextPassword);
//
//        ImageView logoPic = (ImageView)findViewById(R.id.imageView);
//        logoPic.setBackgroundResource(R.drawable.logo2);
//
//        signIn_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Username = username.getText().toString();
//                String Password = password.getText().toString();
//                UserClass user = new UserClass(Username,Password,MainActivity.this);
//
//                String Result = user.Login();
//                Toast.makeText(MainActivity.this,Result,Toast.LENGTH_LONG).show();
//                Intent i = new Intent(MainActivity.this,HomeActivity.class);
//                i.putExtra("username",user.getUsername());
//                startActivity(i);
//            }
//        });
//
//       signUp_btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent i = new Intent(MainActivity.this,SignupActivity.class);
//               startActivity(i);
//           }
//       });
//
//
//
////        Button remindersBtn = (Button)findViewById(R.id.remindersBtn);
////        Button todoBtn = (Button)findViewById(R.id.todoBtn);
////
////        remindersBtn.setOnClickListener(v -> {
////            Intent i = new Intent(MainActivity.this, RemindersActivity.class);
////            startActivity(i);
////        });
////
////        todoBtn.setOnClickListener(v -> {
////            Intent i = new Intent(MainActivity.this, ToDoActivity.class);
////            startActivity(i);
////        });
//    }
//}
package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //DayPlannerDatabase db;
//ArrayAdapter<String> dpAdapter;
//ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn_btn = (Button)findViewById(R.id.SignInbutton);
        Button signUp_btn = (Button)findViewById(R.id.SignUpbutton);

        EditText username = (EditText)findViewById(R.id.editTextUsername);
        EditText password = (EditText)findViewById(R.id.editTextPassword);

        ImageView logoPic = (ImageView)findViewById(R.id.imageView);
        logoPic.setBackgroundResource(R.drawable.logo2);

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = username.getText().toString();
                String Password = password.getText().toString();
                UserClass user = new UserClass(Username,Password,MainActivity.this);

                String Result = user.Login();
                if(Result=="false")
                {
                    Toast.makeText(MainActivity.this,"Wrong Username or Password",Toast.LENGTH_LONG).show();
                }
                else if(Result=="true")
                {
                    Toast.makeText(MainActivity.this," Login Successfully",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    i.putExtra("username",user.getUsername());
                    startActivity(i);
                }
                else if(Result=="Not found")
                {
                    Toast.makeText(MainActivity.this,"Username does not exist,Please Signup",Toast.LENGTH_LONG).show();
                }
                

            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });




//        Button remindersBtn = (Button)findViewById(R.id.remindersBtn);
//        Button todoBtn = (Button)findViewById(R.id.todoBtn);
//
//        remindersBtn.setOnClickListener(v -> {
//            Intent i = new Intent(MainActivity.this, RemindersActivity.class);
//            startActivity(i);
//        });
//
//        todoBtn.setOnClickListener(v -> {
//            Intent i = new Intent(MainActivity.this, ToDoActivity.class);
//            startActivity(i);
//        });
    }
}