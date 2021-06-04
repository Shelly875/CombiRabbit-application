package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.combirabbit.R;
import com.example.combirabbit.adapters.GuessColorAdapter;
import com.example.combirabbit.adapters.GuessNumAdapter;
import com.example.combirabbit.models.ColorGuessItem;
import com.example.combirabbit.models.GameOperations;
import com.example.combirabbit.models.NumberGuessItem;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BullsAndCows extends ActivityMethods{
    private LinearLayout layerGuessedColors;
    private static final int MAX_GUESS_PLACE = 3;
    private static final int MAX_GUESS_NUM_LENGTH = 4;
    private int nGuessPlace = 0;
    private int nGuessNumber = 0;
    private final int [] nGameColors = {R.drawable.black_stain_color,
                                         R.drawable.pink_stain_color,
                                         R.drawable.blue_stain_color,
                                         R.drawable.yellow_stain_color,
                                         R.drawable.red_stain_color};
    private int [] nRandomColorsToGuess;
    private int [] nRandomNumbersToGuess;
    private ArrayList<NumberGuessItem> arrUserNumbersGuess;
    private ColorGuessItem nUserGuess;
    private ArrayList<ColorGuessItem> arrUserColorsGuess;
    private RecyclerView.Adapter mAdapter;
    private GameOperations gameInstance;
    private Chronometer timerView;
    private final int BULL = 0;
    private boolean isButtonPressedClear = true;
    private EditText txtUserGuess;
    private int gameNumber;
    int[] nResultNum = {0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get previous intent parameters sent - from loading a game,
        // or starting a new game
        Intent prevIntent = getIntent();
        this.gameInstance = (GameOperations) prevIntent
                .getSerializableExtra("gameInstance");

        String maxAge = prevIntent.getStringExtra("maxAge");
        this.gameNumber = prevIntent.getIntExtra("gameNumber", 0);

        // Set the activity to use full screen
        this.fullScreen();

        Random r = new Random();

        // Find the instructions button
        ImageButton btnInstruction = new ImageButton(this);

        // Layout will be declared by the user age
        // bulls and cows with colors
        if(maxAge.equals("7")) {
            // Main view - bulls and cows in colors
            setContentView(R.layout.bulls_cows_color_game);

            btnInstruction= findViewById(R.id.btn_bull_color_instructions);

            // Declare popup for the instruction button
            btnInstruction.setOnClickListener(v -> {
                ShowInstructionPopUp(R.drawable.bulls_colors_instructions_img);
            });

            // the colors the game generate to guess randomly
            nRandomColorsToGuess = new int[]{nGameColors[r.nextInt(nGameColors.length)],
                    nGameColors[r.nextInt(nGameColors.length)],
                    nGameColors[r.nextInt(nGameColors.length)]};
            Log.d("Log: ", "The random colors are: "
                    + Arrays.toString(nRandomColorsToGuess));
        }
        // bulls and cows with numbers
        else{
            // Main view - bulls and cows with numbers
            setContentView(R.layout.bulls_cows_num_game);

            btnInstruction= findViewById(R.id.btn_bull_num_instructions);

            // Declare popup for the instruction button
            btnInstruction.setOnClickListener(v -> {
                ShowInstructionPopUp(R.drawable.bulls_num_instructions_img);
            });

            // the numbers the game generate to guess randomly
            nRandomNumbersToGuess = new int[]{r.nextInt(9),
                    r.nextInt(9), r.nextInt(9), r.nextInt(9)};

            Log.d("Log: ", "The random numbers are: "
                    + Arrays.toString(nRandomNumbersToGuess));
        }


    }

    // Class functions
    // Function that will manage the game and it's components
    public void startColorsGame(View view) {
        arrUserColorsGuess = new ArrayList<>();
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

        // Init numbers guessing recycle view
        buildColorRecyclerView();

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
    public void clearColorGuess(View view) {
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendColorGuess(View view) {

        if(nGuessPlace < MAX_GUESS_PLACE)
        {
            Log.d("Log: ", "You can't sent guess that is partly filled.");
        }

        else {

            // Clear in case the guess sent and not cleared by a button
            this.isButtonPressedClear = false;

            // Clear the guess
            clearColorGuess(view);

            // Add new guess to table
            arrUserColorsGuess.add(nGuessNumber, nUserGuess);
            nUserGuess = new ColorGuessItem();

            // Get the result of the guess
            int[] nResult = {0,0};
            nResult = this.arrUserColorsGuess.get(nGuessNumber).checkGuess(nRandomColorsToGuess);

            if(nResult !=null) {

                // If the user guessed correctly, popup will appear
                int COLORS_SUCCESS_RESULT = 3;
                if (nResult[this.BULL] == COLORS_SUCCESS_RESULT) {
                    // stop the clock
                    timerView.stop();

                    // if there is a new record, show on the screen and save
                    // currentRecord > previousRecord, open firebase to check
                    this.ShowPopUp(this.gameInstance, (String) timerView.getText(), this.gameNumber);
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

    // Build the colors guessing table
    public void buildColorRecyclerView()
    {
        RecyclerView mColorsGuessTable = findViewById(R.id.guess_list);
        mColorsGuessTable.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GuessColorAdapter(arrUserColorsGuess);
        mColorsGuessTable.setLayoutManager(mLayoutManager);
        mColorsGuessTable.setAdapter(mAdapter);
    }

    // Build the numbers guessing table
    public void buildNumRecyclerView()
    {
        RecyclerView mNumbersGuessTable = findViewById(R.id.guess_list);
        //mNumbersGuessTable.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GuessNumAdapter(arrUserNumbersGuess);
        mNumbersGuessTable.setLayoutManager(mLayoutManager);
        mNumbersGuessTable.setAdapter(mAdapter);
    }

    public void startNumGame(View view) {

        // Make user number's input guess enabled
        // and find it in the xml elements
        this.txtUserGuess = findViewById(R.id.user_number);
        this.txtUserGuess.setEnabled(true);

        // Init the user and the game numbers guess
        this.arrUserNumbersGuess = new ArrayList<>();

        // Find elements of the buttons of the game
        ImageButton btnStartGuessing = findViewById(R.id.btn_start_guess);
        ImageButton btnSendGuess = findViewById(R.id.btn_send_guess);

        // Replace buttons - start/send
        btnStartGuessing.setVisibility(View.INVISIBLE);
        btnSendGuess.setVisibility(View.VISIBLE);

        // Start timer
        startTimer();

        // Init numbers guessing recycle view
        buildNumRecyclerView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNumGuess(View view) {

        // Check that the guess entered is in the proper length
        if(this.txtUserGuess.length() < MAX_GUESS_NUM_LENGTH)
        {
            Log.d("Log: ", "You can't sent guess that is partly filled.");
        }
        else
        {
            // Find the user guess element
            this.txtUserGuess = findViewById(R.id.user_number);

            // Insert the user's guess to the items array
            NumberGuessItem nUserGuessNum = new NumberGuessItem(this.txtUserGuess.getText());

            // Add new guess to table
            this.arrUserNumbersGuess.add(nUserGuessNum);

            Log.d("Log: ", "New table: " + Arrays.toString(this.arrUserNumbersGuess.toArray()));

            // Get the result of the guess
            this.nResultNum = this.arrUserNumbersGuess.get(this.nGuessNumber)
                    .checkGuess(this.nRandomNumbersToGuess);

            // If the user guessed correctly, popup will appear
            int NUMBERS_SUCCESS_RESULT = 4;
            if (this.nResultNum[this.BULL] == NUMBERS_SUCCESS_RESULT) {
                 // stop the clock
                 timerView.stop();
                 // if there is a new record, show on the screen and save
                 // currentRecord > previousRecord, open firebase to check
                 Log.d("log: ", "game number:" + this.gameNumber);
                 this.ShowPopUp(this.gameInstance, (String) timerView.getText(), this.gameNumber);
            }

            // Write the guess into the table of guessing
            mAdapter.notifyItemInserted(nGuessNumber);

            // increase number of guessing
            nGuessNumber++;

            // Clear the guess text
            clearNumGuess(view);
        }
    }

    public void clearNumGuess(View view) {
        this.txtUserGuess.getText().clear();
    }
}
