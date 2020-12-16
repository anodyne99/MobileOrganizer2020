package com.example.eventplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class WarCardGame extends AppCompatActivity {

    ImageView iv_left_card, iv_right_card;
    TextView tv_left_score, tv_right_score, tv_war;
    Button deal_button;

    Random r_generator;

    int leftScore = 0, rightScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war_card_game);

    }

    public void start_dealing(View view){
        //setting variables here
        iv_left_card = (ImageView) findViewById(R.id.iv_player1);
        iv_right_card = (ImageView) findViewById(R.id.iv_player2);
        tv_left_score = (TextView) findViewById(R.id.player1);
        tv_right_score = (TextView) findViewById(R.id.player2);
        tv_war = (TextView) findViewById(R.id.tv_war);
        deal_button = (Button) findViewById(R.id.deal);
        r_generator = new Random(); //creates random num generator

        //creates the numbers that apepar randomly on card from 2 to 14
        int leftCard = r_generator.nextInt(13)+2;
        int rightCard = r_generator.nextInt(13)+2;


        //this will set the card images on click
        int leftImage = getResources().getIdentifier("hearts" + leftCard,"drawable",getPackageName());
        iv_left_card.setImageResource(leftImage);
        int rightImage = getResources().getIdentifier("hearts" + rightCard,"drawable",getPackageName());
        iv_right_card.setImageResource(rightImage);

        //makes text invisible until war condition is satisfied
        tv_war.setVisibility(View.INVISIBLE);

        //will compare cards then add points
        if (leftCard > rightCard){
            leftScore++;
            tv_left_score.setText("Player 1: " + String.valueOf(leftScore));

        }else if (rightCard > leftCard){
            rightScore++;
            tv_right_score.setText("Player 2: "+ String.valueOf(rightScore));

        }else {
            //makes teh war text appear if War condition is met (matching cards)
            tv_war.setVisibility(View.VISIBLE);

        }

    }
}