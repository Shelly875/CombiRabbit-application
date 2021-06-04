package com.example.combirabbit.pages;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.combirabbit.R;
import com.example.combirabbit.activity.ActivityMethods;
import com.example.combirabbit.activity.GameBoard;
import com.example.combirabbit.activity.MainActivity;
import com.example.combirabbit.models.User;


public class AgePage extends ActivityMethods {

    // Class variables
    private Spinner ageSpinner;
    private String spinnerAgeSelected;
    private User newUser;
    private boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.enter_age_page);

        // Get previous intent parameters sent
        Intent prevIntent = getIntent();
        newUser = (User) prevIntent.getSerializableExtra("newUser");
        isNewGame = (boolean) prevIntent.getSerializableExtra("isNewGame");

        // choose age from the list
        ageSpinner = findViewById(R.id.spinner_choose_age);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.ages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_age_config);
        ageSpinner.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        this.configAnimation(R.drawable.combi_animation, R.raw.enter_age_record, true);

    }

    public void PhonePage(View view) {

        // Take selected item from the age's spinner
        spinnerAgeSelected = ageSpinner.getSelectedItem().toString();

        // Add the age to the user's parameters
        newUser.setAge(spinnerAgeSelected);

        // Stop record and animation when clicking on the button
        this.mediaPlayer.stop();
        startActivity(new Intent(this,PhonePage.class)
                .putExtra("newUser", newUser).putExtra("isNewGame", isNewGame));
    }

//    public void onBackPressed ()
//    {
//        super.onBackPressed();
//        finish();
//        Intent in = new Intent(AgePage.this,MainActivity.class);
//        startActivity(in);
//    }
}