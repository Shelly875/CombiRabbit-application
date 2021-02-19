package com.example.combirabbit.models;

import com.example.combirabbit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BearsArrangement {

    private int [] game_bears;
    private int [] game_rules;

    // Init the game with it's proper level
    BearsArrangement(int newLevel){
        int nPlaces;

        int[] bears_only_color = new int[]{R.drawable.teddy_bear_blue,
                R.drawable.teddy_bear_red,
                R.drawable.teddy_bear_yellow,
                R.drawable.teddy_bear_green,
                R.drawable.teddy_bear_brown,
                R.drawable.teddy_bear_purple};
        // level 1
        if(newLevel == 1)
        {
            // X arranges
            nPlaces = 3;
            // random bears from the bears repository
            Collections.shuffle(Collections.singletonList(bears_only_color));
            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, nPlaces);
            // declare the rules of the game
            Collections.shuffle(Collections.singletonList(this.game_bears));
            this.game_rules = Arrays.copyOfRange(this.game_bears,0,2);
        }
        // level 2 or 3
        if(newLevel == 2 || newLevel == 3)
        {
            // X arranges
            nPlaces = 4;
            // random bears from the bears repository
            Collections.shuffle(Collections.singletonList(bears_only_color));
            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, nPlaces);
            // declare the rules of the game
            Collections.shuffle(Collections.singletonList(this.game_bears));
            this.game_rules = Arrays.copyOfRange(this.game_bears,0,2);
        }

        int[] bears_only_equip = {R.drawable.teddy_bear_purple_hat,
                R.drawable.teddy_bear_brown_bracelet,
                R.drawable.teddy_bear_blue_glasses,
                R.drawable.teddy_bear_green_hat,
                R.drawable.teddy_bear_red_hat,
                R.drawable.teddy_bear_yellow_glasses};
        // level 4
        if(newLevel == 4)
        {
            nPlaces = 5;
            List<Object> allBearsColorsAndEquip = new ArrayList<>();

            // Merge all bears images to one array
            Collections.addAll(allBearsColorsAndEquip, bears_only_color);
            Collections.addAll(allBearsColorsAndEquip, bears_only_equip);

            // random bears from the bears only colors repository + equip
            Collections.shuffle(Collections.singletonList(allBearsColorsAndEquip));

            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, nPlaces);

            // declare the rules of the game
            Collections.shuffle(Collections.singletonList(this.game_bears));
            this.game_rules = Arrays.copyOfRange(this.game_bears,0,4);

        }
        // level 5
        if(newLevel == 5)
        {
            nPlaces = 6;
            List<Object> allBearsColorsAndEquip = new ArrayList<>();

            // Merge all bears images to one array
            Collections.addAll(allBearsColorsAndEquip, bears_only_color);
            Collections.addAll(allBearsColorsAndEquip, bears_only_equip);

            // random bears from the bears only colors repository + equip
            Collections.shuffle(Collections.singletonList(allBearsColorsAndEquip));

            // copy to the games bears only the first X places
            this.game_bears = Arrays.copyOfRange(bears_only_color,0, nPlaces);

            // declare the rules of the game
            Collections.shuffle(Collections.singletonList(this.game_bears));
            this.game_rules = Arrays.copyOfRange(this.game_bears,0,4);
        }


    }

    public int[] getGame_rules() {
        return game_rules;
    }

    public void setGame_rules(int[] game_rules) {
        this.game_rules = game_rules;
    }

    public int[] getGame_bears() {
        return this.game_bears;
    }

    public void setGame_bears(int[] new_game_bears) {
        this.game_bears = new_game_bears;
    }

}
