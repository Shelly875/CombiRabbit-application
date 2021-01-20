package com.example.combirabbit.pages;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.combirabbit.R;
import com.example.combirabbit.activity.ActivityMethods;
import com.example.combirabbit.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NamePage extends ActivityMethods {

    // Class variables
    private AnimationDrawable rabbitAnimation;
    private EditText editTextCode;
    private User newUser;
    private String newUserName;
    private int animationDuration = 9;
    private boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.enter_name_page);

        // Start playing recording - enter your name
        this.configRecord(R.raw.enter_name_record);

        // Get previous intent parameters sent
        Intent prevIntent = getIntent();
        isNewGame = (boolean) prevIntent.getSerializableExtra("isNewGame");
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon,animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }


    public void AgePage(View view) {

        // Create new user and add his name
        newUser = new User();
        editTextCode = findViewById(R.id.user_input);
        newUserName = editTextCode.getText().toString().trim();
        newUser.setName(newUserName);

        this.stopRecordAndAnimation(rabbitAnimation, animationDuration);
        startActivity(new Intent(this,AgePage.class)
                .putExtra("newUser", newUser).putExtra("isNewGame", isNewGame));
    }
}