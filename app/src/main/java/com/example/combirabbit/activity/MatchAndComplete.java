package com.example.combirabbit.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.combirabbit.R;
import com.example.combirabbit.models.GameOperations;

public class MatchAndComplete extends ActivityMethods{

    private GameOperations gameInstance;
    private Chronometer timerView;
    private int shapeClickPlace = 0;
    private FrameLayout layerShapesBoard;

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

        // Timer start the game
        // Start timer
        startTimer();

    }


    public void onClick(View v) {

        // For placing the shapes in the board
        ImageView imgChosenShape = new ImageView(this);

        // Set the shapes buttons onclick
        switch (v.getId()) {
            case R.id.btn_block_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_block_triangular_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_half_circle:
                imgChosenShape.setBackgroundResource(R.drawable.match_blue_half_circle_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_green_rectangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_green_rectangle_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_blue_half_star:
                imgChosenShape.setBackgroundResource(R.drawable.match_half_star_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_purple_in_circle_rec:
                imgChosenShape.setBackgroundResource(R.drawable.match_purple_half_circle_rec_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_parallelogram_blue:
                imgChosenShape.setBackgroundResource(R.drawable.match_blue_trapeze_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_rhombus_red:
                imgChosenShape.setBackgroundResource(R.drawable.match_red_rhombus_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_q_circle_red:
                imgChosenShape.setBackgroundResource(R.drawable.match_red_quarter_circle_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_yellow_square:
                imgChosenShape.setBackgroundResource(R.drawable.match_yellow_square_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_block_circle:
                imgChosenShape.setBackgroundResource(R.drawable.match_circle_in_circle_block);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_lines_yellow:
                imgChosenShape.setBackgroundResource(R.drawable.match_yellow_lines_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_blue_star_empty:
                imgChosenShape.setBackgroundResource(R.drawable.match_star_backgroud_colored);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_orange_square:
                imgChosenShape.setBackgroundResource(R.drawable.match_orange_square_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_parallelogram_green:
                imgChosenShape.setBackgroundResource(R.drawable.match_green_parallelogram_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_orange_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_orange_triangle_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_green_shape:
                imgChosenShape.setBackgroundResource(R.drawable.match_light_green_rec_squre_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_purple_out_circle_rec:
                imgChosenShape.setBackgroundResource(R.drawable.match_purple_rec_circle_out_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
                break;
            case R.id.btn_green_triangle:
                imgChosenShape.setBackgroundResource(R.drawable.match_light_green_triangle_img);
                layerShapesBoard.addView(imgChosenShape);
                v.setEnabled(false);
                shapeClickPlace++;
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
    }

    public void rotateShape(View v) {

       ImageView imgCurrentShapeOnBoard;
       imgCurrentShapeOnBoard = (ImageView) this.layerShapesBoard
               .getChildAt(this.layerShapesBoard.getChildCount() -1);
        imgCurrentShapeOnBoard.setRotation(imgCurrentShapeOnBoard.getRotation() + 90);
    }

}
