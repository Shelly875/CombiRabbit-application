package com.example.combirabbit.models;

import android.util.Log;

import com.example.combirabbit.R;

import java.util.ArrayList;
import java.util.Random;

public class MatchPattern {
    private final int nLevel;
    private Pattern singlePatternToGuess;

    public MatchPattern(int newLevel){

        // Random action
        Random r = new Random();

        // get current level
        this.nLevel = newLevel;

        // Array that will hold all the possible patterns
        ArrayList <Pattern> patternToGuess = new ArrayList<Pattern>();

        // Add all the possible images
        patternToGuess.add(new Pattern(R.drawable.img_comb_one));
        patternToGuess.add(new Pattern(R.drawable.img_comb_two));
        patternToGuess.add(new Pattern(R.drawable.img_comb_three));
        patternToGuess.add(new Pattern(R.drawable.img_comb_four));
        patternToGuess.add(new Pattern(R.drawable.img_comb_five));
        patternToGuess.add(new Pattern(R.drawable.img_comb_six));
        patternToGuess.add(new Pattern(R.drawable.img_comb_seven));
        patternToGuess.add(new Pattern(R.drawable.img_comb_eight));
        patternToGuess.add(new Pattern(R.drawable.img_comb_nine));
        patternToGuess.add(new Pattern(R.drawable.img_comb_ten));
        patternToGuess.add(new Pattern(R.drawable.img_comb_eleven));
        patternToGuess.add(new Pattern(R.drawable.img_comb_twelve));
        patternToGuess.add(new Pattern(R.drawable.img_comb_thirteen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_fourteen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_fiftheen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_sixteen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_seventeen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_eighteen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_nineteen));
        patternToGuess.add(new Pattern(R.drawable.img_comb_twenty));

        // set image to random according to the level
        if(this.nLevel == 1)
        {
            this.singlePatternToGuess = patternToGuess
                    .get(r.nextInt(patternToGuess.size() - 17));
        }

        if(this.nLevel == 2)
        {
            this.singlePatternToGuess = patternToGuess
                    .get(r.nextInt(4) + 4);
        }

        if(this.nLevel == 3)
        {
            this.singlePatternToGuess = patternToGuess
                    .get(r.nextInt(4) + 8);
        }

        if(this.nLevel == 4)
        {
            this.singlePatternToGuess = patternToGuess
                    .get(r.nextInt(4) + 12);
        }

        if(this.nLevel == 5)
        {
            this.singlePatternToGuess = patternToGuess
                    .get(r.nextInt(4) + 16);
        }
    }


    public Pattern getSinglePatternToGuess() {
        return this.singlePatternToGuess;
    }

    public void setSinglePatternToGuess(Pattern singlePatternToGuess) {
        this.singlePatternToGuess = singlePatternToGuess;
    }

    public boolean isMatch(ArrayList<ImagePlace> userPattern) {

        if(userPattern.size() != this.getSinglePatternToGuess().get_ImgPattern().size())
        {
            Log.d("msg :", "user pattern size: " + userPattern.size());
            Log.d("msg :", ": singlePatternToGuess " + this.getSinglePatternToGuess().get_ImgPattern().size());

            return false;
        }
        for (int i = 0; i < userPattern.size(); i++) {
            if(this.getSinglePatternToGuess().get_ImgPattern().get(i).getImage() ==
                    userPattern.get(i).getImage())
            {
                Log.d("msg : ", "Degree of user: " + userPattern.get(i).getDegree());
                Log.d("msg : ", "Degree of pattern: " +
                        this.getSinglePatternToGuess().get_ImgPattern().get(i).getDegree());
                if(this.getSinglePatternToGuess().get_ImgPattern().get(i).getDegree() !=
                userPattern.get(i).getDegree()){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        return true;
    }
}
