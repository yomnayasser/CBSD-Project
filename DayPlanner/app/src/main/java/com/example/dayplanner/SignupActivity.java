package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText name = (EditText)findViewById(R.id.editTextName);
        EditText username = (EditText)findViewById(R.id.editTextUsername);
        EditText email = (EditText)findViewById(R.id.editTextEmail);
        EditText password = (EditText)findViewById(R.id.editTextPassword);
        Button submit = (Button)findViewById(R.id.Submitbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(Name.length()==0|| Username.length()==0 || Email.length()==0 || Password.length()==0 )
                {
                    Toast.makeText(SignupActivity.this, "Please enter the missing data!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    UserClass newUSer = new UserClass(Username,Name,Password,Email,SignupActivity.this);
                    String result = newUSer.Signup();
                    if(result.equals("true"))
                    {
                        Toast.makeText(SignupActivity.this, "Sign up successfully",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SignupActivity.this,HomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Username already taken,Please choose another one",Toast.LENGTH_LONG).show();
                    }


                }



            }
        });
    }
}