package com.example.combirabbit.models;

import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class NumberGuessItem {

    private String nUserNumbersGuess;
    private int [] nGuessResult;
    private final int BULL = 0;
    private final int COW = 1;

    public NumberGuessItem() {
        this.nUserNumbersGuess = "";
        this.nGuessResult = new int[]{0, 0};
    }

    public NumberGuessItem(Editable nNewUserNumGuess) {
        this.nUserNumbersGuess = nNewUserNumGuess.toString();
        this.nGuessResult = new int[]{0, 0};
    }

    public int[] getGuessResult() {
        return this.nGuessResult;
    }

    public void setGuessResult(int[] nNewGuessResult) {
        this.nGuessResult = nNewGuessResult;
    }

    public String getUserNumbersGuess() {
        return this.nUserNumbersGuess;
    }

    public void setUserNumbersGuess(Editable nNewUserNumbersGuess) {
        this.nUserNumbersGuess = nNewUserNumbersGuess.toString();
    }

    public int[] checkGuess(int[] nGameNumbers) {
        int countBull = 0;
        int countCow = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        //check bull
        for (int i = 0; i < nGameNumbers.length; i++) {
            int c1 = nGameNumbers[i];
            int c2 = Integer.parseInt(String.valueOf(this.nUserNumbersGuess.charAt(i)));

            if (c1 == c2) {
                countBull++;
            } else {
                if (map.containsKey(c1)) {
                    int freq = map.get(c1);
                    freq++;
                    map.put(c1, freq);
                } else {
                    map.put(c1, 1);
                }
            }
        }

        //check cow
        for (int i = 0; i < nGameNumbers.length; i++) {
            int c1 = nGameNumbers[i];
            int c2 = Integer.parseInt(String.valueOf(this.nUserNumbersGuess.charAt(i)));

            if (c1 != c2) {
                if (map.containsKey(c2)) {
                    countCow++;
                    if (map.get(c2) == 1) {
                        map.remove(c2);
                    } else {
                        int freq = map.get(c2);
                        freq--;
                        map.put(c2, freq);
                    }
                }
            }
        }

        if(this.nGuessResult == null)
        {
            Log.d("Log", "hello i'm null!!");
        }
        if (countBull == 0 && countCow == 0) {
            this.nGuessResult = new int[]{0, 0};;
        } else {
            this.nGuessResult[this.BULL] = countBull;
            this.nGuessResult[this.COW] = countCow;
        }
        return this.nGuessResult;
    }

    @NonNull
    public String toString(){

        return this.getUserNumbersGuess() + ", ";
    }
}
