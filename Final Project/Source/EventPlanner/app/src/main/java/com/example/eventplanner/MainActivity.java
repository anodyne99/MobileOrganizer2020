package com.example.eventplanner;

import androidx.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;//makes toolbar function correctly

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar; //used to access toolbar created in activity_main
    private ListView lists; //used to access list from activity_main

    /**Program start up
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //allows viewCreator to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This section will first create the cards,toolbar, then list will be created upon start up
        //order matters
        viewCreator();
        create_toolbar();
        listCreator();
    }

    /**
     * Function will create the toolbar for the user to see
     */
    private void create_toolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event Planner");//this will set the title of our application

    }

    /**
     * Function will act as template to create the cards for user interface
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//allows toolbar to work
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        lists = (ListView) findViewById(R.id.list);

    }

    /**
     * Function will create the list to be viewed
     */
    private void listCreator() {
        String[] title = getResources().getStringArray(R.array.Home);
        String[] description = getResources().getStringArray(R.array.Description);

        Adapter adapter = new Adapter(this, title, description);
        lists.setAdapter(adapter);

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //switch case implemented to know which item was clicked
                switch (position) {
                    case 0: {//if first section is selected, open new activity -->WeeklyView
                        Intent intent = new Intent(MainActivity.this, WeeklyView.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {//if second option is selected, open new activity displaying monthly calendar
                        break;
                    }
                    case 2: {//if third option is selected, it will open to umkc bookstore in new browser
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.umkcbookstore.com/"));
                        startActivity(browserIntent);
                        break;
                    }
                    case 3: {//if fourth option is selected it will open umkc email in new browser
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.umkc.edu/exchange/"));
                        startActivity(browserIntent);
                        break;
                    }
                    //WORK IN PROGRESS. PLACEHOLDER USED FOR NOW
                    case 4: {//if fifth option is selected, open new actiivty showing educational resources
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pitt.libguides.com/openeducation/biglist"));
                        startActivity(browserIntent);
                        break;
                    }
                    case 5: {//if sixth option chosen, open new activity showing youtube playlist. youtube api used here
                        break;
                    }
                    //WORK IN PROGRESS BUTTON NON FUNCTIONAL FOR NOW
                    case 6: {//if seventh option is chosen, open new activity, grid view of options for 2 games, facebook, hulu, netflix, youtube.
                        break;
                    }
                    default: break;
                }
            }
        });
    }

    /**
     * Adapter used for list view. Layout of the listview
     */
    public class Adapter extends BaseAdapter {

        private Context myContext; //access to application-specific resources
        private LayoutInflater inflater; //define the row layout. Loads different layouts into views
        private TextView title, description; //used for each card creation
        private String[] titleA, descriptA; //used to save info
        private ImageView images; //images used here

        /**
         * Constructor is created here. Used to help pupulate listview. Will be called on start up
         * @param c
         * @param title
         * @param descript
         */
        public Adapter(Context c, String[] title, String[] descript) {
            myContext = c;
            titleA = title;
            descriptA = descript;
            inflater = LayoutInflater.from(c);

        }

        /**
         * This section will return title's length
         * @return titleA.length
         */
        @Override
        public int getCount() {
            return titleA.length;//returns array length
        }

        /**
         * This section will return the title's postion
         * @param position
         * @return titleA[postion]
         */
        @Override
        public Object getItem(int position) {
            return titleA[position];
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
            //if the view is empty, access the card layout in activity_main_2
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.acitivity_main_2, null);
            }
            //set the title to be equal to the title in the activity_main_2
            title = (TextView) convertView.findViewById(R.id.cardTitle);
            //set the description to be equal to the description in activity_main_2
            description = (TextView) convertView.findViewById(R.id.cardDescription);
            //set the image equal to in activity_main_2
            images = (ImageView) convertView.findViewById(R.id.mainImage);

            //set title and description
            title.setText(titleA[position]);
            description.setText(descriptA[position]);

            //This section will set the image for each title created in strings.xml
            if (titleA[position].equalsIgnoreCase("Weekly View")) {
                images.setImageResource(R.drawable.weekly);
            } else if (titleA[position].equalsIgnoreCase("Calendar View")) {
                images.setImageResource(R.drawable.calendar);
            } else if (titleA[position].equalsIgnoreCase("University Bookstore")) {
                images.setImageResource(R.drawable.bookstore);
            } else if (titleA[position].equalsIgnoreCase("University Email")) {
                images.setImageResource(R.drawable.email);
            } else if (titleA[position].equalsIgnoreCase("Educational Resources")) {
                images.setImageResource(R.drawable.resources);
            } else if (titleA[position].equalsIgnoreCase("Mindfulness")) {
                images.setImageResource(R.drawable.mindfulness);
            } else {
                images.setImageResource(R.drawable.entertainment);
            }

            return convertView;
        }
    }
}