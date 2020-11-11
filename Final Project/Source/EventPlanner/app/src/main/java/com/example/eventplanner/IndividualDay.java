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

import com.example.eventplanner.Utils.LetterImageView;


/****************************************EDIT THIS FOR API INSERTION. THIS IS ONLY A LAYOUT*******

 *************************************************************************************************/
public class IndividualDay extends AppCompatActivity {

    private ListView lists;
    private Toolbar toolbar;
    //made public in case other activity needs access. Arrays that each hold daily activities
    public static String[] Mon, time_Mon;
    public static String[] Tues, time_Tues;
    public static String[] Wed, time_Wed;
    public static String[] Thurs, time_Thurs;
    public static String[] Fri, time_Fri;
    public static String[] Sat, time_Sat;
    public static String[] Sun, time_Sun;

    //used in conditional statements
    private String[] day_selected;
    private String[] time_selected;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //allows viewCreator to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_day);

        //order matters
        viewCreator();
        create_toolbar();
        listCreator();
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
        //day assignment
        Mon = getResources().getStringArray(R.array.Monday);
        Tues = getResources().getStringArray(R.array.Tuesday);
        Wed = getResources().getStringArray(R.array.Wednesday);
        Thurs = getResources().getStringArray(R.array.Thursday);
        Fri = getResources().getStringArray(R.array.Friday);
        Sat = getResources().getStringArray(R.array.Saturday);
        Sun = getResources().getStringArray(R.array.Sunday);

        //time assignment
        time_Mon = getResources().getStringArray(R.array.t_mon);
        time_Tues = getResources().getStringArray(R.array.t_tues);
        time_Wed = getResources().getStringArray(R.array.t_wed);
        time_Thurs= getResources().getStringArray(R.array.t_thur);
        time_Fri = getResources().getStringArray(R.array.t_fri);
        time_Sat = getResources().getStringArray(R.array.t_sat);
        time_Sun = getResources().getStringArray(R.array.t_sun);

        //retrieves selected day from WeeklyView. Set to null if info cannot be found
        String selected_day = WeeklyView.sharedInfo.getString(WeeklyView.sel_day, null);

        //format copied from MainActivity. Use switch case if you want. Will set info based off selected day
        if (selected_day.equalsIgnoreCase("Monday")) {
            day_selected = Mon;
            time_selected = time_Mon;
        } else if (selected_day.equalsIgnoreCase("Tuesday")) {
            day_selected = Tues;
            time_selected = time_Tues;
        } else if (selected_day.equalsIgnoreCase("Wednesday")) {
            day_selected = Wed;
            time_selected = time_Wed;
        } else if (selected_day.equalsIgnoreCase("Thursday")) {
            day_selected = Thurs;
            time_selected = time_Thurs;
        } else if (selected_day.equalsIgnoreCase("Friday")) {
            day_selected = Fri;
            time_selected = time_Fri;
        } else if (selected_day.equalsIgnoreCase("Saturday")) {
            day_selected = Sat;
            time_selected = time_Sat;
        } else {
            day_selected = Sun;
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
        private String[] classA, timeA; //used to save info
        private LetterImageView letter; //images used here

        /**
         * Constructor is created here. Used to help pupulate listview. Will be called on start up
         * @param c
         * @param subjects
         * @param times
         */
        public Adapter(Context c, String[] subjects, String[] times) {
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
            return classA.length;//returns array length
        }

        /**
         * This section will return the class' position
         * @param position
         * @return classA[postion]
         */
        @Override
        public Object getItem(int position) {
            return classA[position];
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
            subject.setText(classA[position]);
            time.setText(timeA[position]);

            letter.setOval(true);
            letter.setLetter(classA[position].charAt(0));

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