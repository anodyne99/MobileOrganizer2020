package com.example.eventplanner;

import android.os.Build;
import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;


/****************************************EDIT THIS FOR API INSERTION. THIS IS ONLY A LAYOUT*******

 *************************************************************************************************/
public class IndividualDay extends AppCompatActivity {

    private ListView lists;
    private Toolbar toolbar;
    //made public in case other activity needs access. Arrays that each hold daily activities
    public ArrayList<String> Mon = new ArrayList<String>();
    public ArrayList<String> time_Mon = new ArrayList<String>();
    public ArrayList<String> Tues = new ArrayList<String>();
    public ArrayList<String> time_Tues = new ArrayList<String>();
    public ArrayList<String> Wed = new ArrayList<String>();
    public ArrayList<String> time_Wed = new ArrayList<String>();
    public ArrayList<String> Thurs = new ArrayList<String>();
    public ArrayList<String> time_Thurs = new ArrayList<String>();
    public ArrayList<String> Fri = new ArrayList<String>();
    public ArrayList<String> time_Fri = new ArrayList<String>();
    public ArrayList<String> Sat = new ArrayList<String>();
    public ArrayList<String> time_Sat = new ArrayList<String>();
    public ArrayList<String> Sun = new ArrayList<String>();
    public ArrayList<String> time_Sun = new ArrayList<String>();


    //used in conditional statements
    private ArrayList<String> day_selected;
    private ArrayList<String> time_selected;

    //used for calendar call and handling
    private int dayOfWeekChosen, todayDate, dateChosen;
    private Calendar today = Calendar.getInstance();
    private int dayToday;
    private int monthToday;
    private int yearToday;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //allows viewCreator to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_day);

        //order matters
        viewCreator();
        create_toolbar();
        listCreator();
        dateSetter();
    }

    public void dateSetter(){
        todayDate = today.get(Calendar.DAY_OF_MONTH);
        dayToday = today.get(Calendar.DAY_OF_WEEK);
        monthToday = today.get(Calendar.MONTH) + 1;
        yearToday = today.get(Calendar.YEAR);
        if (dayToday > dayOfWeekChosen){
            dateChosen =  todayDate - dayOfWeekChosen;
        }
        else {
            int dayDiff = dayOfWeekChosen - dayToday;
            dateChosen = todayDate + dayDiff;
        }
        calendarReader(dateChosen, monthToday, yearToday);
    }


    public void calendarReader(int day, int month, int year){

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
            day_selected = Mon;
            dayOfWeekChosen = 2;
            time_selected = time_Mon;
        } else if (selected_day.equalsIgnoreCase("Tuesday")) {
            day_selected = Tues;
            dayOfWeekChosen = 3;
            time_selected = time_Tues;
        } else if (selected_day.equalsIgnoreCase("Wednesday")) {
            day_selected = Wed;
            dayOfWeekChosen = 4;
            time_selected = time_Wed;
        } else if (selected_day.equalsIgnoreCase("Thursday")) {
            day_selected = Thurs;
            dayOfWeekChosen = 5;
            time_selected = time_Thurs;
        } else if (selected_day.equalsIgnoreCase("Friday")) {
            day_selected = Fri;
            dayOfWeekChosen = 6;
            time_selected = time_Fri;
        } else if (selected_day.equalsIgnoreCase("Saturday")) {
            day_selected = Sat;
            dayOfWeekChosen = 7;
            time_selected = time_Sat;
        } else {
            day_selected = Sun;
            dayOfWeekChosen = 1;
            time_selected = time_Sun;
        }

        Adapter adapter = new Adapter(this, day_selected, time_selected);
        lists.setAdapter(adapter);
    }


    /**
     * Adapter used for list view. Layout of the listview
     */
    public class Adapter extends BaseAdapter {

        private Context myContext; //access to application-specific resources
        private LayoutInflater inflater; //define the row layout. Loads different layouts into views
        private TextView subject, time; //used for each card creation
        private ArrayList<String> classA, timeA; //used to save info
        private LetterImageView letter; //images used here

        /**
         * Constructor is created here. Used to help populate listview. Will be called on start up
         * @param c
         * @param subjects
         * @param times
         */
        public Adapter(Context c, ArrayList<String> subjects, ArrayList<String> times) {
            myContext = c;
            classA = subjects;
            timeA = times;
            inflater = LayoutInflater.from(c);
        }

        /**
         * This section will return class array's length
         * @return classA.length
         */
        @Override
        public int getCount() {
            return classA.size();//returns array length
        }

        /**
         * This section will return the class' position
         * @param position
         * @return classA[postion]
         */
        @Override
        public Object getItem(int position) {
            return classA.get(position);
        }

        /**
         * This section will return the position
         * @param position
         * @return position
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * This section will set each different view. Helps swap between the 2 main activities
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if the view is empty, access the card layout in activity_individual_day_2
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_individual_day_2, null);
            }
            //set the class name to be equal to the title in the activity_individual_day_2
            subject= (TextView) convertView.findViewById(R.id._subject);
            //set the time to be equal to the description in activity_individual_day_2
            time = (TextView) convertView.findViewById(R.id._time);
            //set the letter equal to in activity_individual_day_2
            letter = (LetterImageView) convertView.findViewById(R.id.letters_day);

            //set text info here
            subject.setText(classA.get(position));
            time.setText(timeA.get(position));

            letter.setOval(true);
            letter.setLetter(classA.get(position).charAt(0));

            /*This section will set the professor for each subject created in strings.xml NOT SURE IF I WANT THIS
            //WILL MAKE MORE EDITS WHEN INFO IS ADDED
            if (classA[position].equalsIgnoreCase("Monday Placeholder Class")) {
                images.setImageResource(R.drawable.weekly);
            } else if (classA[position].equalsIgnoreCase("Tuesday Placeholder Class")) {
                images.setImageResource(R.drawable.calendar);
            } else if (classA[position].equalsIgnoreCase("Wednesday Placeholder Class")) {
                images.setImageResource(R.drawable.bookstore);
            } else if (classA[position].equalsIgnoreCase("Thursday Placeholder Class")) {
                images.setImageResource(R.drawable.email);
            } else if (classA[position].equalsIgnoreCase("Friday Placeholder Class")) {
                images.setImageResource(R.drawable.resources);
            } else if (classA[position].equalsIgnoreCase("Saturday Plans")) {
                images.setImageResource(R.drawable.mindfulness);
            } else {
                images.setImageResource(R.drawable.entertainment);
            }*/

            return convertView;
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

}