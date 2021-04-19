package com.example.combirabbit.models;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.combirabbit.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BearsArrangement {

    private Integer[] game_bears;
    private Integer[] game_rules;
    private int nLevel;
    private int nPlaces;


    // Init the game with it's proper level
    public BearsArrangement(int newLevel){
        this.nLevel = newLevel;

        Integer [] tempGameRules;
        Integer[] bears_only_color = new Integer[]{R.drawable.teddy_bear_blue,
                R.drawable.teddy_bear_red,
                R.drawable.teddy_bear_yellow,
                R.drawable.teddy_bear_green,
                R.drawable.teddy_bear_brown,
                R.drawable.teddy_bear_purple};

        // level 1
        if(this.nLevel == 1)
        {
            // X arranges
            this.nPlaces = 3;

            // random bears from the bears repository
            Collections.shuffle(Arrays.asList(bears_only_color));

            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, this.nPlaces);
            tempGameRules = Arrays.copyOfRange(this.game_bears,0, this.nPlaces);

            // declare the rules of the game
            Collections.shuffle(Arrays.asList(this.game_bears));
            Collections.shuffle(Arrays.asList(tempGameRules));

            this.game_rules = Arrays.copyOfRange(tempGameRules,0,2);

        }
        // level 2 or 3
        if(this.nLevel == 2 || this.nLevel == 3)
        {
            // X arranges
            this.nPlaces = 4;

            // random bears from the bears repository
            Collections.shuffle(Arrays.asList(bears_only_color));

            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, this.nPlaces);
            tempGameRules = Arrays.copyOfRange(this.game_bears,0, this.nPlaces);

            // declare the rules of the game
            Collections.shuffle(Arrays.asList(this.game_bears));
            Collections.shuffle(Arrays.asList(tempGameRules));
            this.game_rules = Arrays.copyOfRange(tempGameRules,0,2);
        }

        Integer[] bears_only_equip = new Integer[]{R.drawable.teddy_bear_purple_hat,
                R.drawable.teddy_bear_brown_bracelet,
                R.drawable.teddy_bear_blue_glasses,
                R.drawable.teddy_bear_green_hat,
                R.drawable.teddy_bear_red_hat,
                R.drawable.teddy_bear_yellow_glasses};
        List <Integer> allBearsColorsAndEquip = new ArrayList<>();

        // level 4
        if(this.nLevel == 4)
        {
            // X arranges
            this.nPlaces = 4;

            // Merge all bears images to one array
            Collections.addAll(allBearsColorsAndEquip, bears_only_color);
            Collections.addAll(allBearsColorsAndEquip, bears_only_equip);

            // random bears from the bears only colors repository + equip
            Collections.shuffle(allBearsColorsAndEquip);

            // copy to the games bears only the first X places
            this.game_bears = allBearsColorsAndEquip.subList(0,this.nPlaces).toArray(new Integer[0]);

            tempGameRules = Arrays.copyOfRange(this.game_bears,0, this.nPlaces);
            // declare the rules of the game
            Collections.shuffle(Arrays.asList(this.game_bears));

            Collections.shuffle(Arrays.asList(tempGameRules));
            this.game_rules = Arrays.copyOfRange(tempGameRules,0,4);

            // for the bears check later in the game, we need one bear to be
            // the same for the second rule
            this.game_rules[2] = this.game_rules[1];


        }
        // level 5
        if(this.nLevel == 5) {
            // X arranges
            this.nPlaces = 6;

            // Merge all bears images to one array
            Collections.addAll(allBearsColorsAndEquip, bears_only_color);
            Collections.addAll(allBearsColorsAndEquip, bears_only_equip);

            // random bears from the bears only colors repository + equip
            Collections.shuffle(allBearsColorsAndEquip);

            // copy to the games bears only the first 4 places
            // to make it a little difficult

            this.game_bears = allBearsColorsAndEquip.subList(0, this.nPlaces)
                    .toArray(new Integer[0]);
            tempGameRules = Arrays.copyOfRange(this.game_bears, 0, this.nPlaces);
            // declare the rules of the game
            Collections.shuffle(Collections.singletonList(this.game_bears));
            Collections.shuffle(Arrays.asList(tempGameRules));
            this.game_rules = Arrays.copyOfRange(tempGameRules, 0, 4);

            // for the bears check later in the game, we need one bear to be
            // the same for the second rule
            this.game_rules[2] = this.game_rules[1];
        }
    }

    // Place images in the user repository
    public void placeRepository(LinearLayout linearRepository)
    {
        // Take the repository layout
        // and go over it's image button children
        for(int i=0; i < this.get_places(); i++)
        {
            linearRepository.getChildAt(i).setBackgroundResource(this.getGame_bears()[i]);
        }
    }

    // Place the rules of the game by the level
    public void placeGamesRules(RelativeLayout linearGameRules)
    {
        // init rules layout
        LinearLayout linearRuleOne = (LinearLayout) linearGameRules.getChildAt(1);
        LinearLayout linearRuleTwo = (LinearLayout) linearGameRules.getChildAt(2);

        // check the level of the game
        // only one rule for levels 1,2,3

        linearRuleOne.getChildAt(0).setBackgroundResource(this.getGame_rules()[0]);
        linearRuleOne.getChildAt(1).setBackgroundResource(this.getGame_rules()[1]);

        // place the arrow and the cross
        linearRuleOne.getChildAt(2).setBackgroundResource(R.drawable.whositnexttome_arrow);
        linearRuleOne.getChildAt(3).setBackgroundResource(R.drawable.cow_sign);

        // two rules for levels 4,5
        if(this.get_gameLevel() >= 4)
        {
            linearRuleTwo.getChildAt(0).setBackgroundResource(this.getGame_rules()[2]);
            linearRuleTwo.getChildAt(1).setBackgroundResource(this.getGame_rules()[3]);

            // place the arrow and the cross
            linearRuleTwo.getChildAt(2).setBackgroundResource(R.drawable.whositnexttome_arrow);
            linearRuleOne.getChildAt(3).setBackgroundResource(R.drawable.bull_sign);
            linearRuleTwo.getChildAt(3).setBackgroundResource(R.drawable.cow_sign);

        }
    }

    // check that the user arrangement is valid
    // according to the rules
    public boolean check_arrangement(ArrayList<Integer> userBearsGuess)
    {
        // In case the guess syntax is invalid
        if(userBearsGuess.size() < this.getGame_bears().length)
        {
            return false;
        }

        // in case the guess syntax is valid
        for(int i = 0; i < userBearsGuess.size()-1; i++)
        {
            // In case we are in greater levels
            if(this.getGame_rules().length == 2) {
                // check the current place with the first rule
                if (userBearsGuess.get(i).equals(this.getGame_rules()[0])) {
                    // check if bear [i] sit next to bear [i + 1]
                    if (userBearsGuess.get(i + 1).equals(this.getGame_rules()[1])) {
                        return false;
                    }
                }

                // check the current place with the second rule
                // check the current place with the first rule
                if (userBearsGuess.get(i).equals(this.getGame_rules()[1])) {
                    // check if bear [i] sit next to bear [i + 1]
                    if (userBearsGuess.get(i + 1).equals(this.getGame_rules()[0])) {
                        return false;
                    }
                }

            }
            else
            {
                if(userBearsGuess.get(i).equals(this.getGame_rules()[0])
                && userBearsGuess.get(i+1).equals(this.getGame_rules()[1]))
                {
                    if(userBearsGuess.get(i+2).equals(this.game_rules[3]))
                    {
                        return false;
                    }
                }
                else {
                    if (userBearsGuess.get(i).equals(this.getGame_rules()[1])
                            && userBearsGuess.get(i + 1).equals(this.getGame_rules()[0])) {
                        if (userBearsGuess.get(i - 1).equals(this.game_rules[3])) {
                            return false;
                        }
                    }
                }

//                if(userBearsGuess.get(i).equals(this.getGame_rules()[2]))
//                {
//                    if(userBearsGuess.get(i + 1).equals(this.getGame_rules()[3]))
//                    {
//                        return false;
//                    }
//                }
//
//                if(userBearsGuess.get(i).equals(this.getGame_rules()[3]))
//                {
//                    if(userBearsGuess.get(i + 1).equals(this.getGame_rules()[2]))
//                    {
//                        return false;
//                    }
//                }
            }
        }
        return true;
    }


    public Integer[] getGame_rules() {
        return this.game_rules;
    }

    public void setGame_rules(Integer[] game_rules) {
        this.game_rules = game_rules;
    }

    public Integer[] getGame_bears() {
        return this.game_bears;
    }

    public void setGame_bears(Integer[] new_game_bears) {
        this.game_bears = new_game_bears;
    }

    public void set_places(int newPlace)
    {
        this.nPlaces = newPlace;
    }

    public int get_places(){
        return this.nPlaces;
    }

    public int get_gameLevel(){
        return this.nLevel;
    }

    public void set_gameLevel(int newLevel)
    {
        this.nLevel = newLevel;
    }


}
