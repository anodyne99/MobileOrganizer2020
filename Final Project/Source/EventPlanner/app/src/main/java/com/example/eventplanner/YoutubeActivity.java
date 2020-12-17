package com.example.eventplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

public class YoutubeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        // TEST Toast.makeText(YoutubeActivity.this, "Redirect works", Toast.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.recyclerView);

        //making model and adapter files.
        //model file used to set and get data
        adapter = new Adapter(YoutubeActivity.this, list);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getApplicationContext());
        //setting the layout to recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetchData();


    }

    /**retrieves data from the youtube API
     *
     */
    private void fetchData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UChSpME3QaSFAWK8Hpmg-Dyw&maxResults=30&key=AIzaSyDZteUKqvy0W7C5vLUqDkKIcDrTpQ1RYHs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                //access each part of the api to retrieve specific data
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                                JSONObject jsonSnippet = jsonObject1.getJSONObject("snippet");
                                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                                //json objects accessed, setting to model class
                                Model md = new Model();

                                //because of the formatting of the youtube api, the first 8 videos must be ignored. all subsequent videos are fine
                                if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7 && i != 8) {
                                    md.setVideoID(jsonVideoId.getString("videoId"));
                                    md.setTitle(jsonSnippet.getString("title"));
                                    md.setUrl(jsonThumbnail.getString("url"));

                                    //adding accessed youtube api to array
                                    list.add(md);

                                }
                                //while the list isnt empty notify the change in the adapter class
                                if (list.size() > 0) {
                                    // TEST Toast.makeText(YoutubeActivity.this, "LIST Accessed", Toast.LENGTH_LONG).show();

                                    adapter.notifyDataSetChanged();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(YoutubeActivity.this, "Error accessing json info", Toast.LENGTH_LONG).show();
            }
        });
        //adds video once all information has been retrieved
        requestQueue.add(stringRequest);

    }

}
