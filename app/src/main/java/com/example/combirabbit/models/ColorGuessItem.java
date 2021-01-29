package com.example.combirabbit.models;

import java.util.ArrayList;

public class ColorGuessItem {
    private ArrayList<Integer> strUserColorsGuess;

    public ColorGuessItem()
    {
        this.strUserColorsGuess = new ArrayList<>();
    }
    public ColorGuessItem(ArrayList<Integer> strUserColorsGuess, String strGuessResult)
    {
        this.strUserColorsGuess = strUserColorsGuess;
    }


    public ArrayList<Integer> getStrUserColorsGuess() {
        return strUserColorsGuess;
    }

    public void setStrUserColorsGuess(ArrayList<Integer> strUserColorsGuess) {
        this.strUserColorsGuess = strUserColorsGuess;
    }

    public void addToList(int colorImage)
    {
        this.strUserColorsGuess.add(colorImage);
    }

}
