package com.example.eventplanner;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Button;
import android.provider.CalendarContract;


import java.util.Calendar;


public class MonthlyActivity extends AppCompatActivity {
    CalendarView cal;
    int selectedDay = 0;
    int selectedMonth = 0;
    int selectedYear = 0;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_calendar);
        cal = (CalendarView) findViewById(R.id.calendarView);
        Button butInsert = (Button) findViewById(R.id.add_button);

        viewCreator();
        create_toolbar();

        // Checks and updates the selected date when the user changes the date
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                int m = month + 1;
                selectedDay = dayOfMonth;
                selectedYear = year;
                selectedMonth = month;
            }
        });

        // Adds a listener for when the '+' button is clicked
        butInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // if the user wanted to insert an event today and doesn't change the selected date first
                if (selectedDay == 0 || selectedMonth == 0 || selectedYear == 0){
                    Calendar today = Calendar.getInstance();
                    selectedDay = today.get(Calendar.DAY_OF_MONTH);
                    selectedMonth = today.get(Calendar.MONTH);
                    selectedYear = today.get(Calendar.YEAR);
                }
                insert(selectedDay, selectedMonth, selectedYear);
            }
        });
    }

    private void create_toolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Monthly View");//this will set the title of our application

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//allows toolbar to work
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_monthly);
    }


    public void insert(int day, int month, int year) {
        // Creates the new intent for inserting
        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        // Creates a base calendar from the current date
        Calendar startTime = Calendar.getInstance();
        // Assigns the selected time to the placeholder base calendar
        startTime.set(year, month, day);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        // Opens the calendar to the date specified and allows the user to add details
        startActivity(intent);
    }
}