package com.example.combirabbit.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.combirabbit.R;
import com.example.combirabbit.models.BearsArrangement;
import com.example.combirabbit.models.GameOperations;
import java.util.ArrayList;

public class WhoSitNextToMe extends ActivityMethods{

    TextView txtGameLevel;
    private final int MAX_LEVEL = 5;
    private Chronometer timerView;
    private int nGameLevel = 1;
    private BearsArrangement bUserArrangement;
    private int bearClickPlace = 0;
    private LinearLayout layerGuessedBears;
    private ArrayList <Integer> userBearGuess;
    private LinearLayout linearRepoUserBear;
    private RelativeLayout relativeRulesTable;
    private GameOperations gameInstance;
    private int gameNumber;
    private ImageButton btnClearArrange;

    public WhoSitNextToMe() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.who_next_to_me_game);

        // Get previous intent parameters sent - from loading a game,
        // or starting a new game
        Intent prevIntent = getIntent();
        this.gameInstance = (GameOperations) prevIntent
                .getSerializableExtra("gameInstance");

        this.gameNumber = prevIntent.getIntExtra("gameNumber", 0);
        // Declare popup for the instruction button
        ImageButton btnInstruction= findViewById(R.id.btn_who_next_instructions);
        btnInstruction.setOnClickListener(v -> {
            ShowInstructionPopUp(R.drawable.who_next_to_instructions_img);
        });

        // Init the game level on the user view
        this.txtGameLevel = findViewById(R.id.current_game_level);
        this.txtGameLevel.setText(String.valueOf(this.nGameLevel));

        // declare global buttons - clear arrange
        this.btnClearArrange = findViewById(R.id.btn_clear_arrange);
        this.btnClearArrange.setEnabled(false);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        this.configAnimation(R.drawable.combi_with_sign_animation,
                R.raw.start_game_record, false);
    }


    public void startGame(View view) {

        // Buttons on the screen to initialize
        ImageButton btnStartGame = findViewById(R.id.btn_start_game);
        ImageButton btnSendArrange = findViewById(R.id.btn_send_arrange);
        ImageButton btnBearOne = findViewById(R.id.bear_one);
        ImageButton btnBearTwo = findViewById(R.id.bear_two);
        ImageButton btnBearThree = findViewById(R.id.bear_three);
        ImageButton btnBearFour = findViewById(R.id.bear_four);
        ImageButton btnBearFive = findViewById(R.id.bear_five);
        ImageButton btnBearSix = findViewById(R.id.bear_six);

        // Init user arrange repository and rules table
        this.linearRepoUserBear = findViewById(R.id.layout_user_registry);
        this.relativeRulesTable = findViewById(R.id.layout_rules_table);

        // Init the user guess
        this.userBearGuess = new ArrayList<>();

        // Init guess layer
        this.layerGuessedBears = findViewById(R.id.layout_user_arrangement);

        // Timer start the game
        // Start timer
        startTimer();

        // when pressing on the start button,
        // it disappear and the sendArrangement button enabled
        btnSendArrange.setEnabled(true);
        this.btnClearArrange.setEnabled(true);
        btnStartGame.setVisibility(View.INVISIBLE);

        // Init the game to be at level 1
        this.bUserArrangement = new BearsArrangement(nGameLevel);

        // place the game repository
        this.bUserArrangement.placeRepository(this.linearRepoUserBear);

        // place game rules
        this.bUserArrangement.placeGamesRules(this.relativeRulesTable);

        // disable bears buttons by level 1
        disable_bears_buttons();

        // Init all the colors buttons on the screen
        // so when clicked, will appear on the guess list
        btnBearOne.setOnClickListener(this::onClick);
        btnBearTwo.setOnClickListener(this::onClick);
        btnBearThree.setOnClickListener(this::onClick);
        btnBearFour.setOnClickListener(this::onClick);
        btnBearFive.setOnClickListener(this::onClick);
        btnBearSix.setOnClickListener(this::onClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendArrangement(View view) {

        ImageView bubbleSpeech = findViewById(R.id.bubble_speech);
        TextView speechText = findViewById(R.id.speech_text);

        // If the user did not arrange as the rules
        // push an alert with a failed attempt
        if(!this.bUserArrangement.check_arrangement(this.userBearGuess) ||
                this.userBearGuess == null)
        {
            // error message
            bubbleSpeech.setVisibility(View.VISIBLE);
            speechText.setText("נסה שנית");
            speechText.setVisibility(View.VISIBLE);
        }
        else {

            // check if we are in level 4 or 5 and enable if else statement
            if(this.nGameLevel == MAX_LEVEL - 2 ||
            this.nGameLevel == MAX_LEVEL -1)
            {
                findViewById(R.id.if_state).setVisibility(View.VISIBLE);
                findViewById(R.id.else_state).setVisibility(View.VISIBLE);
            }

            // If finished all 5 games
            if(this.nGameLevel == MAX_LEVEL)
            {
                // finished and pop up with harry!!
                Log.d("INFO: ", "game finished and you succeeded all!");

                timerView.stop();

                // if there is a new record, show on the screen and save
                // currentRecord > previousRecord, open firebase to check
                this.ShowPopUp(this.gameInstance, (String) timerView.getText(), this.gameNumber);

            }
            else {
                // success message and moving to the next level
                bubbleSpeech.setVisibility(View.VISIBLE);
                speechText.setVisibility(View.VISIBLE);
                speechText.setText("יפה מאוד!");

                // Increase the level after a success
                this.nGameLevel++;

                // Declare new level for the user => constructor

                // Init the game to be at the next level
                this.bUserArrangement = new BearsArrangement(nGameLevel);

                // place the game repository
                this.bUserArrangement.placeRepository(this.linearRepoUserBear);

                // place game rules
                this.bUserArrangement.placeGamesRules(this.relativeRulesTable);

                // update the game level in the xml (user view)
                txtGameLevel.setText(String.valueOf(this.nGameLevel));

                // Enabled the bears buttons back
                enable_bears_buttons();

                // clear the arrangement from the board
                clearArrangement(view);
            }
        }

        // clear the arrangement from the board
        clearArrangement(view);

        // Init again the user guess
        this.userBearGuess = new ArrayList<>();

        // disable bears buttons by level
        disable_bears_buttons();
    }

    // clear the user's arrangement board
    public void clearArrangement(View view) {

        this.bearClickPlace = 0;
        enable_bears_buttons();
        LinearLayout l1;
        for(int i=0; i<this.layerGuessedBears.getChildCount();i++)
        {
            l1 = (LinearLayout) this.layerGuessedBears.getChildAt(i);
            l1.removeAllViews();
        }
    }

    public void onClick(View v) {

        // Do only if the user did not finished to arrangement yet
        if(bearClickPlace < this.bUserArrangement.get_places()) {

            // For placing the bears in the guess board
            ImageView imgChosenColor = new ImageView(this);
            LinearLayout linear = (LinearLayout) this.layerGuessedBears.getChildAt(bearClickPlace);

            // Clicking methods for the user choice - rules:
            // 1. each image button can be chosen one time only
            // 2. after each click, number of possible bearclick increase by one
            // 3. TODO: no option to click on images that not in the current level
            switch (v.getId()) {
                case R.id.bear_one:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[0]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[0]);
                    Log.d("Log: ", "I'm bear one");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;
                case R.id.bear_two:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[1]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[1]);
                    Log.d("Log: ", "I'm bear two");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;

                case R.id.bear_three:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[2]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[2]);
                    Log.d("Log: ", "I'm bear three");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;

                case R.id.bear_four:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[3]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[3]);
                    Log.d("Log: ", "I'm bear four");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;

                case R.id.bear_five:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[4]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[4]);
                    Log.d("Log: ", "I'm bear five");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;

                case R.id.bear_six:
                    imgChosenColor.setBackgroundResource(this.bUserArrangement.getGame_bears()[5]);
                    linear.addView(imgChosenColor);
                    this.userBearGuess.add(this.bUserArrangement.getGame_bears()[5]);
                    Log.d("Log: ", "I'm bear six");
                    v.setEnabled(false);
                    bearClickPlace++;
                    break;
            }
        }
        else
        {
            Log.d("Log: ", "Can't place any more bear, press send.");
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

    // Enable buttons after each level increase
    public void enable_bears_buttons() {

        // Buttons on the screen to initialize
        ImageButton btnBearOne = findViewById(R.id.bear_one);
        ImageButton btnBearTwo = findViewById(R.id.bear_two);
        ImageButton btnBearThree = findViewById(R.id.bear_three);
        ImageButton btnBearFour = findViewById(R.id.bear_four);
        ImageButton btnBearFive = findViewById(R.id.bear_five);
        ImageButton btnBearSix = findViewById(R.id.bear_six);

        btnBearOne.setEnabled(true);
        btnBearTwo.setEnabled(true);
        btnBearThree.setEnabled(true);
        btnBearFour.setEnabled(true);
        btnBearFive.setEnabled(true);
        btnBearSix.setEnabled(true);
    }

    // Disable bears buttons in case we didn't arrive to their level
    public void disable_bears_buttons() {

        // Buttons on the screen to initialize
        ImageButton btnBearFour = findViewById(R.id.bear_four);
        ImageButton btnBearFive = findViewById(R.id.bear_five);
        ImageButton btnBearSix = findViewById(R.id.bear_six);

        // get level places on the board
        if (this.bUserArrangement.get_places() == 3) {

            btnBearFour.setEnabled(false);
            btnBearFive.setEnabled(false);
            btnBearSix.setEnabled(false);
        }

        if(this.bUserArrangement.get_places() == 4){
            btnBearFive.setEnabled(false);
            btnBearSix.setEnabled(false);
        }

        if(this.bUserArrangement.get_places() == 5)
        {
            btnBearSix.setEnabled(false);
        }
    }
}
