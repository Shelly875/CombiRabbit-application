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
    private boolean isNewGame;
    private EditText phoneNumberField;
    private String strPhoneNumber = "";
    private int maxPhoneLength = 10;
    private String phonePrefix = "+972";
    private int animationDuration = 11;
    private User newUser;
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
        this.newUser = (User) prevIntent.getSerializableExtra("newUser");
        this.isNewGame = (boolean) prevIntent.getSerializableExtra("isNewGame");

        // In case the user enter to this intent
        // from loading an exist game
        if(this.newUser == null) {
            this.newUser = new User();
        }
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        this.configAnimation(R.drawable.combi_animation, R.raw.enter_phone_record, true);
    }

    public void CodePage(View view) {

        // get phone number text
        phoneNumberField = findViewById(R.id.user_input);
        strPhoneNumber = phoneNumberField.getText().toString();

        // stop record and animation
        this.mediaPlayer.stop();

        // Check that the phone number is valid
        if (strPhoneNumber.isEmpty() || strPhoneNumber.length() < maxPhoneLength) {
            this.configRecord(R.raw.error_phone_record, true);
            phoneNumberField.setError("הקש מספר טלפון חוקי :-)");
            phoneNumberField.requestFocus();
        } else {

            // Add the phone number to the new user's parameters
            this.newUser.setPhone(phonePrefix + strPhoneNumber);
            // Add new user to the game
            this.gameInstance = new GameOperations(this.newUser);
            GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());
            String phone = tempGameInstance.getUserInstance().getPhone();

            // Load game or start a new game (that was already saved)
            // when loading a game - take name, high score 1, high score 2 for view only
            // take age - for loading a specific game fragment
            FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
            DocumentReference docRef = mDatabase
                    .collection("SavedGames")
                    .document(phone);

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
                        if ((!document.exists() && isNewGame) ||
                                (document.exists() && isNewGame)) {

                            // save new game to db
                            this.gameInstance.saveGame();

                            // create new parent control for the new user
                            this.gameInstance.createParentControl();
                        }
                        startActivity(new Intent(this, GameBoard.class)
                                .putExtra("gameInstance", tempGameInstance));
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