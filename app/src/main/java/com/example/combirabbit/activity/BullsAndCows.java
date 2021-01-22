package com.example.combirabbit.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.combirabbit.R;

public class BullsAndCows extends ActivityMethods{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.bulls_cows_color_game);

        Intent prevIntent = getIntent();

    }


}
