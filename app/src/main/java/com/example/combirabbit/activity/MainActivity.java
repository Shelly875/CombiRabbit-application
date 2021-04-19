package com.example.combirabbit.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import com.example.combirabbit.R;
import com.example.combirabbit.pages.NamePage;
import com.example.combirabbit.pages.PhonePage;

public class MainActivity extends ActivityMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        // Class variables
        this.configAnimation(R.drawable.combi_animation, R.raw.start_game_record, true);
    }

    public void NamePage(View view) {

        this.mediaPlayer.stop();
        startActivity(new Intent(this, NamePage.class)
                .putExtra("isNewGame", true));
    }

    public void PhonePage(View view) {

        this.mediaPlayer.stop();
        startActivity(new Intent(this, PhonePage.class)
                .putExtra("isNewGame", false));
    }
}