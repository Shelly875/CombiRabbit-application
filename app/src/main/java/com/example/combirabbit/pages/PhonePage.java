package com.example.combirabbit.pages;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.combirabbit.R;
import com.example.combirabbit.activity.ActivityMethods;
import com.example.combirabbit.activity.GameBoard;
import com.example.combirabbit.models.GameOperations;
import com.example.combirabbit.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PhonePage extends ActivityMethods {

    // Class variables
    private AnimationDrawable rabbitAnimation;
    private boolean isNewGame;
    private EditText phoneNumberField;
    private String strPhoneNumber = "";
    private int maxPhoneLength = 10;
    private String phonePrefix = "+972";
    private int animationDuration = 11;
    private User newUser;
    private FirebaseFirestore mDatabase;
    private GameOperations gameInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.enter_phone_page);

        // Get previous intent parameters sent
        Intent prevIntent = getIntent();
        newUser = (User) prevIntent.getSerializableExtra("newUser");
        isNewGame = (boolean) prevIntent.getSerializableExtra("isNewGame");

        // In case the user enter to this intent
        // from loading an exist game
        if(newUser == null) {
            newUser = new User();
        }

        // Start playing recording - enter your name
        this.configRecord(R.raw.enter_phone_record);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon, animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }

    public void CodePage(View view) {

        // get phone number text
        phoneNumberField = findViewById(R.id.user_input);
        strPhoneNumber = phoneNumberField.getText().toString();

        // stop record and animation
        this.stopRecordAndAnimation(rabbitAnimation, animationDuration);

        // Check that the phone number is valid
        if (strPhoneNumber.isEmpty() || strPhoneNumber.length() < maxPhoneLength) {
            this.configRecord(R.raw.error_phone_record);
            phoneNumberField.setError("הקש מספר טלפון חוקי :-)");
            phoneNumberField.requestFocus();
        } else {

            // Add the phone number to the new user's parameters
            this.newUser.setPhone(phonePrefix + strPhoneNumber);
            // Add new user to the game
            this.gameInstance = new GameOperations(this.newUser);

            this.mDatabase = FirebaseFirestore.getInstance();
            DocumentReference docRef = this.mDatabase
                    .collection("SavedGames")
                    .document(this.gameInstance.getUserInstance().getPhone());

            // check if the user already has a game saved in db
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (!document.exists() && !isNewGame) {
                        phoneNumberField.setError("לא קיים משחק לטעינה");
                        phoneNumberField.requestFocus();
                        Log.d("INFO: ", "No such document");
                    } else {
                        if (!document.exists() && isNewGame) {
                            this.gameInstance.saveGame();
                        }
                        startActivity(new Intent(this, GameBoard.class)
                                .putExtra("gameInstance", gameInstance));
                    }
                } else {
                    phoneNumberField.setError("קיימת בעיה במערכת. אנא נסה שנית מאוחר יותר.");
                    phoneNumberField.requestFocus();
                    Log.d("INFO: ", "get failed with ", task.getException());
                }
            });
        }
    }
}