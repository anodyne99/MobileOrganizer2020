package com.example.eventplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void redirectToMainActivity (View v) {
        Intent redirect = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(redirect);

    }


    //Redirecting to LoginActivity
    public void redirectToLoginActivity (View v) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.getInstance().signOut();

        Intent redirect = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(redirect);


    }

}