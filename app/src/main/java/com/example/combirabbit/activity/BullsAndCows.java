package com.example.combirabbit.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.combirabbit.R;
import com.example.combirabbit.adapters.GuessColorAdapter;
import com.example.combirabbit.models.ColorGuessItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BullsAndCows extends ActivityMethods{
    private LinearLayout layerGuessedColors;
    private static final int MAX_GUESS_PLACE = 3;
    private int nGuessPlace = 0;
    private int nGuessNumber = 0;
    private final int [] strColors = {R.drawable.black_stain_color,
                                         R.drawable.pink_stain_color,
                                         R.drawable.blue_stain_color,
                                         R.drawable.yellow_stain_color,
                                         R.drawable.red_stain_color};
    private int [] strRandomColorsToGuess;
    private ColorGuessItem nUserGuess;
    private ArrayList<ColorGuessItem> strUserColorsGuess;
    private RecyclerView mColorsGuessTable;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();
        String maxAge = "7";
//        Intent prevIntent = getIntent();
//        maxAge = prevIntent.getStringExtra("maxAge");
//        Log.d("max age is: ", maxAge);

        // Layout will be declared by the user age
        // bulls and cows with colors
        if(maxAge.equals("7")) {
            Log.d("INFO : ", "here");
            // Main view - bulls and cows in colors
            setContentView(R.layout.bulls_cows_color_game);

            // the colors the game generate to guess randomly
            Random r = new Random();
            strRandomColorsToGuess = new int[]{strColors[r.nextInt(strColors.length)],
                    strColors[r.nextInt(strColors.length)], strColors[r.nextInt(strColors.length)]};

            Log.d("Log: ", ""+ Arrays.toString(strRandomColorsToGuess));
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
        Chronometer timerView = findViewById(R.id.timer);
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
    }

    // Send the guess of the user and write it down
    // in the guessing table
    public void sendGuess(View view) {

        // Clear the guess
        clearGuess(view);

        // Add new guess to table
        strUserColorsGuess.add(nGuessNumber,nUserGuess);
        nUserGuess = new ColorGuessItem();

        // Write the guess into the table of guessing
        mAdapter.notifyItemInserted(nGuessNumber);

        // increase number of guessing
        nGuessNumber++;
    }

    // Build the guessing table
    public void buildRecyclerView()
    {
        mColorsGuessTable = findViewById(R.id.guess_list);
        mColorsGuessTable.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GuessColorAdapter(strUserColorsGuess);
        mColorsGuessTable.setLayoutManager(mLayoutManager);
        mColorsGuessTable.setAdapter(mAdapter);
    }
}
