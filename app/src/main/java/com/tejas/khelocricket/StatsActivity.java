package com.tejas.khelocricket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class StatsActivity extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Variables
    int userRuns;
    int userBalls;
    int userWickets;
    int compRuns;
    int compBalls;
    int compWickets;

    // Layout Variables
    TextView userScoreTxt, userAvg, userSr, compScoreTxt, compAvg, compSr;
    Button goToHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Initializing Layoyt Variables
        userScoreTxt = findViewById(R.id.user_score_txt);
        userAvg = findViewById(R.id.user_avg);
        userSr = findViewById(R.id.user_sr);
        compScoreTxt = findViewById(R.id.comp_score_txt);
        compAvg = findViewById(R.id.comp_avg);
        compSr = findViewById(R.id.comp_sr);
        goToHomeBtn = findViewById(R.id.go_to_home_btn);

//        Getting Intent and Initializing Variables
        Intent intent = getIntent();
        userRuns = intent.getIntExtra("userRuns", 0);
        userBalls = intent.getIntExtra("userBalls", 0);
        userWickets = intent.getIntExtra("userWickets", 0);
        compRuns = intent.getIntExtra("compRuns", 0);
        compBalls = intent.getIntExtra("compBalls", 0);
        compWickets = intent.getIntExtra("compWickets", 0);

        // Getting it into String Format
        String user_score = "User Score : " + userRuns + "/" + userWickets + " (" + userBalls + ")";

        String user_avg = "00";
        if (userWickets==0){user_avg = "undefined";}
        else{user_avg = "User Average : " + String.valueOf(df.format((double) userRuns/ (double) userWickets));}
        String user_sr = "User SR. : " + String.valueOf(df.format(((double) userRuns/(double) userBalls)*100));

        String comp_score = "Comp. Score : " + compRuns + "/" + compWickets + " (" + compBalls + ")";
        String comp_avg = "00";
        if (compWickets==0){comp_avg = "undefined";}
        else{comp_avg = "Computer Average : " + String.valueOf(df.format((double) compRuns/ (double) compWickets));}
        String comp_sr = "Computer SR. : " + String.valueOf(df.format(((double) compRuns/(double) compBalls)*100));

        // Updating Data in Layout
        userScoreTxt.setText(user_score);
        userAvg.setText(user_avg);
        userSr.setText(user_sr);
        compScoreTxt.setText(comp_score);
        compAvg.setText(comp_avg);
        compSr.setText(comp_sr);

        // Going to Home on button click
        goToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StatsActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}