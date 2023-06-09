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

    private Context context; // the context
    private ArrayList<Session> allSessions; // all of the sessions in the recycler view

    private RecyclerViewInterface recyclerViewInterface;

    // the constructor
    public ViewTasksSessions_RecyclerViewAdapter(Context context, ArrayList<Session> allSessions, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.allSessions = allSessions;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // creating the actual recycler view with all of the rows
    @NonNull
    @Override
    public ViewTasksSessions_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view_tasks_sessions, parent, false);

        return new ViewTasksSessions_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    // setting the rows with the correct information
    @Override
    public void onBindViewHolder(@NonNull ViewTasksSessions_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.taskTimeText.setText(allSessions.get(position).getTimeblock().getStartTime() + " - " + allSessions.get(position).getTimeblock().getEndTime());
        holder.doingDateText.setText(allSessions.get(position).getDate().toString());
    }

    // get total amount of sessions
    @Override
    public int getItemCount() {
        return allSessions.size();
    }

    // the view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // all of the layouts
        private TextView doingDateText, taskTimeText;

        // the constructor
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            doingDateText = itemView.findViewById(R.id.doingDateText_viewTasksSessions);
            taskTimeText = itemView.findViewById(R.id.taskTimeDisplayText_viewTasksSessions);

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
