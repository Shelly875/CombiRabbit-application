package com.example.combirabbit.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.combirabbit.R;

public class WhoSitNextToMe extends ActivityMethods{

    private AnimationDrawable rabbitAnimation;
    private int animationDuration = 11;
    private Chronometer timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.who_next_to_me_game);

        Intent prevIntent = getIntent();

//        this.configRecord(R.raw.enter_phone_record);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        rabbitAnimation = (AnimationDrawable) this.configAnimationHoldSign
                (R.id.combi_icon, animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }


    public void startGame(View view) {

        // Buttons on the screen to initialize
        ImageButton btnStartGame = findViewById(R.id.btn_start_game);
        ImageButton btnSendArrange = findViewById(R.id.btn_send_arrange);

        // Timer start the game
        // Start timer
        startTimer();

        // when pressing on the start button,
        // it disappear and the sendArrangement button enabled
        btnSendArrange.setEnabled(true);
        btnStartGame.setVisibility(View.INVISIBLE);





    }

    public void sendArrangement(View view) {
    }

    public void clearArrangement(View view) {
    }

    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.bear_one:

                    Log.d("Log: ", "I'm bear one");
                    break;
                case R.id.bear_two:

                    Log.d("Log: ", "I'm bear two");
                    break;

                case R.id.bear_three:

                    Log.d("Log: ", "I'm bear three");
                    break;

                case R.id.bear_four:

                    Log.d("Log: ", "I'm bear four");
                    break;

                case R.id.bear_five:

                    Log.d("Log: ", "I'm bear five");
                    break;

                case R.id.bear_six:

                    Log.d("Log: ", "I'm bear six");
                    break;
            }
    }

    public void clicking(View view) {

    }

    // Function that start the timer of the game
    public void startTimer() {
        timerView = findViewById(R.id.timer);
        timerView.setBase(SystemClock.elapsedRealtime());
        timerView.start();
    }

}
