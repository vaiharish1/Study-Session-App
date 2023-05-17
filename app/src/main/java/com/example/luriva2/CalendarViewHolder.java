package com.example.luriva2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView dayOfMonth; // days of the month
    private final CalendarAdapter.OnItemListener onItemListener; // on item click listener

    // the constructor
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener){
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    // this is what happened when you click on the day
    @Override
    public void onClick(View view){
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }


}
