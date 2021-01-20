package com.example.combirabbit.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import com.example.combirabbit.R;
import com.example.combirabbit.pages.NamePage;
import com.example.combirabbit.pages.PhonePage;

public class MainActivity extends ActivityMethods {

    // Class variables
    private AnimationDrawable rabbitAnimation;
    private final int animationDuration = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.activity_main);

        // Config recording - welcome
        mediaPlayer = MediaPlayer.create(this, R.raw.start_game_record);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon,animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }

    public void NamePage(View view) {

        this.stopRecordAndAnimation(rabbitAnimation, animationDuration);
        startActivity(new Intent(this, NamePage.class)
                .putExtra("isNewGame", true));
    }

    public void PhonePage(View view) {

        this.stopRecordAndAnimation(rabbitAnimation, animationDuration);
        startActivity(new Intent(this, PhonePage.class)
                .putExtra("isNewGame", false));
    }
}