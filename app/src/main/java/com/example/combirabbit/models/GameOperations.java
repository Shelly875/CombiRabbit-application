package com.example.combirabbit.models;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameOperations implements Serializable {

    private User userInstance;
    private FirebaseFirestore mDatabase;
    private String highestScoreGameOne = "";
    private String highestScoreGameTwo = "";

    public GameOperations(User newUser) {
        this.userInstance = newUser;
    }

    public GameOperations(User newUser, String highestScoreOne, String highestScoreTwo) {
        this.userInstance = newUser;
        this.highestScoreGameOne = highestScoreOne;
        this.highestScoreGameTwo = highestScoreTwo;
    }

    // Start connection to the database server
    public void setFireBaseInstance() {
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    // Get firebase instance
    public FirebaseFirestore getFireBaseInstance() {

        this.mDatabase = FirebaseFirestore.getInstance();
        return this.mDatabase;
    }

    // Save the user details and the game itself
    // in cloud store db
    public void saveGame() {

        Map<String, Object> user = new HashMap<>();
        user.put("name", this.getUserInstance().getName());
        user.put("age", this.getUserInstance().getAge());
        user.put("highestScoreOne", this.highestScoreGameOne);
        user.put("highestScoreTwo", this.highestScoreGameTwo);

        // Check in case there is already a saved phone in DB
        // if so - save the data instead of the current row
        // and not in addition to it
        // Add a new document with a generated ID
        this.getFireBaseInstance().collection("SavedGames")
                .document(this.getUserInstance().getPhone())
                .set(user);
    }

    // Save the kid's highest score at the end of the game
    public void saveHighestScore(String newScore) {
        // If savedGame.getScore() > newScore
        // save the score in the DB for this user (recognize by email)
        // Else
        // don't save
    }

    public User getUserInstance() {
        return this.userInstance;
    }

    public void setNewUser(User newUser) {
        this.userInstance = newUser;
    }

    public String getHighestScoreGameOne() {
        return this.highestScoreGameOne;
    }

    public void setHighestScoreGameOne(String highestScoreGameOne) {
        this.highestScoreGameOne = highestScoreGameOne;
    }

    public String getHighestScoreGameTwo() {
        return this.highestScoreGameTwo;
    }

    public void setHighestScoreGameTwo(String highestScoreGameTwo) {
        this.highestScoreGameTwo = highestScoreGameTwo;
    }

    public boolean isBetterScoreOne(String newRecord)
    {

        // In case this is the first game
        if(this.getHighestScoreGameOne().equals(""))
        {
            Log.d("Log: ", "score: " + this.getHighestScoreGameOne());
            return true;
        }

        // Make string to int in order to compare between them
        int newRecordTemp = Integer.parseInt(newRecord.replace(":",""));
        int oldRecordTemp = Integer.parseInt(this.getHighestScoreGameOne()
                .replace(":", ""));

        // Check which score is better, if the current one it better return false,
        // else return true
        Log.d("LOG: ", "new record: "+ newRecordTemp + " old record: " + oldRecordTemp);
        return newRecordTemp < oldRecordTemp;
    }


    public boolean isBetterScoreTwo(String newRecord)
    {
        // In case this is the second game
        if(this.getHighestScoreGameTwo().equals(""))
        {
            Log.d("Log: ", "score: " + this.getHighestScoreGameTwo());
            return true;
        }

        // Make string to int in order to compare between them
        int newRecordTemp = Integer.parseInt(newRecord.replace(":",""));
        int oldRecordTemp = Integer.parseInt(this.getHighestScoreGameTwo()
                .replace(":", ""));

        // Check which score is better, if the current one it better return false,
        // else return true
        Log.d("LOG: ", "new record: "+ newRecordTemp + " old record: " + oldRecordTemp);
        return newRecordTemp < oldRecordTemp;
    }
}

