package com.example.luriva2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth; // days of the month
    private final OnItemListener onItemListener; // on item click listener

    // the constructor
    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener){
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    // creating the actual recycler view with all of the rows
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    // setting the rows with the correct information
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
       holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    // get total amount of days
    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    // this is what happens when you click on a certain day
    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }

}
