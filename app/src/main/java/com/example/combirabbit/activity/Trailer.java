package com.example.combirabbit.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.combirabbit.R;
import com.example.combirabbit.models.GameOperations;

public class Trailer extends ActivityMethods {

    private GameOperations gameInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.trailer);

        // Make screen landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get previous intent parameters sent - from loading a game,
        // or starting a new game
        Intent prevIntent = getIntent();
        this.gameInstance = (GameOperations) prevIntent.getSerializableExtra("gameInstance");
        String gameName = prevIntent.getStringExtra("gameName");
        String maxAge = prevIntent.getStringExtra("maxAge");

        // Get the video id
        VideoView videoView = findViewById(R.id.video_instructions);

        // get Bulls and cows in colors video instructions
        if(gameName.contains("BullsAndCows") && maxAge.equals("7")) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/"
                    + R.raw.webm_bulls_cows_colors);
        }

        // get Bulls and cows in numbers video instructions
        if(gameName.contains("BullsAndCows") && maxAge.equals("12+")) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/"
                    + R.raw.webm_bull_cow_numbers);
        }

        // get Who sit next to me in numbers video instructions
        if(gameName.contains("WhoSitNextToMe")) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/"
                    + R.raw.webm_bulls_cows_colors);
        }

        // get Match and complete to me in numbers video instructions
        if(gameName.contains("MatchAndComplete")) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/"
                    + R.raw.webm_match_complete);
        }

        videoView.setMediaController(new MediaController(Trailer.this));
        videoView.start();
    }
}
