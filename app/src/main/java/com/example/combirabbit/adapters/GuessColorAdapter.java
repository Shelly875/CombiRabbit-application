package com.example.combirabbit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.combirabbit.R;
import com.example.combirabbit.models.ColorGuessItem;

import java.util.ArrayList;


public class GuessColorAdapter extends RecyclerView.Adapter<GuessColorAdapter.ExampleViewHolder> {
    private ArrayList<ColorGuessItem> nColorsGuessList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgColorOne;
        private ImageView imgColorTwo;
        private ImageView imgColorThree;
        private TextView txtGuessResult;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            imgColorOne = itemView.findViewById(R.id.imgGuessOne);
            imgColorTwo = itemView.findViewById(R.id.imgGuessTwo);
            imgColorThree = itemView.findViewById(R.id.imgGuessThree);
            txtGuessResult = itemView.findViewById(R.id.txtGuessResult);

        }
    }

    public GuessColorAdapter(ArrayList<ColorGuessItem> nColorsGuessList) {
        this.nColorsGuessList = nColorsGuessList;
    }

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

          holder.imgColorOne.setBackgroundResource(currentItem.getStrUserColorsGuess().get(0));
          holder.imgColorTwo.setBackgroundResource(currentItem.getStrUserColorsGuess().get(1));
          holder.imgColorThree.setBackgroundResource(currentItem.getStrUserColorsGuess().get(2));
          holder.txtGuessResult.setText("פ פ פ");
    }

    @Override
    public int getItemCount() {
        return nColorsGuessList.size();
    }
}