package com.example.combirabbit.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.combirabbit.R;
import com.example.combirabbit.pages.PhonePage;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityMethods extends AppCompatActivity {

    // Class variables
    protected MediaPlayer mediaPlayer;
    protected ImageView rabbitIcon;

    protected void fullScreen() {

        /* No title bar */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        /* No navigation bar */
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    protected void configRecord(int rabbitRecord) {

        mediaPlayer = MediaPlayer.create(this, rabbitRecord);
        mediaPlayer.start();
    }

    protected Object configAnimation(int combiIcon, int duration) {

        AnimationDrawable rabbitAnimation;
        rabbitIcon = findViewById(combiIcon);
        rabbitIcon.setBackgroundResource(R.drawable.combi_animation);
        rabbitAnimation = (AnimationDrawable) rabbitIcon.getBackground();
        rabbitAnimation.start();

        // When pressing the rabbit, we can hear the record again
        rabbitIcon.setOnClickListener(v -> {
            mediaPlayer.start();
            rabbitAnimation.start();
            stopAnimation(rabbitAnimation, duration);
        });

        return rabbitAnimation;
    }

    protected void stopAnimation(AnimationDrawable rabbitAnimation, int duration) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rabbitAnimation.stop();
            }
        },duration*1000);
    }

    protected void stopRecordAndAnimation(AnimationDrawable rabbitAnimation,
                                          int animationDuration) {
        this.stopAnimation(rabbitAnimation, animationDuration);
        this.mediaPlayer.stop();
    }

    protected void transferToPage(ImageButton buttonContinue, Object nextPage) {

        buttonContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), (Class<?>) nextPage);
            startActivity(intent);
        });
    }
}
