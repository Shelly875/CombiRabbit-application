package com.example.combirabbit.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ColorGuessItem {

    private ArrayList<Integer> strUserColorsGuess;
    private int [] nGuessResult;
    private final int BULL = 0;
    private final int COW = 1;

    public ColorGuessItem()
    {
        this.strUserColorsGuess = new ArrayList<>();
        this.nGuessResult = new int[]{0, 0};
    }
    public ColorGuessItem(ArrayList<Integer> strUserColorsGuess, int[] nGuessResultNew)
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

    public int[] getGuessResult() {
        return this.nGuessResult;
    }

    public void setGuessResult( int[] nGuessResultNew) {
        this.nGuessResult = nGuessResultNew;
    }

    public int[] checkGuess(int [] nGameColors)
    {
        int countBull=0;
        int countCow=0;

        HashMap<Integer, Integer> map = new HashMap<>();

        //check bull
        for(int i=0; i<nGameColors.length; i++){
            int c1 = nGameColors[i];
            int c2 = this.strUserColorsGuess.get(i);

            if(c1==c2){
                countBull++;
            }else{
                if(map.containsKey(c1)){
                    int freq = map.get(c1);
                    freq++;
                    map.put(c1, freq);
                }else{
                    map.put(c1, 1);
                }
            }
        }

        //check cow
        for(int i=0; i<nGameColors.length; i++){
            Integer c1 = nGameColors[i];
            Integer c2 = this.strUserColorsGuess.get(i);

            if(!c1.equals(c2)){
                if(map.containsKey(c2)){
                    countCow++;
                    if(map.get(c2)==1){
                        map.remove(c2);
                    }else{
                        int freq = map.get(c2);
                        freq--;
                        map.put(c2, freq);
                    }
                }
            }
        }

        this.nGuessResult[this.BULL] = countBull;
        this.nGuessResult[this.COW] =countCow;
        if(countBull == 0 && countCow == 0)
        {
            this.nGuessResult = null;
        }
        return this.nGuessResult;
    }
}
