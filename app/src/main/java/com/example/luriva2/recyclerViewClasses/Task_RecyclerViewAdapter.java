package com.example.luriva2.recyclerViewClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luriva2.R;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Task;

import java.util.ArrayList;

public class Task_RecyclerViewAdapter extends RecyclerView.Adapter<Task_RecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Task> allTasks;

    public Task_RecyclerViewAdapter(Context context, ArrayList<Task> allTasks) {
        this.context = context;
        this.allTasks = allTasks;
    }

    @NonNull
    @Override
    public Task_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_all_tasks, parent, false);

        return new Task_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Task_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.taskNameText.setText(allTasks.get(position).getTaskName());
        holder.taskTypeText.setText(allTasks.get(position).getTaskType());
//        Date dueDate = allTasks.get(position).getDueDate();
//        if (dueDate != null) {
//            holder.dueDateText.setText(dueDate.toString());
//        } else {
//            holder.dueDateText.setText("");
//        }
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView taskNameText, dueDateText, taskTypeText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameText = itemView.findViewById(R.id.taskNameText_allTasks);
            dueDateText = itemView.findViewById(R.id.dueDateText_allTasks);
            taskTypeText = itemView.findViewById(R.id.taskType_allTasks);
        }
    }
}
