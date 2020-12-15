package com.example.eventplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends AppCompatActivity {

    TextView textView;
    Button loginBtn, registerBtn, lunch, show, save;
    EditText name, age, gender, location, email, password;
    User user;

    //FirebaseDatabase rootNode;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        lunch = (Button) findViewById(R.id.launch);
        show = (Button) findViewById(R.id.show);
        save = (Button) findViewById(R.id.save);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        age = (EditText) findViewById(R.id.age);
        gender = (EditText) findViewById(R.id.gender);
        location = (EditText) findViewById(R.id.location);
        textView= (TextView) findViewById(R.id.textView) ;


        user= new User();
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String na = name.getText().toString().trim();
                String ag = age.getText().toString().trim();
                String gen = gender.getText().toString().trim();
                String loc = location.getText().toString().trim();


                user.setName(name.getText().toString().trim());
                user.setAge(ag);
                user.setGender(gen);
                user.setLocation(loc);
                reff.push().setValue(user);
                Toast.makeText(HomeActivity.this,"Data inserted successfully",Toast.LENGTH_LONG).show();

                textView.setText("Name: " + na + " Age: " + ag  + " Gen: " + gen + " Loc: " + loc);
            }
        });
     }



        public void redirectToMainActivity (View v){ //name, age, gender, location
            /*if (name.getText().toString().equals("")) {
                //name.setError("Please choose a date");
                String test = user.getName();
                Toast.makeText(HomeActivity.this, "The name field is empty. Please enter your name", Toast.LENGTH_LONG).show();
            }else if (age.getText().toString().equals("")){
                Toast.makeText(HomeActivity.this, "The age field is empty. Please enter your age", Toast.LENGTH_LONG).show();


            }else if (gender.getText().toString().equals("")){
                Toast.makeText(HomeActivity.this, "The gender field is empty. Please enter your gender", Toast.LENGTH_LONG).show();


            }else if (location.getText().toString().equals("")){
                Toast.makeText(HomeActivity.this, "The location field is empty. Please enter your location", Toast.LENGTH_LONG).show();

            }
            else{*/
                //String test = user.getName();
                //Toast.makeText(HomeActivity.this, test, Toast.LENGTH_LONG).show();
                Intent redirect = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(redirect);
            //}


        }


        //Redirecting to LoginActivity
        public void redirectToLoginActivity (View v){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.getInstance().signOut();

            Intent redirect = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(redirect);


        }

    }
