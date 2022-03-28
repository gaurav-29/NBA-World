package com.nextgensoft.nbaworld.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nextgensoft.nbaworld.R;

public class HomeScreenActivity extends AppCompatActivity {

    HomeScreenActivity context = HomeScreenActivity.this;
    TextView btnToday, btnUpcoming, btnCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //getSupportActionBar().hide();

        btnToday = findViewById(R.id.btnToday);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnCompleted = findViewById(R.id.btnCompleted);

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TodayAppointmentsActivity.class);
                startActivity(i);
            }
        });
        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(context, UpcomingActivity.class);
                startActivity(i2);
            }
        });
        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(context, CompletedActivity.class);
                startActivity(i3);
            }
        });
    }
}