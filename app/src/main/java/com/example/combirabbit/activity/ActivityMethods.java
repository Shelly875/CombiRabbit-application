package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.combirabbit.R;
import com.example.combirabbit.models.GameOperations;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    protected Object configAnimation(int combImg, int record, boolean isOnStart) {

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
        return rabbitAnimation;
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

    protected void ShowPopUp(GameOperations gameInstance, String newRecord)
    {
        ImageButton btnReturnToBoardGame;

        Dialog successPopUp = new Dialog(this);
        successPopUp.setContentView(R.layout.success_popup);
        successPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Show the pop up for - success in the game
        this.configAnimation(R.drawable.combi_animation,R.raw.guess_success,
                successPopUp, true);

        // Update the score of the first game of the user
        // if the score is smaller than the previous one
        updateHighestScore(gameInstance, newRecord, successPopUp);

        successPopUp.setCancelable(false);
        successPopUp.show();

        // Return to the game board
        GameOperations tempGameInstance = new GameOperations(gameInstance.getUserInstance());
        btnReturnToBoardGame = successPopUp.findViewById(R.id.btn_return);
        btnReturnToBoardGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mediaPlayer.stop();
                startActivity(new Intent(successPopUp.getContext(), GameBoard.class)
                        .putExtra("gameInstance", tempGameInstance));
            }
        });
    }

    protected void updateHighestScore(GameOperations gameInstance, String newRecord,
                                      Dialog successPopUp)
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

                    if(gameInstance.isBetterScoreTwo(newRecord))
                    {
                        msgNewRecord = successPopUp.findViewById(R.id.msg_new_record);
                        msgNewRecord.setVisibility(View.VISIBLE);
                        newRecordView = successPopUp.findViewById(R.id.new_record);
                        newRecordView.setVisibility(View.VISIBLE);
                        newRecordView.setText(newRecord);
                        gameInstance.setHighestScoreGameTwo(newRecord);
                        gameInstance.saveGame();
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
