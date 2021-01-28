package com.example.combirabbit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.combirabbit.R;

public class BullsAndCows extends ActivityMethods{
    private String maxAge;
    private Chronometer timerView;
    private ImageButton btnStartGuessing;
    private ImageButton btnSendGuess;
    private ImageButton imgBlackColor;
    private ImageButton imgRedColor;
    private ImageButton imgYellowColor;
    private ImageButton imgBlueColor;
    private ImageButton imgPinkColor;
    private ImageView imgChosenColor;
    private LinearLayout linear;
    private LinearLayout layerGuessedColors;
    private static final int MAX_NUM_GUESSES = 3;
    private int nNumGuess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        Intent prevIntent = getIntent();
        maxAge = prevIntent.getStringExtra("maxAge");
        Log.d("max age is: ", maxAge);

        // Layout will be declared by the user age
        // bulls and cows with colors
        if(maxAge.equals("7")) {
            Log.d("INFO : ", "here");
            // Main view - bulls and cows in colors
            setContentView(R.layout.bulls_cows_color_game);




        }
        // bulls and cows with numbers
        else{
            Log.d("INFO : ", "second here");
            // Main view - bulls and cows with numbers
            setContentView(R.layout.bulls_cows_num_game);



        }
    }

    // Class functions
    // Function that will manage the game and it's components
    public void startPlay(View view) {

        // Find elements of the buttons of the game
        btnStartGuessing = findViewById(R.id.btn_start_guess);
        btnSendGuess = findViewById(R.id.btn_send_guess);

        // Replace buttons - start/send
        btnStartGuessing.setVisibility(View.INVISIBLE);
        btnSendGuess.setVisibility(View.VISIBLE);

        // Start timer
        startTimer();

        //layerGuessedColors = new LinearLayout[NUM_COLORS];
        layerGuessedColors = findViewById(R.id.colors_to_guess);
        imgRedColor = findViewById(R.id.red_color);
        imgBlackColor = findViewById(R.id.black_color);
        imgBlueColor = findViewById(R.id.blue_color);
        imgYellowColor = findViewById(R.id.yellow_color);
        imgPinkColor = findViewById(R.id.pink_color);

        // Init all the colors buttons on the screen
        // so when clicked, will appear on the guess list
        imgBlackColor.setOnClickListener(this::onClick);
        imgRedColor.setOnClickListener(this::onClick);
        imgBlueColor.setOnClickListener(this::onClick);
        imgPinkColor.setOnClickListener(this::onClick);
        imgYellowColor.setOnClickListener(this::onClick);
    }


    // Function that start the timer of the game
    public void startTimer() {
        timerView = findViewById(R.id.timer);
        timerView.setBase(SystemClock.elapsedRealtime());
        timerView.start();
    }

    public void onClick(View v) {

        // Do only if the user did not finished to guess yet
        if(nNumGuess < MAX_NUM_GUESSES) {
            imgChosenColor = new ImageView(this);
            linear = (LinearLayout) layerGuessedColors.getChildAt(nNumGuess);
            switch (v.getId()) {
                case R.id.black_color:

                    imgChosenColor.setBackgroundResource(R.drawable.black_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im black!");
                    nNumGuess++;
                    break;
                case R.id.red_color:

                    imgChosenColor.setBackgroundResource(R.drawable.red_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im red!");
                    nNumGuess++;
                    break;

                case R.id.blue_color:

                    imgChosenColor.setBackgroundResource(R.drawable.blue_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im blue!");
                    nNumGuess++;
                    break;

                case R.id.yellow_color:

                    imgChosenColor.setBackgroundResource(R.drawable.yellow_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im yellow!");
                    nNumGuess++;
                    break;

                case R.id.pink_color:

                    imgChosenColor.setBackgroundResource(R.drawable.pink_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im pink!");
                    nNumGuess++;
                    break;
            }
        }
        else{

            Log.d("Error: ", "Can't continue choose a color, press on send guess.");
        }
    }

}
