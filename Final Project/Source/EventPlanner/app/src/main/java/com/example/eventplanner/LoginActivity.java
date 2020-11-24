package com.example.eventplanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn,registerBtn,lunch,update,save;
    EditText name,email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn=(Button)findViewById(R.id.loginBtn);
        lunch=(Button)findViewById(R.id.launch);
        update=(Button)findViewById(R.id.update);
        save=(Button)findViewById(R.id.save);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

    }


    public void redirectToHomeActivity (View v) {
        Intent redirect = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(redirect);

    }

}
