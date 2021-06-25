package com.example.combirabbit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.combirabbit.R;
import com.example.combirabbit.models.NumberGuessItem;

import java.util.ArrayList;


public class GuessNumAdapter extends RecyclerView.Adapter<GuessNumAdapter.ExampleViewHolder> {
    private final ArrayList<NumberGuessItem> nNumberGuessList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtUserNumGuess;
        private final LinearLayout viewGuessResult;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            viewGuessResult = itemView.findViewById(R.id.game_result);
            txtUserNumGuess = itemView.findViewById(R.id.user_num_guess);
        }
    }

    public GuessNumAdapter(ArrayList<NumberGuessItem> nNumberGuessList) {
        this.nNumberGuessList = nNumberGuessList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.numbers_adapter, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        NumberGuessItem currentItem = nNumberGuessList.get(position);
        int nSavedIndex = 0;

        holder.txtUserNumGuess.setText(currentItem.getUserNumbersGuess());
        if(currentItem.getGuessResult() != null) {
            int BULL = 0;
            for (int i = 0; i < currentItem.getGuessResult()[BULL]; i++) {
                holder.viewGuessResult.getChildAt(i).setBackgroundResource(R.drawable.bull_sign);
                nSavedIndex++;
            }

            int COW = 1;
            for (int j = 0; j < currentItem.getGuessResult()[COW]; j++) {
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
        return nNumberGuessList.size();
    }

}