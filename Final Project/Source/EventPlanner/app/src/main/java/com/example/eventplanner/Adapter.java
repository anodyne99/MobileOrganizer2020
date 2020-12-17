package com.example.eventplanner;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//adapter created for youtube

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    Context context;
    ArrayList<Model> list;

    public Adapter(Context context, ArrayList<Model> model){
        this.context = context;
        this.list = model;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //creates the layout for the youtube recycler view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Model model = list.get(position);
        holder.textView.setText(model.getTitle());//sets the text of the title based of the json objects
        Picasso.get().load(model.getUrl()).into(holder.imageView);//loads the image from the thumbnail of the json objects
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayVideo.class);
                intent.putExtra("videoId", model.getVideoID());//retrieves the video id to help display
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {//required method. returns the number of videos in a list
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{ //helps create the recyclerview layout

        public ImageView imageView;
        public TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.youtubeImageView);
            textView = itemView.findViewById(R.id.youtubeTitle);

        }
    }


}
