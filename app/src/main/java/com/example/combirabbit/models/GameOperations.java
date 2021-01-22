package com.example.combirabbit.models;

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
        return userInstance;
    }

    public void setNewUser(User newUser) {
        this.userInstance = newUser;
    }

    public String getHighestScoreGameOne() {
        return highestScoreGameOne;
    }

    public void setHighestScoreGameOne(String highestScoreGameOne) {
        this.highestScoreGameOne = highestScoreGameOne;
    }

    public String getHighestScoreGameTwo() {
        return highestScoreGameTwo;
    }

    public void setHighestScoreGameTwo(String highestScoreGameTwo) {
        this.highestScoreGameTwo = highestScoreGameTwo;
    }
}
