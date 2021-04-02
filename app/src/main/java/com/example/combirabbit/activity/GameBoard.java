package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.FragmentTransaction;

import com.example.combirabbit.R;
import com.example.combirabbit.fragments.BullAndCowsColorFragment;
import com.example.combirabbit.fragments.BullAndCowsNumFragment;
import com.example.combirabbit.fragments.MatchAndCompleteFragment;
import com.example.combirabbit.fragments.WhoSitNextToMeFragment;
import com.example.combirabbit.models.GameOperations;
import com.example.combirabbit.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class GameBoard extends ActivityMethods {

    // Class variables

    private GameOperations gameInstance;
    private TextView scoreOne;
    private ImageButton btnPlayGameOne;
    private ImageButton btnPlayGameTwo;
    private Dialog trailerPopUp;
    private TextView scoreTwo;
    private String maxAge = "";
    private Object objChosenGame;
    TextView playerName;
    VideoView video;
    MediaController mc;

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
        this.gameInstance = (GameOperations) prevIntent.getSerializableExtra("gameInstance");
        GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());

        // Start playing recording - enter your name
        // Config recording - welcome
        mediaPlayer = MediaPlayer.create(this, R.raw.game_board_record);

        // Load game or start a new game (that was already saved)
        // when loading a game - take name, high score 1, high score 2 for view only
        // take age - for loading a specific game fragment
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        DocumentReference docRef = mDatabase
                .collection("SavedGames")
                .document(tempGameInstance.getUserInstance().getPhone());

        // check if the user already has a game saved in db
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {

                    // If the current game is a saved one and not a new one
                    // upload to the gameInstance the data
                    this.gameInstance = new GameOperations(new User(document.getString("name"),
                                                 document.getString("age"),
                                                 this.gameInstance.getUserInstance().getPhone()),
                                                 document.getString("highestScoreOne"),
                                                 document.getString("highestScoreTwo") );

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

                        // will be used to direct the user to the game
                        maxAge = "7";

                        // Game one
                        ft.replace(R.id.game_one, new BullAndCowsColorFragment());

                        // Game two
                        ft.replace(R.id.game_two, new WhoSitNextToMeFragment());
                    }
                    if(Objects.requireNonNull(document
                            .getString("age")).matches("[8-9]|10|11|12[.]")){

                        Log.d("INFO:", "age 8 and above");

                        // will be used to direct the user to the game
                        maxAge = "12+";

                        // Game one
                        ft.replace(R.id.game_one, new BullAndCowsNumFragment());

                        // Game two
                        ft.replace(R.id.game_two, new MatchAndCompleteFragment());
                    }

                    // operate fragments
                    ft.commit();

                    // When pressing play button - popup about the trailer
                    trailerPopUp = new Dialog(this);
                    btnPlayGameOne = findViewById(R.id.btn_play_game);
                    btnPlayGameTwo = findViewById(R.id.btn_play_game_two);

                    // For game one - show popup window and navigate to trailer or game
                    btnPlayGameOne.setOnClickListener(v -> {

                        // Bulls and cows - colors or numbers
                        objChosenGame = new BullsAndCows();

                        // input for function: game number 1
                        ShowPopUp(objChosenGame, maxAge);
                    });

                    // For game two - show popup window and navigate to trailer or game
                    btnPlayGameTwo.setOnClickListener(v -> {
                        // give the popup the chosen game class
                        if (maxAge.equals("7")) {
                            objChosenGame = new WhoSitNextToMe();
                        } else {
                            objChosenGame = new MatchAndComplete();
                        }
                        // input for function: game number 2
                        ShowPopUp(objChosenGame, maxAge);
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
        int animationDuration = 17;
        AnimationDrawable rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon, animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }

    // popup that will redirect to the wanted game
    protected void ShowPopUp(Object objChosenGame, String maxAge){

        // Show the pop up for - instructions/start game
        trailerPopUp.setContentView(R.layout.trailer_popup);
        trailerPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // can't exit the popup without pressing the exit X button
        trailerPopUp.setCancelable(false);

        // pressing on the close popup feature
        trailerPopUp.findViewById(R.id.close_pop_up).setOnClickListener(v -> {
            trailerPopUp.dismiss();
        });

        // Take the buttons and direct the intents
        ImageButton btnStartTrailer = trailerPopUp.findViewById(R.id.btn_guide_button);
        ImageButton btnStartGame = trailerPopUp.findViewById(R.id.btn_start_game_button);

        // Each press will lead to other intent - start game
        btnStartGame.setOnClickListener(v -> {

            // we sending the max age to see which intent of bulls and cows
            // we will operate
            startActivity(new Intent(GameBoard.this,
                    objChosenGame.getClass()).putExtra("maxAge", maxAge)
                                             .putExtra("gameInstance", this.gameInstance));
        });
        // Each press will lead to other intent - start trailer
        btnStartTrailer.setOnClickListener(v -> {
            try
            {
             startActivity(new Intent(GameBoard.this, Trailer.class)
               .putExtra("gameName", objChosenGame.toString())
               .putExtra("maxAge", maxAge)
               .putExtra("gameInstance", this.gameInstance));

            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Error in btnInfo Click -->"+e.toString());
            }
        });
        //BtnPlay


//        btnStartTrailer.setOnClickListener(v ->
//                startActivity(new Intent(GameBoard.this, Trailer.class)
//                .putExtra("gameInstance", this.gameInstance)));

        trailerPopUp.show();
    }
}
