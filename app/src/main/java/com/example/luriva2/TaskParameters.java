package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Constants;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TaskParameters extends AppCompatActivity {

    private ArrayList<Session> allSessions, daysSessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private ArrayList<Task> allTasks; // all the tasks

    private Date today; // today's date and the date of the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting today's date by getting a calendar instance
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));
    }

    // custom toast message
    public void showToast(String str) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.navigation_bar_toast, (ViewGroup) findViewById(R.id.toastLayoutRoot));

        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(str);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    // get today's date
    public Date getToday() {
        return today;
    }

    // check if task name is suitable
    public boolean checkTaskName(String name) {
        if (name.isEmpty()) {
            showToast("Empty task name.");
            return true;
        }
        return false;
    }

    // check if due date fits the regular expression
    public boolean checkDueDate(String dueDateStr) {
        String dueDateRegEx = "^(1[0-2]|0[1-9])/(3[01]|[12]\\d|0[1-9])/\\d{4}$";

        if (!dueDateStr.matches(dueDateRegEx)) {
            showToast("Incorrect date format.");
            return true;
        }

        String[] dueDateComponents = dueDateStr.split("/");
        Date dueDate = new Date(Integer.parseInt(dueDateComponents[0]), Integer.parseInt(dueDateComponents[1]), Integer.parseInt(dueDateComponents[2]));
        if (dueDate.compareTo(today) < 0) {
            showToast("Due date cannot be before today.");
            return true;
        }

        return false;
    }

    // transform it into an actual date
    public Date transformToDate(String dueDateStr) {
        String[] dueDateComponents = dueDateStr.split("/");
        return new Date(Integer.parseInt(dueDateComponents[0]), Integer.parseInt(dueDateComponents[1]), Integer.parseInt(dueDateComponents[2]));
    }

    // check if estimate time isn't too large or negative
    public boolean checkEstimatedTime(String timeStr) {
        if (timeStr.isEmpty()) {
            showToast("No estimated time given.");
            return true;
        }

        if (timeStr.contains(".")) {
            showToast("No decimal minutes.");
            return true;
        }

        int time = Integer.parseInt(timeStr);
        if (time > Constants.MAX_ESTIMATED_TIME) {
            showToast("Why?");
            return true;
        }

        if (time < 0) {
            showToast("You cannot do a task for negative time.");
            return true;
        }

        return false;
    }

    // transform time into an integer
    public int transformToTime(String timeStr) {
        return Integer.parseInt(timeStr);
    }

    // check if the difficulty is suitable
    public boolean checkDifficulty(String difficulty) {
        if (difficulty.equals("Click Difficulty")) {
            showToast("No task difficulty stated.");
            return true;
        }
        return false;
    }

    // transform estimated difficulty into an integer
    public int transformToEstimatedDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
            default:
                return 0;
        }
    }

    // check how often (for repetitive tasks)
    public boolean checkHowOften(String howOftenStr) {
        if (howOftenStr.isEmpty()) {
            showToast("How often task is repeated not given.");
            return false;
        }

        int howOften = Integer.parseInt(howOftenStr);
        if (howOften <= 0) {
            showToast("Don't break the system.");
            return false;
        }

        if (howOften >= Constants.MAX_HOW_OFTEN) {
            showToast("Why.");
            return false;
        }
        return true;
    }

    // transform into an integer
    public int transformToHowOften(String howOftenStr) {
        return Integer.parseInt(howOftenStr);
    }

    // add the task by loading it, adding it to the array list, and saving it
    public void addTask(Task t) {
        loadTasks();
        allTasks.add(t);
        saveTasks();
    }

    // save data according to this particular date
    public void saveData(Date thisDay) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Session> savedSessions = new ArrayList<>();
        for (int i = 0; i < allSessions.size(); i++) {
            if (!allSessions.get(i).getDate().equals(thisDay)) {
                savedSessions.add(allSessions.get(i));
            }
        }

        savedSessions.addAll(daysSessions);

        String json = gson.toJson(savedSessions);

        editor.putString("session list", json);
        editor.apply();
    }

    // saving tasks
    public void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Task> savedTasks = new ArrayList<>(allTasks);
        String json = gson.toJson(savedTasks);

        editor.putString("task list", json);
        editor.apply();
    }

    // loading all the sessions
    public void loadData(Date thisDay) {
        daysSessions = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        for (Session s : allSessions) {
//            Log.v("all sessions here", "adding these sessions: " + s.toString());
            if (s.getDate().equals(thisDay)) {
                daysSessions.add(s);
//                Log.v("TODAYS SESSION ADDING", "adding these sessions: " + s.toString());
            }
        }
    }

    // loading all the tasks
    public void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        allTasks = gson.fromJson(json, type);
    }

    // adding the session (the real meat of the algorithm)
    public boolean addingSessions(Date doingDate, int estimatedTime, Task task, int sessionId) {
        loadData(doingDate);
        // if there aren't any sessions on this date YET, then add one at 4pm
        if (daysSessions.size() == 0) {
            Time startTime = new Time(12+4, 0);
            Time endTime = startTime.add(0, estimatedTime);
            Timeblock newTB = new Timeblock(startTime, endTime);
            Session newSession = new Session(task, doingDate, newTB, sessionId);
            daysSessions.add(newSession);
            saveData(doingDate);
            return true;
        } // otherwise, add this session right after the previous session according to buffer times
        else {
            Session lastSession = daysSessions.get(daysSessions.size()-1);
            Time startTime = lastSession.getNextStartTime();
            Time endTime = startTime.add(0, estimatedTime);

            Time midnight = new Time(0, 0);
            Time fourPM = new Time(16, 0);

            if (endTime.compareTo(midnight) > 0 && endTime.compareTo(fourPM) < 0) return false;
            else {
                Timeblock newTB = new Timeblock(startTime, endTime);
                Session newSession = new Session(task, doingDate, newTB, sessionId);
                daysSessions.add(newSession);
                saveData(doingDate);
                return true;
            }
        }
    }
}