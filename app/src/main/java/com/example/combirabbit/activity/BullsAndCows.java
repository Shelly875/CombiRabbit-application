package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.combirabbit.R;
import com.example.combirabbit.adapters.GuessColorAdapter;
import com.example.combirabbit.models.ColorGuessItem;
import com.example.combirabbit.models.GameOperations;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BullsAndCows extends ActivityMethods{
    private LinearLayout layerGuessedColors;
    private static final int MAX_GUESS_PLACE = 3;
    private int nGuessPlace = 0;
    private int nGuessNumber = 0;
    private final int [] nGameColors = {R.drawable.black_stain_color,
                                         R.drawable.pink_stain_color,
                                         R.drawable.blue_stain_color,
                                         R.drawable.yellow_stain_color,
                                         R.drawable.red_stain_color};
    private int [] nRandomColorsToGuess;
    private ColorGuessItem nUserGuess;
    private ArrayList<ColorGuessItem> strUserColorsGuess;
    private RecyclerView.Adapter mAdapter;
//    private static final String SUCCESS_RESULT = "בבב";
    private final int SUCCESS_RESULT = 3;
    private GameOperations gameInstance;
    private Chronometer timerView;
    private final int BULL = 0;
    private boolean isButtonPressedClear = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get previous intent parameters sent - from loading a game,
        // or starting a new game
        Intent prevIntent = getIntent();
        this.gameInstance = (GameOperations) prevIntent
                .getSerializableExtra("gameInstance");
        String maxAge = prevIntent.getStringExtra("maxAge");

        // Set the activity to use full screen
        this.fullScreen();

        // Layout will be declared by the user age
        // bulls and cows with colors
        if(maxAge.equals("7")) {
            // Main view - bulls and cows in colors
            setContentView(R.layout.bulls_cows_color_game);

            // the colors the game generate to guess randomly
            Random r = new Random();
            nRandomColorsToGuess = new int[]{nGameColors[r.nextInt(nGameColors.length)],
                    nGameColors[r.nextInt(nGameColors.length)], nGameColors[r.nextInt(nGameColors.length)]};
            Log.d("Log: ", ""+ Arrays.toString(nRandomColorsToGuess));
        }
        // bulls and cows with numbers
        else{
            // Main view - bulls and cows with numbers
            setContentView(R.layout.bulls_cows_num_game);
        }
    }

    // Class functions
    // Function that will manage the game and it's components
    public void startPlay(View view) {
        strUserColorsGuess = new ArrayList<>();
        nUserGuess = new ColorGuessItem();
        // Find elements of the buttons of the game
        ImageButton btnStartGuessing = findViewById(R.id.btn_start_guess);
        ImageButton btnSendGuess = findViewById(R.id.btn_send_guess);

        // Replace buttons - start/send
        btnStartGuessing.setVisibility(View.INVISIBLE);
        btnSendGuess.setVisibility(View.VISIBLE);

        // Start timer
        startTimer();

        // Init elements in xml
        layerGuessedColors = findViewById(R.id.colors_to_guess);
        ImageButton imgRedColor = findViewById(R.id.red_color);
        ImageButton imgBlackColor = findViewById(R.id.black_color);
        ImageButton imgBlueColor = findViewById(R.id.blue_color);
        ImageButton imgYellowColor = findViewById(R.id.yellow_color);
        ImageButton imgPinkColor = findViewById(R.id.pink_color);


        // Init colors guessing
        buildRecyclerView();

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
        if(nGuessPlace < MAX_GUESS_PLACE) {
            ImageView imgChosenColor = new ImageView(this);
            LinearLayout linear = (LinearLayout) layerGuessedColors.getChildAt(nGuessPlace);
            switch (v.getId()) {
                case R.id.black_color:

                    imgChosenColor.setBackgroundResource(R.drawable.black_stain_color);
                    nUserGuess.addToList(R.drawable.black_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im black!");
                    nGuessPlace++;
                    break;
                case R.id.red_color:

                    imgChosenColor.setBackgroundResource(R.drawable.red_stain_color);
                    nUserGuess.addToList(R.drawable.red_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im red!");
                    nGuessPlace++;
                    break;

                case R.id.blue_color:

                    imgChosenColor.setBackgroundResource(R.drawable.blue_stain_color);
                    nUserGuess.addToList(R.drawable.blue_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im blue!");
                    nGuessPlace++;
                    break;

                case R.id.yellow_color:

                    imgChosenColor.setBackgroundResource(R.drawable.yellow_stain_color);
                    nUserGuess.addToList(R.drawable.yellow_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im yellow!");
                    nGuessPlace++;
                    break;

                case R.id.pink_color:

                    imgChosenColor.setBackgroundResource(R.drawable.pink_stain_color);
                    nUserGuess.addToList(R.drawable.pink_stain_color);
                    linear.addView(imgChosenColor);
                    Log.d("Log: ", "Im pink!");
                    nGuessPlace++;
                    break;
            }
        }
        else{
            Log.d("Error: ", "Can't continue choose a color, press on send guess.");
        }
    }

    // Clear the guess blocks
    public void clearGuess(View view) {
        // return numbers of guesses to zero
        nGuessPlace = 0;
        LinearLayout l1;

        // Loop over all the layout's views and remove the colors chosen
        for(int i=0;i<layerGuessedColors.getChildCount();i++) {
            l1 = (LinearLayout) layerGuessedColors.getChildAt(i);
            l1.removeAllViews();
            Log.d("Log:", "Deleted " + i);
        }

        // In case the button is pressed and the guess is not sent
        if(this.isButtonPressedClear) {
            this.nUserGuess = new ColorGuessItem();
        }
    }

    // Send the guess of the user and write it down
    // in the guessing table
    public void sendGuess(View view) {

        if(nGuessPlace < MAX_GUESS_PLACE)
        {
            Log.d("Log: ", "You can't sent guess that is partly filled.");
        }

        else {

            // Clear in case the guess sent and not cleared by a button
            this.isButtonPressedClear = false;

            // Clear the guess
            clearGuess(view);

            // Add new guess to table
            strUserColorsGuess.add(nGuessNumber, nUserGuess);
            nUserGuess = new ColorGuessItem();

            // Get the result of the guess
//            String nResult = strUserColorsGuess.get(nGuessNumber).checkGuess(strRandomColorsToGuess);

            int[] nResult = {0,0};
            nResult = this.strUserColorsGuess.get(nGuessNumber).checkGuess(nRandomColorsToGuess);

            if(nResult !=null) {

                // If the user guessed correctly, popup will appear
                if (nResult[this.BULL] == this.SUCCESS_RESULT) {
                    // stop the clock
                    timerView.stop();

                    // if there is a new record, show on the screen and save
                    // currentRecord > previousRecord, open firebase to check
                    ShowPopUp((String) timerView.getText());
                }
            }
            // Write the guess into the table of guessing
            mAdapter.notifyItemInserted(nGuessNumber);

            // increase number of guessing
            nGuessNumber++;

            // return the isbuttonclearpressed button to true
            this.isButtonPressedClear = true;
        }
    }

    // Build the guessing table
    public void buildRecyclerView()
    {
        RecyclerView mColorsGuessTable = findViewById(R.id.guess_list);
        mColorsGuessTable.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GuessColorAdapter(strUserColorsGuess);
        mColorsGuessTable.setLayoutManager(mLayoutManager);
        mColorsGuessTable.setAdapter(mAdapter);
    }

    protected void ShowPopUp(String newRecord)
    {
        int animationDuration = 8;
        ImageButton btnReturnToBoardGame;


        // Show the pop up for - instructions/start game
        // Start playing recording - enter your name
        this.configRecord(R.raw.guess_success);

        Dialog successPopUp = new Dialog(this);
        successPopUp.setContentView(R.layout.success_popup);
        successPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Start playing animation & record when pressing the rabbit icon
        AnimationDrawable rabbitAnimation;
        rabbitIcon = successPopUp.findViewById(R.id.combi_icon);
        rabbitIcon.setBackgroundResource(R.drawable.combi_animation);
        rabbitAnimation = (AnimationDrawable) rabbitIcon.getBackground();
        rabbitAnimation.start();

        // Update the score of the first game of the user
        // if the score is smaller than the previous one
        updateHighestScore(newRecord, successPopUp);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);

        successPopUp.show();

        // Return to the game board
        GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());
        btnReturnToBoardGame = successPopUp.findViewById(R.id.btn_return);
        btnReturnToBoardGame.setOnClickListener(v ->
                startActivity(new Intent(successPopUp.getContext(), GameBoard.class)
                .putExtra("gameInstance", tempGameInstance)));

    }

    protected void updateHighestScore(String newRecord,
                                      Dialog successPopUp)
    {
        GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());
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
                    TextView newRecordView;
                    TextView msgNewRecord;

                    if(this.gameInstance.isBetterScoreOne(newRecord))
                    {
                        msgNewRecord = successPopUp.findViewById(R.id.msg_new_record);
                        msgNewRecord.setVisibility(View.VISIBLE);
                        newRecordView = successPopUp.findViewById(R.id.new_record);
                        newRecordView.setVisibility(View.VISIBLE);
                        newRecordView.setText(newRecord);
                        this.gameInstance.setHighestScoreGameOne(newRecord);
                        this.gameInstance.saveGame();
                    }
                }
                else
                {
                    Log.d("INFO: ", "No such document");
                }
            }
            else
            {
                Log.d("INFO: ", "get failed with ", task.getException());
            }
        });
    }
}
