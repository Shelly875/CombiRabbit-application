package com.example.combirabbit.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.example.combirabbit.R;

public class BullsAndCows extends ActivityMethods{
    private String maxAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        Intent prevIntent = getIntent();
        maxAge = prevIntent.getStringExtra("maxAge");
        Log.d("max age is: ", maxAge);

        // Layout will be declared by the user age
        if(maxAge.equals("7")) {
            Log.d("INFO : ", "here");
            // Main view - bulls and cows in colors
            setContentView(R.layout.bulls_cows_color_game);
        }
        else{
            Log.d("INFO : ", "second here");
            // Main view - bulls and cows with numbers
            setContentView(R.layout.bulls_cows_num_game);



        }



    }

    // Class functions

}
