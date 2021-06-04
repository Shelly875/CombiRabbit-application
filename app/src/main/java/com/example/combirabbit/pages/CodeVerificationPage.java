package com.example.combirabbit.pages;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.combirabbit.R;
import com.example.combirabbit.activity.ActivityMethods;
import com.example.combirabbit.activity.GameBoard;
import com.example.combirabbit.activity.MainActivity;
import com.example.combirabbit.models.GameOperations;
import com.example.combirabbit.models.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class CodeVerificationPage extends ActivityMethods {

    // Class variables
    private String mVerificationId;
    private String strPhoneNumber;
    private String strCode;
    private User newUser;
    private EditText editTextCode;
    private FirebaseAuth mAuth;
    private GameOperations gameInstance;
    private FirebaseUser currentUser;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.enter_code_page);

        //initializing objects
        this.mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.user_input);

        // Get previous intent parameters sent
        Intent prevIntent = getIntent();
        gameInstance = (GameOperations) prevIntent.getSerializableExtra("gameInstance");
        strPhoneNumber = gameInstance.getUserInstance().getPhone();

        // Send verification code
        sendVerificationCode(strPhoneNumber);

        // Restore instance state
        // put this code after starting phone number verification
        if (mVerificationId == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
       this.configAnimation(R.drawable.combi_animation, R.raw.enter_code_record, true);
    }

    //the callback to detect the verification status
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    //Getting the code sent by SMS
                    String code = phoneAuthCredential.getSmsCode();
                    Log.d("Log: ", "Im in verification complete!");
                    //sometime the code is not detected automatically
                    //in this case the code will be null
                    //so user has to manually enter the code
                    verifyVerificationCode(code);
                    if (code != null) {
                        editTextCode.setText(code);
                        //verifying the code
                        verifyVerificationCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.d("Log: ", "Im in verification Failed!");
                    Toast.makeText(CodeVerificationPage.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.e("TAG", e.getMessage());
                }

                //when the code is generated then this method will receive the code.
                @Override
                public void onCodeSent(@NonNull String s,
                                       @NonNull PhoneAuthProvider.ForceResendingToken
                                               forceResendingToken) {
                    Log.d("Log: ", "Im in onCodeSent!");
//                    super.onCodeSent(s, forceResendingToken);

                    //storing the verification id that is sent to the user
                    mVerificationId = s;
                    Log.d("Log: ", "Verification id is: " + mVerificationId);
                }
            };

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
            if(mVerificationId == null)
            {
                Log.d("Log: ", "this is null for sure!");
            }

            if(this.mVerificationId == null)
            {
                Log.d("Log: ", "this is null for sure!");
            }

            Log.d("Log: ", "code: " +  code);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e){
            Log.d("Error:", String.valueOf(e));
        }
    }

    //used for signing the user
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        this.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(CodeVerificationPage.this,
                        task -> {
                            if (task.isSuccessful() && !(task.getException()
                                    instanceof FirebaseAuthInvalidCredentialsException)) {

                                // Check that the current user is logged in
                                this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (this.currentUser != null) {

                                    // Verification succeeded and moving to game board
                                    startActivity(new Intent(this,
                                            GameBoard.class).putExtra("gameInstance",
                                            this.gameInstance));
                                }
                            }
                            else {

                                //verification unsuccessful.. display an error message
                                editTextCode.setError("הקוד שגוי");
                                editTextCode.requestFocus();
                            }
                        });
    }

    public void GameBoard(View view) {

        // stop animation and record when clicking on the button
        this.mediaPlayer.stop();

        // check that the code is valid
        strCode = editTextCode.getText().toString().trim();
        if (strCode.isEmpty() || strCode.length() < 6) {

            // start record for enter valid number
            this.configRecord(R.raw.error_code_record, true);
            editTextCode.setError("הקש מספר חוקי");
            editTextCode.requestFocus();
            return;
        }

        // If the code is correct, continue to the next screen
        verifyVerificationCode(strCode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationId = savedInstanceState.getString(KEY_VERIFICATION_ID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID, mVerificationId);
    }
}