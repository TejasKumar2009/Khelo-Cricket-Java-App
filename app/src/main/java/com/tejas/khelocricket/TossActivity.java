package com.tejas.khelocricket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TossActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
    // Not calling **super**, disables back button in current screen.
    }

    TextView heads_tails_text, multiworkText;
    ImageView toss_image;
    Button headsBtn, tailsBtn, batBtn, ballBtn, startMatchBtn;
    boolean tossChoice;
    String tossWinnerChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss);

        // Initializing Variables
        heads_tails_text = findViewById(R.id.heads_tails_text);
        multiworkText = findViewById(R.id.multiwork_text);
        toss_image = findViewById(R.id.toss_image);
        headsBtn = findViewById(R.id.heads_btn);
        tailsBtn = findViewById(R.id.tails_btn);
        batBtn = findViewById(R.id.bat_btn);
        ballBtn = findViewById(R.id.ball_btn);
        startMatchBtn = findViewById(R.id.start_match_btn);

        headsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossChoice = toss("heads");
                headsTailsClickMethod(tossChoice);
            }
        });
        tailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossChoice = toss("tails");
                heads_tails_text.setText("Tails");
                toss_image.setImageResource(R.drawable.toss_tails);
                toss_image.setMaxHeight(100);
                headsTailsClickMethod(tossChoice);
            }
        });

        batBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossWinnerChoice = "bat";
                multiworkText.setText("You Choose to " + tossWinnerChoice +" First !");
                batBtn.setVisibility(View.INVISIBLE);
                ballBtn.setVisibility(View.INVISIBLE);
                startMatchBtn.setVisibility(View.VISIBLE);
            }
        });
        ballBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossWinnerChoice = "ball";
                multiworkText.setText("You Choose to " + tossWinnerChoice +" First !");
                batBtn.setVisibility(View.INVISIBLE);
                ballBtn.setVisibility(View.INVISIBLE);
                startMatchBtn.setVisibility(View.VISIBLE);
            }
        });

        startMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TossActivity.this, GameActivity.class);
                if (tossChoice) {
                    if (tossWinnerChoice == "bat"){
                        intent.putExtra("firstUserTurn", "bat");
                    }
                    else if(tossWinnerChoice == "ball"){
                        intent.putExtra("firstUserTurn", "ball");
                    }
                } else{
                    if (tossWinnerChoice == "bat"){
                        intent.putExtra("firstUserTurn", "ball");
                    }
                    else if(tossWinnerChoice == "ball"){
                        intent.putExtra("firstUserTurn", "bat");
                    }
                }
                startActivity(intent);
            }
        });

    }

    // Methods

    // Toss Method
    boolean toss(String userChoice){
        int min = 0;
        int max = 1;
        int toss_number = (int)(Math.random()*(max-min+1)+min);
        String toss_string = "";

        if(toss_number == 0){
            toss_string = "heads";
            heads_tails_text.setText("Heads");
            toss_image.setImageResource(R.drawable.toss_heads);
        } else{
            toss_string = "tails";
            heads_tails_text.setText("Tails");
            toss_image.setImageResource(R.drawable.toss_tails);
        }

        if (userChoice=="heads" && toss_string=="heads" || userChoice=="tails" && toss_string=="tails"){
            return true;
        }
        else{
            return false;
        }
    }

    // Batting Balling Method
    String batball_comp_choice() {
        int min = 0;
        int max = 1;
        int batball_choice_number = (int) (Math.random() * (max - min + 1) + min);
        String batball_string_number = "";

        if (batball_choice_number == 0) {
            batball_string_number = "bat";
        } else {
            batball_string_number = "ball";
        }
        return batball_string_number;
    }

    // Heads and Tails Button Click Function
    void headsTailsClickMethod(boolean tossChoice){
        if(tossChoice){
            headsBtn.setVisibility(headsBtn.INVISIBLE);
            tailsBtn.setVisibility(tailsBtn.INVISIBLE);
            batBtn.setVisibility(batBtn.VISIBLE);
            ballBtn.setVisibility(ballBtn.VISIBLE);
            multiworkText.setText("You won the toss. Choose Batting or Balling.");
        }
        else{
            headsBtn.setVisibility(headsBtn.INVISIBLE);
            tailsBtn.setVisibility(tailsBtn.INVISIBLE);
            startMatchBtn.setVisibility(startMatchBtn.VISIBLE);
            String batball_comp_choice = batball_comp_choice();
            multiworkText.setText("Computer won the Toss & Choose to " + batball_comp_choice + " First !");
            tossWinnerChoice = batball_comp_choice;
        }
    }

}