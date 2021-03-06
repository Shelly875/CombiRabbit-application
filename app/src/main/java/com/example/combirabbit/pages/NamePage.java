package com.example.combirabbit.pages;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.combirabbit.R;
import com.example.combirabbit.activity.ActivityMethods;
import com.example.combirabbit.activity.GameBoard;
import com.example.combirabbit.activity.MainActivity;
import com.example.combirabbit.models.User;

public class NamePage extends ActivityMethods {

    // Class variables
    private EditText editTextCode;
    private User newUser;
    private String newUserName;
    private boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.enter_name_page);

        // Get previous intent parameters sent
        Intent prevIntent = getIntent();
        isNewGame = (boolean) prevIntent.getSerializableExtra("isNewGame");
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        this.configAnimation(R.drawable.combi_animation, R.raw.enter_name_record, true);
    }


    public void AgePage(View view) {

        // Create new user and add his name
        newUser = new User();
        editTextCode = findViewById(R.id.user_input);
        newUserName = editTextCode.getText().toString().trim();
        newUser.setName(newUserName);

        if (newUserName.isEmpty() || !(newUserName.matches("[a-zA-Zא-ת][a-zA-Zא-ת]+"))) {
            editTextCode.setError("אנא הזן שם חוקי");
            editTextCode.requestFocus();
        }else{
            this.mediaPlayer.stop();
            startActivity(new Intent(this, AgePage.class)
                    .putExtra("newUser", newUser).putExtra("isNewGame", isNewGame));
        }
    }

    public void onBackPressed ()
    {
        super.onBackPressed();
        this.mediaPlayer.stop();
        finish();
        Intent in = new Intent(NamePage.this,MainActivity.class);
        startActivity(in);
    }
}
