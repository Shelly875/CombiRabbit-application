package com.example.combirabbit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.combirabbit.R;
import com.example.combirabbit.models.ColorGuessItem;
import java.util.ArrayList;


public class GuessColorAdapter extends RecyclerView.Adapter<GuessColorAdapter.ExampleViewHolder> {
    private final ArrayList<ColorGuessItem> nColorsGuessList;
    private final int COW = 1;
    private final int BULL = 0;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgColorOne;
        private final ImageView imgColorTwo;
        private final ImageView imgColorThree;
        private final RelativeLayout viewGuessResult;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            imgColorOne = itemView.findViewById(R.id.imgGuessOne);
            imgColorTwo = itemView.findViewById(R.id.imgGuessTwo);
            imgColorThree = itemView.findViewById(R.id.imgGuessThree);
            viewGuessResult = itemView.findViewById(R.id.game_result);
        }
    }

    public GuessColorAdapter(ArrayList<ColorGuessItem> nColorsGuessList) {
        this.nColorsGuessList = nColorsGuessList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.colors_adapter, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
       ColorGuessItem currentItem = nColorsGuessList.get(position);
          int nSavedIndex = 0;
          holder.imgColorOne.setBackgroundResource(currentItem.getStrUserColorsGuess().get(0));
          holder.imgColorTwo.setBackgroundResource(currentItem.getStrUserColorsGuess().get(1));
          holder.imgColorThree.setBackgroundResource(currentItem.getStrUserColorsGuess().get(2));

          if(currentItem.getGuessResult() != null) {
              for (int i = 0; i < currentItem.getGuessResult()[this.BULL]; i++) {
                  holder.viewGuessResult.getChildAt(i).setBackgroundResource(R.drawable.bull_sign);
                  nSavedIndex++;
              }

              for (int j = 0; j < currentItem.getGuessResult()[this.COW]; j++) {
                  holder.viewGuessResult.getChildAt(nSavedIndex)
                          .setBackgroundResource(R.drawable.cow_sign);
                  nSavedIndex++;
              }
          }
          else
          {
              holder.viewGuessResult.getChildAt(1).setBackgroundResource(R.drawable.line);
          }
    }

    @Override
    public int getItemCount() {
        return nColorsGuessList.size();
    }
}