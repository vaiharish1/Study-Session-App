package com.example.luriva2.recyclerViewClasses;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luriva2.R;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Task;

import java.util.ArrayList;

public class Task_RecyclerViewAdapter extends RecyclerView.Adapter<Task_RecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface; // the item click listener
    private Context context; // the context
    private ArrayList<Task> allTasks; // the list of tasks

    // the constructor
    public Task_RecyclerViewAdapter(Context context, ArrayList<Task> allTasks, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.allTasks = allTasks;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // creating the actual recycler view with all of the rows
    @NonNull
    @Override
    public Task_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_all_tasks, parent, false);

        return new Task_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    // setting the rows with the correct information
    @Override
    public void onBindViewHolder(@NonNull Task_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.taskNameText.setText(allTasks.get(position).getTaskName());
        holder.taskTypeText.setText(allTasks.get(position).getTaskType());
        Date dueDate = allTasks.get(position).getDueDate();

        // if there's no due date then don't put it
        if (dueDate != null) {
            holder.dueDateText.setText("Due: " + dueDate.toString());
        } else {
            holder.dueDateText.setVisibility(View.GONE);
        }

        // set up the background color based on difficulty
        if (allTasks.get(position).getDifficulty() == 1) {
            holder.constraintLayout.setBackgroundColor(0xFFffe8d6);
        } else if (allTasks.get(position).getDifficulty() == 2) {
            holder.constraintLayout.setBackgroundColor(0xFFddbea9);
        } else {
            holder.constraintLayout.setBackgroundColor(0xFFcb997e);
            holder.taskNameText.setTextColor(0xFFffe8d6);
            holder.taskTypeText.setTextColor(0xFFffe8d6);
            holder.dueDateText.setTextColor(0xFFffe8d6);
        }
    }

    // get total amount of tasks
    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    // the view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // all of the layouts
        private TextView taskNameText, dueDateText, taskTypeText;
        private ConstraintLayout constraintLayout;

        // the constructor
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            // initializing textviews and buttons
            constraintLayout = itemView.findViewById(R.id.constraintLayout_allTasks);
            taskNameText = itemView.findViewById(R.id.taskNameText_allTasks);
            dueDateText = itemView.findViewById(R.id.dueDateText_allTasks);
            taskTypeText = itemView.findViewById(R.id.taskType_allTasks);

            // setting up on item click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
