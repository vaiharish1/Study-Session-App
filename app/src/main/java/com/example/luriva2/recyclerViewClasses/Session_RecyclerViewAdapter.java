package com.example.luriva2.recyclerViewClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luriva2.R;
import com.example.luriva2.dataModelClasses.Session;

import java.util.ArrayList;

public class Session_RecyclerViewAdapter extends RecyclerView.Adapter<Session_RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Session> sessionModels;
    private OnItemClickListener listener;

    public Session_RecyclerViewAdapter(Context context, ArrayList<Session> sessionModels) {
        this.context = context;
        this.sessionModels = sessionModels;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    @NonNull
    @Override
    public Session_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_todays_sessions, parent, false);

        return new Session_RecyclerViewAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Session_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.taskNameText.setText(sessionModels.get(position).getTask().getTaskName());
        holder.taskTimeDisplayText.setText(sessionModels.get(position).getTimeblock().getStartTime() + " - " + sessionModels.get(position).getTimeblock().getEndTime());
        holder.sessionTypeDisplayText.setText(sessionModels.get(position).getTask().getTaskType());
        holder.upButton.setText(R.string.upButtonStr);
        holder.downButton.setText(R.string.downButtonStr);
        if (position == 0) {
            holder.upButton.setVisibility(View.GONE);
        } else {
            holder.upButton.setVisibility(View.VISIBLE);
        }
        if (position == getItemCount()-1) {
            holder.downButton.setVisibility(View.GONE);
        } else {
            holder.downButton.setVisibility(View.VISIBLE);
        }
//        Log.v("BINDING POSITION", Integer.toString(position) + " " + holder.taskNameText.getText());
    }

    @Override
    public int getItemCount() {
//        Log.v("ITEM COUNT", Integer.toString(sessionModels.size()));
        return sessionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskNameText, taskTimeDisplayText, sessionTypeDisplayText;
        private Button upButton, downButton;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            taskNameText = itemView.findViewById(R.id.taskNameText);
            taskTimeDisplayText = itemView.findViewById(R.id.taskTimeDisplayText2);
            sessionTypeDisplayText = itemView.findViewById(R.id.sessionTypeText);
            upButton = itemView.findViewById(R.id.upButton);
            downButton = itemView.findViewById(R.id.downButton);

            upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getAdapterPosition());
                }
            });

            downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}
