package com.example.combirabbit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.combirabbit.R;
import com.example.combirabbit.models.GameOperations;
import com.example.combirabbit.models.ImagePlace;
import com.example.combirabbit.models.MatchPattern;
import com.example.combirabbit.models.Pattern;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MatchAndComplete extends ActivityMethods{

    private GameOperations gameInstance;
    private Chronometer timerView;
    private int shapeClickPlace = 0;
    private FrameLayout layerShapesBoard;
    private ArrayList<ImagePlace> userPlacement;
    private int nLevel = 1;
    private final int MAX_LEVEL = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to use full screen
        this.fullScreen();

        // Main view - enter name button
        setContentView(R.layout.match_and_complete_game);

        // Make screen landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

       // Intent prevIntent = getIntent();
        //this.gameInstance = (GameOperations) prevIntent.getSerializableExtra("gameInstance");
        //GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());


    }

    protected void onStart() {
        super.onStart();

        // Start playing animation & record when pressing the rabbit icon
        int animationDuration = 5;
        AnimationDrawable rabbitAnimation = (AnimationDrawable) this.configAnimation
                (R.id.combi_icon, animationDuration);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);
    }

    public void startGame(View view){

        // Buttons on the screen to initialize
        ImageButton btnStartGame = findViewById(R.id.btn_start_game);
        ImageButton btnClearBoard = findViewById(R.id.btn_clear_arrange);
        ImageButton btnRotateShape = findViewById(R.id.btn_rotate_shape);
        ImageButton btnBlockTriangle = findViewById(R.id.btn_block_triangle);
        ImageButton btnHalfBlueCircle = findViewById(R.id.btn_half_circle);
        ImageButton btnGreenRec = findViewById(R.id.btn_green_rectangle);
        ImageButton btnBlueHalfStar = findViewById(R.id.btn_blue_half_star);
        ImageButton btnPurpleCircleRecIn = findViewById(R.id.btn_purple_in_circle_rec);
        ImageButton btnParallelogramBlue = findViewById(R.id.btn_parallelogram_blue);
        ImageButton btnRhombusRed = findViewById(R.id.btn_rhombus_red);
        ImageButton btnQCircleRed = findViewById(R.id.btn_q_circle_red);
        ImageButton btnCircleYellow = findViewById(R.id.btn_yellow_square);
        ImageButton btnBlockCircleEmpty = findViewById(R.id.btn_block_circle);
        ImageButton btnLinesYellow = findViewById(R.id.btn_lines_yellow);
        ImageButton btnStarBlueEmpty = findViewById(R.id.btn_blue_star_empty);
        ImageButton btnSquareOrange = findViewById(R.id.btn_orange_square);
        ImageButton btnParallelogramGreen = findViewById(R.id.btn_parallelogram_green);
        ImageButton btnTriangleOrange = findViewById(R.id.btn_orange_triangle);
        ImageButton btnGreenShape = findViewById(R.id.btn_green_shape);
        ImageButton btnPurpleCircleRecOut = findViewById(R.id.btn_purple_out_circle_rec);
        ImageButton btnGreenTriangle = findViewById(R.id.btn_green_triangle);


        // enable buttons - clear board and rotate
        // make start game button disappear
        btnClearBoard.setEnabled(true);
        btnStartGame.setVisibility(View.INVISIBLE);
        btnRotateShape.setEnabled(true);

        // Declare shapes buttons on click
        btnBlockTriangle.setOnClickListener(this::onClick);
        btnHalfBlueCircle.setOnClickListener(this::onClick);
        btnGreenRec.setOnClickListener(this::onClick);
        btnBlueHalfStar.setOnClickListener(this::onClick);
        btnPurpleCircleRecIn.setOnClickListener(this::onClick);
        btnParallelogramBlue.setOnClickListener(this::onClick);
        btnRhombusRed.setOnClickListener(this::onClick);
        btnQCircleRed.setOnClickListener(this::onClick);
        btnCircleYellow.setOnClickListener(this::onClick);
        btnBlockCircleEmpty.setOnClickListener(this::onClick);
        btnLinesYellow.setOnClickListener(this::onClick);
        btnStarBlueEmpty.setOnClickListener(this::onClick);
        btnSquareOrange.setOnClickListener(this::onClick);
        btnParallelogramGreen.setOnClickListener(this::onClick);
        btnTriangleOrange.setOnClickListener(this::onClick);
        btnGreenShape.setOnClickListener(this::onClick);
        btnPurpleCircleRecOut.setOnClickListener(this::onClick);
        btnGreenTriangle.setOnClickListener(this::onClick);

        // Init guess layer
        this.layerShapesBoard = findViewById(R.id.shapes_board);

        // Init the random image pattern for the user to build
        ImageView imgRandImgPattern = findViewById(R.id.random_guess_img);

        // Init user placement on the board
        this.userPlacement = new ArrayList<>();

        // Timer start the game
        // Start timer
        startTimer();

        // All the checking stuff
        MatchPattern randMatchPattern = new MatchPattern(this.nLevel);
        // set the rand image by level
        imgRandImgPattern.setBackgroundResource(randMatchPattern
                .getSinglePatternToGuess().getImgLevel());

        // Init
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Play the game
                playGame(randMatchPattern, view);
                handler.postDelayed(this, 500);
            }
        };

        //Start
        handler.postDelayed(runnable, 500);

    }


    public void onClick(View v) {

        // For placing the shapes in the board
        ImageView imgChosenShape = new ImageView(this);

        // Set the shapes buttons onclick
        switch (v.getId()) {
            case R.id.btn_block_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_block_triangular_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_block_triangular_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_half_circle:
                imgChosenShape.setBackgroundResource(R.drawable.match_blue_half_circle_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_green_rectangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_green_rectangle_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_green_rectangle_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_blue_half_star:
                imgChosenShape.setBackgroundResource(R.drawable.match_half_star_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_half_star_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_purple_in_circle_rec:
                imgChosenShape.setBackgroundResource(R.drawable.match_purple_half_circle_rec_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_purple_half_circle_rec_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_parallelogram_blue:
                imgChosenShape.setBackgroundResource(R.drawable.match_blue_trapeze_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_rhombus_red:
                imgChosenShape.setBackgroundResource(R.drawable.match_red_rhombus_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_q_circle_red:
                imgChosenShape.setBackgroundResource(R.drawable.match_red_quarter_circle_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_red_quarter_circle_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_yellow_square:
                imgChosenShape.setBackgroundResource(R.drawable.match_yellow_square_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_yellow_square_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_block_circle:
                imgChosenShape.setBackgroundResource(R.drawable.match_circle_in_circle_block);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_circle_in_circle_block,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_lines_yellow:
                imgChosenShape.setBackgroundResource(R.drawable.match_yellow_lines_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_yellow_lines_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_blue_star_empty:
                imgChosenShape.setBackgroundResource(R.drawable.match_star_backgroud_colored);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_star_backgroud_colored,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_orange_square:
                imgChosenShape.setBackgroundResource(R.drawable.match_orange_square_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_orange_square_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_parallelogram_green:
                imgChosenShape.setBackgroundResource(R.drawable.match_green_parallelogram_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_green_parallelogram_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_orange_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_orange_triangle_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_green_shape:
                imgChosenShape.setBackgroundResource(R.drawable.match_light_green_rec_squre_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_purple_out_circle_rec:
                imgChosenShape.setBackgroundResource(R.drawable.match_purple_rec_circle_out_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_purple_rec_circle_out_img,
                        (int) imgChosenShape.getRotation()));
                break;
            case R.id.btn_green_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_light_green_triangle_img);
                this.layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                this.shapeClickPlace++;
                this.userPlacement.add(new ImagePlace(R.drawable.match_light_green_triangle_img,
                        (int) imgChosenShape.getRotation()));
                break;
        }

    }

    // Function that start the timer of the game
    public void startTimer() {
        timerView = findViewById(R.id.timer);
        timerView.setBase(SystemClock.elapsedRealtime());
        timerView.start();
    }

    // Enable buttons after each level increase
    public void enable_shapes_buttons() {

        // Buttons on the screen to initialize
        ImageButton btnBlockTriangle = findViewById(R.id.btn_block_triangle);
        ImageButton btnHalfBlueCircle = findViewById(R.id.btn_half_circle);
        ImageButton btnGreenRec = findViewById(R.id.btn_green_rectangle);
        ImageButton btnBlueHalfStar = findViewById(R.id.btn_blue_half_star);
        ImageButton btnPurpleCircleRecIn = findViewById(R.id.btn_purple_in_circle_rec);
        ImageButton btnParallelogramBlue = findViewById(R.id.btn_parallelogram_blue);
        ImageButton btnRhombusRed = findViewById(R.id.btn_rhombus_red);
        ImageButton btnQCircleRed = findViewById(R.id.btn_q_circle_red);
        ImageButton btnCircleYellow = findViewById(R.id.btn_yellow_square);
        ImageButton btnBlockCircleEmpty = findViewById(R.id.btn_block_circle);
        ImageButton btnLinesYellow = findViewById(R.id.btn_lines_yellow);
        ImageButton btnStarBlueEmpty = findViewById(R.id.btn_blue_star_empty);
        ImageButton btnSquareOrange = findViewById(R.id.btn_orange_square);
        ImageButton btnParallelogramGreen = findViewById(R.id.btn_parallelogram_green);
        ImageButton btnTriangleOrange = findViewById(R.id.btn_orange_triangle);
        ImageButton btnGreenShape = findViewById(R.id.btn_green_shape);
        ImageButton btnPurpleCircleRecOut = findViewById(R.id.btn_purple_out_circle_rec);
        ImageButton btnGreenTriangle = findViewById(R.id.btn_green_triangle);

        btnBlockTriangle.setEnabled(true);
        btnHalfBlueCircle.setEnabled(true);
        btnGreenRec.setEnabled(true);
        btnBlueHalfStar.setEnabled(true);
        btnPurpleCircleRecIn.setEnabled(true);
        btnParallelogramBlue.setEnabled(true);
        btnRhombusRed.setEnabled(true);
        btnQCircleRed.setEnabled(true);
        btnCircleYellow.setEnabled(true);
        btnBlockCircleEmpty.setEnabled(true);
        btnLinesYellow.setEnabled(true);
        btnStarBlueEmpty.setEnabled(true);
        btnSquareOrange.setEnabled(true);
        btnParallelogramGreen.setEnabled(true);
        btnTriangleOrange.setEnabled(true);
        btnGreenShape.setEnabled(true);
        btnPurpleCircleRecOut.setEnabled(true);
        btnGreenTriangle.setEnabled(true);
    }


    // clear the user's shapes board
    public void clearArrangement(View view) {

        this.shapeClickPlace = 0;
        enable_shapes_buttons();
        this.layerShapesBoard.removeAllViews();
        this.userPlacement = new ArrayList<>();
    }

    public void rotateShape(View v) {

       ImageView imgCurrentShapeOnBoard;
       imgCurrentShapeOnBoard = (ImageView) this.layerShapesBoard
               .getChildAt(this.layerShapesBoard.getChildCount() -1);
       // In case we complete a full cycle - init to the start
       if(imgCurrentShapeOnBoard.getRotation() == 360){
           imgCurrentShapeOnBoard.setRotation(0);
       }
       imgCurrentShapeOnBoard.setRotation(imgCurrentShapeOnBoard.getRotation() + 90);

       // each time the user rotate the shape, the shape's degree updated
        this.userPlacement.get(this.shapeClickPlace - 1)
                .setDegree((int) imgCurrentShapeOnBoard.getRotation());
    }

    protected void ShowPopUp(String newRecord)
    {
        int animationDuration = 8;
        ImageButton btnReturnToBoardGame;


        // Show the pop up for - instructions/start game
        // Start playing recording - enter your name
        this.configRecord(R.raw.guess_success);

        Dialog successPopUp = new Dialog(this);
        successPopUp.setContentView(R.layout.success_popup);
        successPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Start playing animation & record when pressing the rabbit icon
        AnimationDrawable rabbitAnimation;
        rabbitIcon = successPopUp.findViewById(R.id.combi_icon);
        rabbitIcon.setBackgroundResource(R.drawable.combi_animation);
        rabbitAnimation = (AnimationDrawable) rabbitIcon.getBackground();
        rabbitAnimation.start();

        // Update the score of the first game of the user
        // if the score is smaller than the previous one
        updateHighestScore(newRecord, successPopUp);

        // Stop animation after first time
        this.stopAnimation(rabbitAnimation, animationDuration);

        successPopUp.show();

        // Return to the game board
        GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());
        btnReturnToBoardGame = successPopUp.findViewById(R.id.btn_return);
        btnReturnToBoardGame.setOnClickListener(v ->
                startActivity(new Intent(successPopUp.getContext(), GameBoard.class)
                        .putExtra("gameInstance", tempGameInstance)));

    }

    protected void updateHighestScore(String newRecord,
                                      Dialog successPopUp)
    {
        GameOperations tempGameInstance = new GameOperations(this.gameInstance.getUserInstance());
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

                    if(this.gameInstance.isBetterScoreTwo(newRecord))
                    {
                        msgNewRecord = successPopUp.findViewById(R.id.msg_new_record);
                        msgNewRecord.setVisibility(View.VISIBLE);
                        newRecordView = successPopUp.findViewById(R.id.new_record);
                        newRecordView.setVisibility(View.VISIBLE);
                        newRecordView.setText(newRecord);
                        this.gameInstance.setHighestScoreGameOne(newRecord);
                        this.gameInstance.saveGame();
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

    public void playGame(MatchPattern randMatchPattern, View view){

        TextView txtCurrentLevel = findViewById(R.id.current_game_level);

        // Check if user pattern is like the image given to him

        // TODO: we will need to do some sort of listener
        //  so this function will always check

        if(randMatchPattern.isMatch(this.userPlacement))
        {
            Log.d("msg: ", "the result is true we are in");
            // if we finished all the levels
            if(this.nLevel == MAX_LEVEL) {
                // finished and pop up with harry!!
                Log.d("INFO: ", "game finished and you succeeded all!");

                timerView.stop();

                // if there is a new record, show on the screen and save
                // currentRecord > previousRecord, open firebase to check
                // TODO
                //ShowPopUp((String) timerView.getText());
            }
            this.nLevel ++;
            // increase the level in the screen
            txtCurrentLevel.setText(String.valueOf(this.nLevel));
            // clear board
            clearArrangement(view);
            // return to the main
            startGame(view);
        }
    }
}

