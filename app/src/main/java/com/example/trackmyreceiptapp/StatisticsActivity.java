package com.example.trackmyreceiptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatisticsActivity extends AppCompatActivity {

    Button openCameraBtn, recentPurchasesBtn, statisticsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsbtn = findViewById(R.id.statistics_button);
        recentPurchasesBtn = findViewById(R.id.recent_purchases_button);

        recentPurchasesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(StatisticsActivity.this, MainActivity.class);
                startActivity(a);
            }
        });
    }
}