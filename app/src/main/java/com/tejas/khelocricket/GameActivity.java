package com.tejas.khelocricket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        // Not calling **super**, disables back button in current screen.
    }

    Button zero,one,two,three,four,six;
    TextView score, user_number, comp_number, chase_text, comp_turn_text, user_turn_text;
    int user_runs;
    int user_balls;
    int user_wickets = 0;
    int comp_runs;
    int comp_balls;
    int comp_wickets = 0;
    String currentUserTurnBat = "bat";
    String currentUserTurnBall = "ball";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Intializing Variables
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        six = findViewById(R.id.six);

        score = findViewById(R.id.score);
        user_number = findViewById(R.id.user_number);
        comp_number = findViewById(R.id.comp_number);
        chase_text = findViewById(R.id.chaseText);
        comp_turn_text = findViewById(R.id.comp_turn_text);
        user_turn_text = findViewById(R.id.user_turn_text);


        // Getting Intent
        Intent intent = getIntent();
        String tossUserChoice = intent.getStringExtra("firstUserTurn");

//        Main Game Logic
        if(Objects.equals(tossUserChoice, "bat")){
            comp_turn_text.setText("Computer's Bowling :");
            user_turn_text.setText("User's Batting :");
        }
        else{
            comp_turn_text.setText("Computer's Batting :");
            user_turn_text.setText("User's Bowling :");
        }

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGame(tossUserChoice, 0);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGame(tossUserChoice, 1);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MainGame(tossUserChoice, 2);}
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGame(tossUserChoice, 3);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGame(tossUserChoice, 4);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGame(tossUserChoice, 6);
            }
        });

    }

