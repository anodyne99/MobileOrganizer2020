package com.example.eventplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class EntertainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
    }

    public void launch_war(View view){
        Intent intent = new Intent(EntertainActivity.this, WarCardGame.class);
        startActivity(intent);
    }

    public void launch_reddit(View view){ //https://www.reddit.com/
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/"));
        startActivity(browserIntent);
        //Intent intent = new Intent(EntertainActivity.this, FbGame.class);
        //startActivity(intent);
    }
    public void launch_netflix(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/login"));
        startActivity(browserIntent);
    }
    public void launch_hulu(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hulu.com/welcome"));
        startActivity(browserIntent);
    }
    public void launch_cr(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.crunchyroll.com/welcome/login"));
        startActivity(browserIntent);

    }
    public void launch_pr(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.primevideo.com/login"));
        startActivity(browserIntent);
    }
}