package com.example.eventplanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/****************************************EDIT THIS FOR API INSERTION. THIS IS ONLY A LAYOUT*******
 *************************************************************************************************/
public class IndividualDay extends AppCompatActivity {

    private Toolbar toolbar;

    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();

    //used for calendar call and handling
    private int dayOfWeekChosen;
    private Calendar today = GregorianCalendar.getInstance();

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
        readCalendarEvent(getTimeOfEvent());
    }

    public void permissionChecker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, CALENDAR_PERMISSION_CODE);
        }
    }

    public void readCalendarEvent(long[] arr) {
        if (arr == null || arr.length == 0) return;
        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + arr[0] + " ) AND ( " + CalendarContract.Events.DTEND + " <= " + arr[1] + " ))";
        Cursor cursor = getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, selection,
                        null, null);
        cursor.moveToFirst();

        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];
        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();

        if (CNames.length == 0) {
            return;
        }
        for (int i = 0; i < CNames.length; i++) {
            nameOfEvent.add(cursor.getString(1) + " --- " + getDate(cursor.getLong(4)));
            startDates.add(getDate(arr[0]));
            endDates.add(getDate(arr[1]));

            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameOfEvent);
        ListView listView = (ListView) findViewById(R.id.daily_list);
        listView.setAdapter(itemsAdapter);
    }

    public long[] getTimeOfEvent() {
       int todayDate = today.get(Calendar.DAY_OF_MONTH);
       int dayToday = today.get(Calendar.DAY_OF_WEEK);
       int month = today.get(Calendar.MONTH);
       int year = today.get(Calendar.YEAR);

        int dateChosen;
        if (dayToday > dayOfWeekChosen){
            dateChosen =  todayDate - dayOfWeekChosen;
        }
        else {
            int dayDiff = dayOfWeekChosen - dayToday;
            dateChosen = todayDate + dayDiff;
        }

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, dateChosen, 0, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, dateChosen, 23, 59);

        return new long[]{beginTime.getTimeInMillis(), endTime.getTimeInMillis()};
    }

    private static String getDate(long parseLong) {
        Date date = new Date(parseLong);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateText = df2.format(date);
        System.out.println(dateText);
        return dateText;
    }

    /**
     * Function will act as template to create the cards for user interface
     */
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_daily);
        ListView listView = (ListView) findViewById(R.id.daily_list);
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

    private void listCreator() {
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
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    // This is to see if this push will update properly now

}
