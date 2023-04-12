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
        holder.taskNameText.setText(sessionModels.get(position).getSessionName());
        holder.taskTimeDisplayText.setText(Integer.toString(sessionModels.get(position).getStartTime()) + " - " + Integer.toString(sessionModels.get(position).getEndTime()));
        holder.upButton.setText(R.string.upButtonStr);
        holder.downButton.setText(R.string.downButtonStr);
        if (position == 0) holder.upButton.setVisibility(View.GONE);
        if (position == getItemCount()-1) holder.downButton.setVisibility(View.GONE);
        Log.v("BINDING POSITION", Integer.toString(position) + " " + holder.taskNameText.getText());
    }

    @Override
    public int getItemCount() {
        return sessionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskNameText, taskTimeDisplayText;
        private Button upButton, downButton;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            taskNameText = itemView.findViewById(R.id.taskNameText);
            taskTimeDisplayText = itemView.findViewById(R.id.taskTimeDisplayText2);
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