//    Generate Random Number From 0 to 5
    int generateRandomNumber(){
        int min = 1;
        int max = 6;
        int comp_runNo = (int)(Math.random()*(max-min+1)+min);
        if (comp_runNo==5){
            return 0;
        }
        else{
            return comp_runNo;

        }
    }

    // Method of Sending Data to StatsActivity
    public void sendDataStatsActivity(int userRuns, int userBalls, int userWickets, int compRuns, int compBalls, int compWickets){
     Intent intent = new Intent(GameActivity.this, StatsActivity.class);
     intent.putExtra("userRuns", userRuns);
     intent.putExtra("userBalls", userBalls);
     intent.putExtra("userWickets", userWickets);
     intent.putExtra("compRuns", compRuns);
     intent.putExtra("compBalls", compBalls);
     intent.putExtra("compWickets", compWickets);
     startActivity(intent);
    }

    // MainGame Method
    void MainGame(String tossUserChoice, int runNo){
        if (Objects.equals(tossUserChoice, "bat")){

            if (currentUserTurnBat.equals("bat")){
                int compRunNo = generateRandomNumber();

                if(compRunNo==runNo || compRunNo==5 && runNo==0) {
                    user_wickets += 1;

                    if (user_wickets==10){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("You are AllOut!");
                    builder.setMessage("Your Total Score is  " + user_runs + "! Now it's time for defend !");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentUserTurnBat = "ball";
                            user_number.setText("0");
                            comp_number.setText("0");
                            score.setText("Computer's Score : 0");
                            comp_turn_text.setText("Computer's Batting :");
                            user_turn_text.setText("User's Bowling :");
                            int comp_target = user_runs+1;
                            chase_text.setText("Computer needs " + comp_target + " runs to Win.");

                        }
                    });
                    builder.show();
                    }
                    else{
                        Toast.makeText(this, "Out! Your "+user_wickets+"th Wicket Gone!", Toast.LENGTH_SHORT).show();
                    }
//
                }
                else{
                    user_runs += runNo;
                }

                user_balls += 1;

                user_number.setText(String.valueOf(runNo));
                comp_number.setText(String.valueOf(compRunNo));
                score.setText("User's Score : " + String.valueOf(user_runs) + "/" + user_wickets + " (" + String.valueOf(user_balls) + ")");
            }
            else{
                int compRunNo = generateRandomNumber();

                if(comp_wickets==10 && comp_runs<user_runs){
                    int remainingRuns = user_runs-comp_runs;
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("You Won the Game!");
                    builder.setMessage("You Won the game by " + remainingRuns + " Runs!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }

                if(comp_runs>user_runs){
                    int remainngWickets = 10-comp_wickets;
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Computer Won the Game!");

                    builder.setMessage("Computer won the game by " + remainngWickets + " Wickets! Better Luck Next Time :(");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }
                else if(comp_runs==user_runs && comp_wickets==10){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Match Tied!");
                    builder.setMessage("Match Tied! No One Wins!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }

                if (runNo==compRunNo) {
                    comp_wickets+=1;
                    Toast.makeText(this, "Out! Computer's "+comp_wickets+"th Wicket Gone!", Toast.LENGTH_SHORT).show();
                } else {
                    comp_runs += compRunNo;
                }
                    comp_balls += 1;
                    comp_number.setText(String.valueOf(compRunNo));
                    user_number.setText(String.valueOf(runNo));
                    score.setText("Computer's Score : " + String.valueOf(comp_runs) + "/" + comp_wickets + " (" + String.valueOf(comp_balls) + ")");

                    if (comp_runs > user_runs) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Computer Won the Game!");
                        builder.setMessage("Computer won the game! Better Luck Next Time :(");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                            }
                        });
                        builder.show();
                    } else {

                        int remainingRuns = user_runs - comp_runs + 1;
                        chase_text.setText("Computer needs " + remainingRuns + " runs to Win.");
                    }
            }
        }



        else{
            if (currentUserTurnBall.equals("ball")){
                int compRunNo = generateRandomNumber();

                if(compRunNo==runNo || compRunNo==5 && runNo==0) {
                    comp_wickets += 1;

                    if (comp_wickets==10){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Computer Out!");
                    builder.setMessage("Computer Out on " + comp_runs + "! Now it's time for chase !");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentUserTurnBall = "bat";
                            user_number.setText("0");
                            comp_number.setText("0");
                            score.setText("User's Score : 0");
                            comp_turn_text.setText("Computer's Bowling :");
                            user_turn_text.setText("User's Batting :");
                            int user_target = comp_runs + 1;
                            chase_text.setText("You needs " + user_target + " runs to Win.");

                        }
                    });
                    builder.show();
                }
                    else{
                        Toast.makeText(this, "Out! Computer's " + comp_wickets + "th Wicket Gone!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    comp_runs += compRunNo;
                }
                comp_balls += 1;

                user_number.setText(String.valueOf(runNo));
                comp_number.setText(String.valueOf(compRunNo));
                score.setText("Computer's Score : " + String.valueOf(comp_runs) + "/" + comp_wickets + " (" + String.valueOf(comp_balls) + ")");
            }
            else{
                int compRunNo = generateRandomNumber();

                if(user_wickets==10 && comp_runs>user_runs){
                    int remainingRuns = comp_runs-user_runs;
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Computer Won the Game!");
                    builder.setMessage("Computer Won the game by " + remainingRuns + " Runs!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }

                if(comp_runs<user_runs){
                    int remainingWickets = 10-user_wickets;
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("You Won the Game!");
                    builder.setMessage("You won the game by " + remainingWickets + "! Wow! What a win :)");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }
                else if(comp_runs==user_runs && compRunNo==runNo){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Match Tied!");
                    builder.setMessage("Match Tied! No One Wins!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Check Stats", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                        }
                    });
                    builder.show();
                }

                if (runNo==compRunNo) {
                    user_wickets+=1;
                    Toast.makeText(this, "Out! Your "+user_wickets+"th Wicket Gone!", Toast.LENGTH_SHORT).show();
                } else {
                    user_runs += runNo;
                }
                    user_balls += 1;
                    comp_number.setText(String.valueOf(compRunNo));
                    user_number.setText(String.valueOf(runNo));
                    score.setText("User's Score : " + String.valueOf(user_runs) + "/" + user_wickets + " (" + String.valueOf(user_balls) + ")");

                    if (comp_runs < user_runs) {
                        int remainingWickets = 10-user_wickets;
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("You Won the Game!");
                        builder.setMessage("You won the game by " + remainingWickets + " ! What a win! :)");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Go to Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setPositiveButton("Check Stats", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendDataStatsActivity(user_runs, user_balls, user_wickets, comp_runs, comp_balls, comp_wickets);
                            }
                        });
                        builder.show();
                    } else {

                        int remainingRuns = comp_runs - user_runs + 1;
                        chase_text.setText("You needs " + remainingRuns + " runs to Win.");
                    }

            }
        }
    }

}