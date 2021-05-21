package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.combirabbit.R;
import com.example.combirabbit.models.GameOperations;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    protected void configRecord(int rabbitRecord, boolean isOnStart) {

        this.mediaPlayer = MediaPlayer.create(this, rabbitRecord);
        if(isOnStart){
            this.mediaPlayer.start();
        }
    }

    protected void configAnimation(int combImg, int record, boolean isOnStart) {

        AnimationDrawable rabbitAnimation;
        rabbitIcon = findViewById(R.id.combi_icon);
        rabbitIcon.setBackgroundResource(combImg);
        rabbitAnimation = (AnimationDrawable) rabbitIcon.getBackground();

        // check if we are running the record at start immidiatly
        if(isOnStart) {
            rabbitAnimation.start();
        }
        // config record
        this.configRecord(record, isOnStart);

        // When pressing the rabbit, we can hear the record again
        rabbitIcon.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying())
            {
                Log.d("msg :", "Im playing");
                this.mediaPlayer.stop();
                rabbitAnimation.stop();
                rabbitAnimation.selectDrawable(0);
            }
            else {
                Log.d("msg :", "Im not playing");
                this.mediaPlayer = MediaPlayer.create(this, record);
                rabbitAnimation.start();
                this.stopAnimation(rabbitAnimation, this.mediaPlayer.getDuration());
                this.mediaPlayer.start();
            }
        });
        this.stopAnimation(rabbitAnimation, this.mediaPlayer.getDuration());
    }

    protected Object configAnimation(int combImg, int record,
                                     Dialog successPopUp,
                                     boolean isOnStart) {

        AnimationDrawable rabbitAnimation;
        rabbitIcon = successPopUp.findViewById(R.id.combi_icon);
        rabbitIcon.setBackgroundResource(combImg);
        rabbitAnimation = (AnimationDrawable) rabbitIcon.getBackground();

        // check if we are running the record at start immidiatly
        if(isOnStart) {
            rabbitAnimation.start();
        }
        // config record
        this.configRecord(record, isOnStart);

        // When pressing the rabbit, we can hear the record again
        rabbitIcon.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying())
            {
                Log.d("msg :", "Im playing");
                this.mediaPlayer.stop();
                rabbitAnimation.stop();
                rabbitAnimation.selectDrawable(0);
            }
            else {
                Log.d("msg :", "Im not playing");
                this.mediaPlayer = MediaPlayer.create(this, record);
                rabbitAnimation.start();
                this.stopAnimation(rabbitAnimation, this.mediaPlayer.getDuration());
                this.mediaPlayer.start();
            }
        });
        this.stopAnimation(rabbitAnimation, this.mediaPlayer.getDuration());
        return rabbitAnimation;
    }


    protected void stopAnimation(AnimationDrawable rabbitAnimation, int duration) {
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("msg: ", "DURATION: " + duration);
                rabbitAnimation.stop();
            }
        }, duration);
    }

    public void ShowInstructionPopUp(int instructions){

        Dialog successPopUp = new Dialog(this);
        successPopUp.setContentView(R.layout.instructions_popup);
        successPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {

            // Find instructions on the popup
            ImageView imgInstructionsPlaceHolder = successPopUp.findViewById(R.id.game_instructions);
            imgInstructionsPlaceHolder.setBackgroundResource(instructions);

            // can't exit the popup without pressing the exit X button
            successPopUp.setCancelable(false);

            // pressing on the close popup feature
            successPopUp.findViewById(R.id.close_pop_up).setOnClickListener(v -> {
               successPopUp.dismiss();
            });

            // show popup to the user
            successPopUp.show();
        }
        catch (Exception e){
            Log.d("LOG: ","THE ERROR: " + e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void ShowPopUp(GameOperations gameInstance, String newRecord, int gameNumber)
    {
        ImageButton btnReturnToBoardGame;

        Dialog successPopUp = new Dialog(this);
        successPopUp.setContentView(R.layout.success_popup);
        successPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Show the pop up for - success in the game
        this.configAnimation(R.drawable.combi_animation,R.raw.guess_success,
                successPopUp, true);

        // Update the score of the current game (one or two) of the user
        // if the score is smaller than the previous one
        updateHighestScore(gameInstance, newRecord, successPopUp, gameNumber);

        successPopUp.setCancelable(false);
        successPopUp.show();

        // Return to the game board
        GameOperations tempGameInstance = new GameOperations(gameInstance.getUserInstance());
        btnReturnToBoardGame = successPopUp.findViewById(R.id.btn_return);
        btnReturnToBoardGame.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            mediaPlayer.stop();
            startActivity(new Intent(successPopUp.getContext(), GameBoard.class)
                    .putExtra("gameInstance", tempGameInstance));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void updateHighestScore(GameOperations gameInstance, String newRecord,
                                      Dialog successPopUp, int gameNumber)
    {
        GameOperations tempGameInstance = new GameOperations(gameInstance.getUserInstance());
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
                    Log.d("Log: ", "Game number 2: " + gameNumber);
                    if(gameNumber == 1) {

                        if (gameInstance.isBetterScoreOne(newRecord)) {
                            saveProgressControl(gameInstance, true, gameNumber);
                            msgNewRecord = successPopUp.findViewById(R.id.msg_new_record);
                            msgNewRecord.setVisibility(View.VISIBLE);
                            newRecordView = successPopUp.findViewById(R.id.new_record);
                            newRecordView.setVisibility(View.VISIBLE);
                            newRecordView.setText(newRecord);
                            gameInstance.setHighestScoreGameOne(newRecord);
                            gameInstance.saveGame();
                        }
                        else{
                            saveProgressControl(gameInstance, false, gameNumber);
                        }
                    }
                    else{
                        if(gameInstance.isBetterScoreTwo(newRecord)){
                            saveProgressControl(gameInstance, true, gameNumber);
                            msgNewRecord = successPopUp.findViewById(R.id.msg_new_record);
                            msgNewRecord.setVisibility(View.VISIBLE);
                            newRecordView = successPopUp.findViewById(R.id.new_record);
                            newRecordView.setVisibility(View.VISIBLE);
                            newRecordView.setText(newRecord);
                            gameInstance.setHighestScoreGameTwo(newRecord);
                            gameInstance.saveGame();
                        }
                        else{
                            saveProgressControl(gameInstance, false, gameNumber);
                        }
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

    protected void ShowProgressControlPopUp(GameOperations gameInstance)
    {
        Dialog progressControlPopUp = new Dialog(this);
        progressControlPopUp.setContentView(R.layout.progress_data_popup);
        progressControlPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Update the score of the first game of the user
        // if the score is smaller than the previous one

        // pressing on the close popup feature
        progressControlPopUp.findViewById(R.id.close_pop_up).setOnClickListener(v -> {
            progressControlPopUp.dismiss();
        });

        // Get all fields to fill from the db from the progress popup layout
        TextView recordBrokeOne = progressControlPopUp.findViewById(R.id.num_record_broke_one);
        TextView recordBrokeTwo = progressControlPopUp.findViewById(R.id.num_record_broke_two);
        TextView numGamesPlayedOne = progressControlPopUp.findViewById(R.id.num_games_one);
        TextView numGamesPlayedTwo = progressControlPopUp.findViewById(R.id.num_games_two);
        TextView lastGameDateOne = progressControlPopUp.findViewById(R.id.date_one);
        TextView lastGameDateTwo = progressControlPopUp.findViewById(R.id.date_two);
        TextView progressOne = progressControlPopUp.findViewById(R.id.percentage_progress_one);
        TextView progressTwo = progressControlPopUp.findViewById(R.id.percentage_progress_two);
        TextView titleGameOne = progressControlPopUp.findViewById(R.id.title_game_one);
        TextView titleGameTwo = progressControlPopUp.findViewById(R.id.title_game_two);

        GameOperations tempGameInstance = new GameOperations(gameInstance.getUserInstance());
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        DocumentReference docRef = mDatabase
                .collection("ProgressData")
                .document(tempGameInstance.getUserInstance().getPhone());

        // check if the progress control instance already exists in db
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                // check if document exists
                if (document.exists()) {

                    // fill all the data on the player's games
                    recordBrokeOne.setText(String.valueOf(((Number) Objects.requireNonNull(document
                            .get("numRecordBrokeOne"))).intValue()));
                    recordBrokeTwo.setText(String.valueOf(((Number) Objects.requireNonNull(document
                            .get("numRecordBrokeTwo"))).intValue()));
                    numGamesPlayedOne.setText(String.valueOf(((Number) Objects.requireNonNull(document
                            .get("sumGamesOne"))).intValue()));
                    numGamesPlayedTwo.setText(String.valueOf(((Number) Objects.requireNonNull(document
                            .get("sumGamesTwo"))).intValue()));
                    lastGameDateOne.setText(document.getString("lastGameDateOne"));
                    lastGameDateTwo.setText(document.getString("lastGameDateTwo"));
                    progressOne.setText(document.getString("progressPercentOne"));
                    progressTwo.setText(document.getString("progressPercentTwo"));

                    // check the ages of the player to fit the titles of the games
                    if(gameInstance.getUserInstance().getAge().matches("[6-7]"))
                    {
                        titleGameOne.setText("בול פגיעה בצבעים");
                        titleGameTwo.setText("מי נמצא לצידי");
                    }
                    else{
                        titleGameOne.setText("בול פגיעה במספרים");
                        titleGameTwo.setText("התאם והשלם");
                    }

                }
                else
                {
                    Log.d("ERROR : ", "Problem, there is no chance" +
                            " that there is no progress control");
                }
            }
            else
            {
                Log.d("INFO: ", "get failed with ", task.getException());
            }
        });

        progressControlPopUp.setCancelable(false);
        progressControlPopUp.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void saveProgressControl(GameOperations gameInstance,
                                       boolean isNewRecord, int gameNumber)
    {
        GameOperations tempGameInstance = new GameOperations(gameInstance.getUserInstance());
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        DocumentReference docRef = mDatabase
                .collection("ProgressData")
                .document(tempGameInstance.getUserInstance().getPhone());

        // Init record number broke per game


        // check if the progress control instance already exists in db
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                // check if document exists
                if (document.exists()) {

                    int numRecordBroke;
                    // the date of the current game
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = myDateObj.format(myFormatObj);

                    // game number one to update
                    if(gameNumber == 1) {

                        // check if the this is a new game so there is no record to increase
                        numRecordBroke = checkRecordBroke(document,isNewRecord,
                                "numRecordBrokeOne");
                        // create a record for progress control db
                        docRef.update("numRecordBrokeOne", numRecordBroke);
                        docRef.update("sumGamesOne", (Long)document
                                .get("sumGamesOne") + 1);
                        docRef.update("lastGameDateOne",formattedDate);
                        if((Long)document.get("sumGamesOne") != 0) {
                            int sumGamesOne = ((Number)((Long) Objects.requireNonNull(document
                                    .get("sumGamesOne")))).intValue() + 1;
                            double progressPercentOne = ((double)numRecordBroke/
                                    (double)sumGamesOne)*100;
                            docRef.update("progressPercentOne",
                                    (Math.round(progressPercentOne)) + "%");
                        }
                    }
                    // game number two to update
                    else{

                        // check if the this is a new game so there is no record to increase
                        numRecordBroke = checkRecordBroke(document,isNewRecord,
                                "numRecordBrokeTwo");

                        // create a record for progress control db
                        docRef.update("numRecordBrokeTwo", numRecordBroke);
                        docRef.update("sumGamesTwo",
                                (Long)document.get("sumGamesTwo") + 1);
                        docRef.update("lastGameDateTwo", formattedDate);
                        if((Long)document.get("sumGamesTwo") != 0)
                        {
                            int sumGamesTwo = ((Number)((Long) document
                                    .get("sumGamesTwo"))).intValue() + 1;
                            double progressPercentTwo = ((double)numRecordBroke/
                                    (double)sumGamesTwo)*100;
                            docRef.update("progressPercentTwo",
                                    (Math.round(progressPercentTwo)) + "%");
                        }

                    }
                }
                else
                {
                    Log.d("ERROR : ", "Problem, there is no chance" +
                            " that there is no progress control");
                }
            }
            else
            {
                Log.d("INFO: ", "get failed with ", task.getException());
            }
        });
    }


    public int checkRecordBroke(DocumentSnapshot document, boolean isNewRecord,
                                String numBrokePerGame){

        int numRecordBroke = ((Number)((Long) Objects.requireNonNull(document.get(numBrokePerGame)))).intValue();

        if(isNewRecord) {
            numRecordBroke ++;
        }

        return numRecordBroke;
    }

}
