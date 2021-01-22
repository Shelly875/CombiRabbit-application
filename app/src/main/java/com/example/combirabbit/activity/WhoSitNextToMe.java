package com.example.combirabbit.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.combirabbit.R;

public class WhoSitNextToMe extends ActivityMethods{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.who_next_to_me_game);

        Intent prevIntent = getIntent();

    }

}
