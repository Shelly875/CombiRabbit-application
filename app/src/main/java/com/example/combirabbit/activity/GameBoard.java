package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.combirabbit.R;
import com.example.combirabbit.fragments.BullAndCowsColorFragment;
import com.example.combirabbit.fragments.BullAndCowsNumFragment;
import com.example.combirabbit.fragments.MatchAndComplete;
import com.example.combirabbit.fragments.WhoSitNextToMeFragment;
import com.example.combirabbit.models.GameOperations;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class GameBoard extends ActivityMethods {

    // Class variables

    private FirebaseAuth mAuth;
    private GameOperations gameInstance;
    private FirebaseUser currentUser;
    private TextView scoreOne;
    private ImageButton btnPlayGameOne;
    private ImageButton btnPlayGameTwo;
    private Dialog trailerPopUp;
    private TextView scoreTwo;
    private AnimationDrawable rabbitAnimation;
    TextView playerName;
    private int animationDuration = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.game_board);

        // Make screen landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get previous intent parameters sent - from loading a game,
        // or starting a new game
        Intent prevIntent = getIntent();
        this.gameInstance = (GameOperations) prevIntent
                .getSerializableExtra("gameInstance");

        // Start playing recording - enter your name
        // Config recording - welcome
        mediaPlayer = MediaPlayer.create(this, R.raw.game_board_record);

        // Load game or start a new game (that was already saved)
        // when loading a game - take name, high score 1, high score 2 for view only
        // take age - for loading a specific game fragment

        DocumentReference docRef = this.gameInstance.getFireBaseInstance()
                .collection("SavedGames")
                .document(this.gameInstance.getUserInstance().getPhone());

        // check if the user already has a game saved in db
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    // Set game score from db
                    scoreOne = (TextView)findViewById(R.id.score_game_one);
                    scoreTwo = (TextView)findViewById(R.id.score_game_two);

                    scoreOne.setText(document.getString("highestScoreOne"));
                    scoreTwo.setText(document.getString("highestScoreTwo"));

                    // Show player name in the screen
                    playerName = (TextView)findViewById(R.id.player_name);
                    playerName.setText(" היי " + " " + document.getString("name"));

                    // Declare the fragment for the games
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    // Load game according to the person's age
                    if(Objects.requireNonNull(document
                            .getString("age")).matches("[6-7]")){

                        Log.d("INFO:", "age 6 to 7");

                        // Game one
                        ft.replace(R.id.game_one, new BullAndCowsColorFragment());

                        // Game two
                        ft.replace(R.id.game_two, new WhoSitNextToMeFragment());
                    }
                    if(Objects.requireNonNull(document
                            .getString("age")).matches("[8-9]|10|11|12[.]")){

                        Log.d("INFO:", "age 8 and above");

                        // Game one
                        ft.replace(R.id.game_one, new BullAndCowsNumFragment());

                        // Game two
                        ft.replace(R.id.game_two, new MatchAndComplete());
                    }

                    // operate fragments
                    ft.commit();

                    // When pressing play button - popup about the trailer
                    trailerPopUp = new Dialog(this);
                    btnPlayGameOne = findViewById(R.id.btn_play_game);
                    btnPlayGameTwo = findViewById(R.id.btn_play_game_two);

                    // For game one - show popup window and navigate to trailer or game
                    btnPlayGameOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // input for function: game number 1
                            ShowPopUp();
                        }
                    });

                    // For game two - show popup window and navigate to trailer or game
                    btnPlayGameTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // input for function: game number 2
                            ShowPopUp();
                        }
                    });


                    // load from db all the relevant fields
                    // fragments game images from relevant age
                    // show name of the user
                    // show score, if there is no score show : - -

                    Log.d("INFO: ", "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d("INFO: ", "No such document");
                }
            } else {
                Log.d("INFO: ", "get failed with ", task.getException());
            }
        });

    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon, animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }

    protected void ShowPopUp(){
        trailerPopUp.setContentView(R.layout.trailer_pop_up);
        trailerPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        trailerPopUp.show();
    }
}
