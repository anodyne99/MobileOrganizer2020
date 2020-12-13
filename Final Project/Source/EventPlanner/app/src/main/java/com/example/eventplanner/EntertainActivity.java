package com.example.eventplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;


public class EntertainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView grid;

    /** Entertainment start
     * @param savedInstanceState
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        viewCreator();
        create_toolbar();
        gridCreator();
    }

    private void create_toolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Entertainment");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void viewCreator() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_entertain);
        grid = (GridView) findViewById(R.id.simpleGrid);
    }

    private void gridCreator(){
        String[] names = getResources().getStringArray(R.array.Entertainment);

        Adapter adapter = new Adapter(this, names);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hulu.com/"));
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/"));
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/"));
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    public class Adapter extends BaseAdapter {

        private Context myContext;
        private LayoutInflater inflater;
        private TextView name;
        private String[] nameA;
        private ImageView image;


        public Adapter(Context c, String[] name){
            myContext = c;
            nameA = name;
            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {return nameA.length;}

        @Override
        public Object getItem(int position) { return nameA[position]; }

        @Override
        public long getItemId(int position) {return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_entertainment_2, null);
            }
            name = (TextView) convertView.findViewById(R.id.entertainTitle);
            image = (ImageView) convertView.findViewById(R.id.entertainImage);

            name.setText(nameA[position]);

            if (nameA[position].equalsIgnoreCase("Hulu")) {
                image.setImageResource(R.drawable.hulu);
            }
            else if (nameA[position].equalsIgnoreCase("Netflix")) {
                image.setImageResource(R.drawable.netflix);
            }
            else if (nameA[position].equalsIgnoreCase("Facebook")) {
                image.setImageResource(R.drawable.facebook);
            }
            else if (nameA[position].equalsIgnoreCase("Twitter")) {
                image.setImageResource(R.drawable.twitter);
            }
            else if (nameA[position].equalsIgnoreCase("Youtube")) {
                image.setImageResource(R.drawable.youtube);
            }
            return convertView;
        }
    }
}
