/*
*   ADD TASK ACTIVITY
*   CONTAINS    form for task name
*               button to forward into AddTaskUsersActivity
* */
package com.example.tasqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddTaskActivity extends AppCompatActivity {

    private TextView taskName;
    private CalendarView calendarView;
    int selectedYear, selectedMonth, selectedDay;

    private final Bundle bundle = new Bundle();

    /* Main on create method */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        selectedYear = 2021;
        selectedMonth = 5;
        selectedDay = 8;

        taskName = findViewById(R.id.taskName);
        calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;
            }
        });
        FloatingActionButton addPeopleButton = findViewById(R.id.addPeopleButtontsk);

        /* Sending all useful data inside intent */
        addPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPeopleIntent = new Intent(AddTaskActivity.this, AddUsersActivity.class);
                addPeopleIntent.putExtra("projectId", getIntent().getStringExtra("projectId"));
                addPeopleIntent.putExtra("previous_activity", "Task");
                addPeopleIntent.putExtra("taskName", taskName.getText().toString());
                addPeopleIntent.putExtra("logged_mail", getIntent().getStringExtra("logged_mail"));
                addPeopleIntent.putExtra("logged_name", getIntent().getStringExtra("logged_name"));
                addPeopleIntent.putExtra("logged_surname", getIntent().getStringExtra("logged_surname"));
                addPeopleIntent.putExtra("year", selectedYear);
                addPeopleIntent.putExtra("month", selectedMonth);
                addPeopleIntent.putExtra("day", selectedDay);
                startActivity(addPeopleIntent);
            }
        });
    }

    /* Resume previous inputs in textfields */
    protected void onResume() {
        taskName.setText(bundle.getString("taskName"));
        super.onResume();
    }

    /* Saves inputs from textfields */
    protected void onPause() {
        bundle.putString("taskName", taskName.getText().toString());
        super.onPause();
    }

}
