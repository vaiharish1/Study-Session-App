package com.example.luriva2.recyclerViewClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luriva2.R;
import com.example.luriva2.dataModelClasses.Session;

import java.util.ArrayList;

public class ViewTasksSessions_RecyclerViewAdapter extends RecyclerView.Adapter<ViewTasksSessions_RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Session> allSessions;

    public ViewTasksSessions_RecyclerViewAdapter(Context context, ArrayList<Session> allSessions) {
        this.context = context;
        this.allSessions = allSessions;
    }

    @NonNull
    @Override
    public ViewTasksSessions_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view_tasks_sessions, parent, false);

        return new ViewTasksSessions_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTasksSessions_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.taskTimeText.setText(allSessions.get(position).getTimeblock().getStartTime() + " - " + allSessions.get(position).getTimeblock().getEndTime());
        holder.doingDateText.setText(allSessions.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return allSessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView doingDateText, taskTimeText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            doingDateText = itemView.findViewById(R.id.doingDateText_viewTasksSessions);
            taskTimeText = itemView.findViewById(R.id.taskTimeDisplayText_viewTasksSessions);
        }
    }
}
