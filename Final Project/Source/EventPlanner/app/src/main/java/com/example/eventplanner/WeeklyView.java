package com.example.eventplanner;

//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.eventplanner.Utils.LetterImageView;


public class WeeklyView extends AppCompatActivity {
    private Toolbar toolbar; //variable that saves toolbar info
    private ListView lists; //variable that saves list info
    public static SharedPreferences sharedInfo; //want to share this between activities. made public
    public static String sel_day; //used to store day user selected

    /**Program start up
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);

        //order matters
        viewCreator();
        create_toolbar();
        listCreator();
    }

    /**Retrieved from Override Methods.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {//when back button is pressed, takes you back to main menu
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function will create the toolbar for the user to see
     */
    private void create_toolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weekly Schedule");//this will set the title of our application
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//take you to previous activity. Back button essentially

    }

    /**
     * Function will act as template to create the cards for user interface
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//allows toolbar to work
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_weekly);
        lists = (ListView) findViewById(R.id.weekly_list);

        //shares info across app, however, this info will only be accessible to this sectoin
        sharedInfo = getSharedPreferences("DAY", MODE_PRIVATE);
    }

    /**
     * Function will create the list to be viewed
     */
    private void listCreator() {
        String[] weekdays = getResources().getStringArray(R.array.Weekdays);

        //adapter being created for list view then set
        WeeklyAdapter adapter = new WeeklyAdapter(this, R.layout.activity_weekly_view_2, weekdays);
        lists.setAdapter(adapter);

        /*adding an item click lister. This will allow individual items to be clicked
        instead of entire list view.*/
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*switch case implemented to know which item was clicked. Whenever each day is clicked
                access local file stored and add the given day. All information gets loaded for the given
                day. Here is where canvas API info would be useful to display
                 */
                switch (position) {
                    case 0 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Monday").apply();
                        break;
                    }
                    case 1 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Tuesday").apply();
                        break;
                    }
                    case 2 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Wednesday").apply();
                        break;
                    }
                    case 3 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Thursday").apply();
                        break;
                    }
                    case 4 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Friday").apply();
                        break;
                    }
                    case 5 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Saturday").apply();
                        break;
                    }
                    case 6 : {
                        startActivity( new Intent(WeeklyView.this, IndividualDay.class)  );
                        sharedInfo.edit().putString(sel_day, "Sunday").apply();
                        break;
                    }
                    default: break;

                }
            }
        });
    }


    public class WeeklyAdapter extends ArrayAdapter{
        private int resources; //used to save activity file
        private LayoutInflater inflater; //define the row layout. Loads different layouts into views
        private String[] weekdays = new String[]{}; //array that holds weekdays


        /**Constructor created for adapter. Used to populate list.
         *
         * @param context
         * @param resource
         * @param days
         */
        public WeeklyAdapter(Context context, int resource, String[] days) {
            super(context, resource, days);
            this.resources= resource;
            this.weekdays = days;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**Generated from Override Methods.
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            holder t_holder;
            /*if the view is empty, create a new holder object and retrieve letter/weekday info.
            otherwise set the holder object to retrieve the view's tag
             */
            if (convertView == null){
                t_holder = new holder(); //creates a new holder object
                convertView = inflater.inflate(resources, null);
                t_holder.letter = (LetterImageView) convertView.findViewById(R.id.letters);
                t_holder.weekView = (TextView) convertView.findViewById(R.id.week_day);
                convertView.setTag(t_holder);
            }else{
                t_holder = (holder) convertView.getTag();
            }
            //provides circular shape around the letters created in LetterImageView
            t_holder.letter.setOval(true);
            //retrieves the first letter of the weekdays array. Will place letter in circle
            t_holder.letter.setLetter(weekdays[position].charAt(0));
            //sets the element of the weekday array to weekly_activity_view_2 card
            t_holder.weekView.setText(weekdays[position]);
            return convertView;
        }

        /**Defines information presented on activity_weekly_view
         *
         */
        class holder{
            private LetterImageView letter;
            private TextView weekView;
        }
    }

}