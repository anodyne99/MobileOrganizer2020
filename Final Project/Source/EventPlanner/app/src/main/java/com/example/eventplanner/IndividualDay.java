package com.example.eventplanner;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.content.Context;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.net.Uri.Builder;

import android.provider.CalendarContract;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/****************************************EDIT THIS FOR API INSERTION. THIS IS ONLY A LAYOUT*******

 *************************************************************************************************/
public class IndividualDay extends AppCompatActivity {

    private ListView lists;
    private Toolbar toolbar;


    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();


    //used in conditional statements
    private ArrayList<String> day_selected;
    private ArrayList<String> time_selected;

    //used for calendar call and handling
    private int dayOfWeekChosen, todayDate, dateChosen;
    private Calendar today = Calendar.getInstance();
    private int dayToday;
    private int monthToday;
    private int yearToday;

    private static final int CALENDAR_PERMISSION_CODE = 1;
    

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //allows viewCreator to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_day);

        //order matters
        viewCreator();
        create_toolbar();
        listCreator();
        permissionChecker();
    }

    public void dateSetter(){
        todayDate = today.get(Calendar.DAY_OF_MONTH);
        dayToday = today.get(Calendar.DAY_OF_WEEK);
        monthToday = today.get(Calendar.MONTH);
        yearToday = today.get(Calendar.YEAR);
        if (dayToday > dayOfWeekChosen){
            dateChosen =  todayDate - dayOfWeekChosen;
        }
        else {
            int dayDiff = dayOfWeekChosen - dayToday;
            dateChosen = todayDate + dayDiff;
        }
        calendarReader(yearToday, monthToday, dateChosen);
    }

    public void permissionChecker(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
        == PackageManager.PERMISSION_GRANTED){
            dateSetter();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, CALENDAR_PERMISSION_CODE);
        }
    }

    @NonNull
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] perm, @NonNull int[] grantResult){
        if (reqCode == CALENDAR_PERMISSION_CODE){
            if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                dateSetter();
            }
        }
        else {
            Toast.makeText(this, "This function requires calendar access", Toast.LENGTH_LONG);
            Intent redirect = new Intent(IndividualDay.this, MainActivity.class);
            startActivity(redirect);
        }
    }

    public void calendarReader(int year, int month, int day) {
        Calendar chosenDay = Calendar.getInstance();
        chosenDay.set(year, month, day);
        long startMillis = chosenDay.getTimeInMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
        startActivity(intent);
    }
    /**
     * Function will act as template to create the cards for user interface
     */
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_daily);
        lists = (ListView) findViewById(R.id.daily_list);
    }

    /**
     * Function will create the toolbar for the user to see
     */
    private void create_toolbar() {
        setSupportActionBar(toolbar);

        //access each day via shared preference. Retrieved from WeeklyView. Title should change based off date
        getSupportActionBar().setTitle(WeeklyView.sharedInfo.getString(WeeklyView.sel_day, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//take you to previous activity. Back button essentially
    }

    private void listCreator(){
        //retrieves selected day from WeeklyView. Set to null if info cannot be found
        String selected_day = WeeklyView.sharedInfo.getString(WeeklyView.sel_day, null);

        //format copied from MainActivity. Use switch case if you want. Will set info based off selected day
        if (selected_day.equalsIgnoreCase("Monday")) {
            // day_selected = Mon;
            dayOfWeekChosen = 2;
            // time_selected = time_Mon;
        } else if (selected_day.equalsIgnoreCase("Tuesday")) {
            // day_selected = Tues;
            dayOfWeekChosen = 3;
            // time_selected = time_Tues;
        } else if (selected_day.equalsIgnoreCase("Wednesday")) {
            //day_selected = Wed;
            dayOfWeekChosen = 4;
            //time_selected = time_Wed;
        } else if (selected_day.equalsIgnoreCase("Thursday")) {
            //day_selected = Thurs;
            dayOfWeekChosen = 5;
            // time_selected = time_Thurs;
        } else if (selected_day.equalsIgnoreCase("Friday")) {
            //day_selected = Fri;
            dayOfWeekChosen = 6;
            //time_selected = time_Fri;
        } else if (selected_day.equalsIgnoreCase("Saturday")) {
            //day_selected = Sat;
            dayOfWeekChosen = 7;
            //time_selected = time_Sat;
        } else {
            //day_selected = Sun;
            dayOfWeekChosen = 1;
            //time_selected = time_Sun;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    // This is to see if this push will update properly now

}
