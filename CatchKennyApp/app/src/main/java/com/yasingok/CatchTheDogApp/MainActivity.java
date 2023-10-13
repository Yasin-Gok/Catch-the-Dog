package com.yasingok.CatchTheDogApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yasingok.catchkennyapp.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timetext, scoretext, gamenameText;
    RadioGroup radioGroup;
    ImageView imageView;
    Handler handler;
    Runnable run;
    Button start_button, exit_button, leaderboard;
    int score, randomX, randomY, zorluk_sure, zorluk_hiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timetext = findViewById(R.id.TimerText);
        scoretext = findViewById(R.id.ScoreText);
        imageView = findViewById(R.id.imageView);
        radioGroup = findViewById(R.id.radioGroup);
        start_button = findViewById(R.id.Start);
        gamenameText = findViewById(R.id.gameNameText);
        exit_button = findViewById(R.id.Exit_btn);
        leaderboard = findViewById(R.id.leaderboard_btn);
        score = 0;

        imageView.setVisibility(View.INVISIBLE);
        start_button.setVisibility(View.INVISIBLE);
        timetext.setVisibility(View.INVISIBLE);
        scoretext.setVisibility(View.INVISIBLE);
        exit_button.setVisibility(View.INVISIBLE);
        leaderboard.setVisibility(View.INVISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                start_button.setVisibility(View.VISIBLE);
                if(checkedId == R.id.Easy){zorluk_hiz = 1000; zorluk_sure = 30000;}
                else if (checkedId == R.id.Medium) {zorluk_hiz = 750; zorluk_sure = 20000;}
                else{ zorluk_hiz = 500; zorluk_sure = 10000;}
            }
        });
    }

    public void increase_score(View view){
        score++;
        scoretext.setText("Score: " + score);
    }

    public void generateNumber(){
        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
                randomX = new Random().nextInt(720 - 0) + 0;
                randomY = new Random().nextInt(720 - 0) + 0;
                handler.postDelayed(this, zorluk_hiz);
                imageView.setX(randomX);
                imageView.setY(randomY);
            }
        };
        handler.post(run);
    }

    public void start(View view){
        radioGroup.setVisibility(View.INVISIBLE);
        start_button.setVisibility(View.INVISIBLE);
        timetext.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        scoretext.setVisibility(View.VISIBLE);
        gamenameText.setVisibility(View.INVISIBLE);
        generateNumber();

        new CountDownTimer(zorluk_sure, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timetext.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish()
            {
                timetext.setText("Time's off");
                imageView.setVisibility(View.INVISIBLE);

                AlertDialog.Builder alarm = new AlertDialog.Builder(MainActivity.this);
                alarm.setTitle("Restart?");
                alarm.setMessage("Are you sure?");

                alarm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scoretext.setText("Your score: " + score);
                        leaderboard.setVisibility(View.VISIBLE);
                        exit_button.setVisibility(View.VISIBLE);
                        //send_data(score, zorluk_sure);
                    }
                });
                alarm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        send_data(score, zorluk_sure);
                        Intent intent = getIntent();    // Aktiviteyi getintent ile alıyoruz
                        finish();                       // Öncekini kapatıyoruz
                        startActivity(intent);          // Aktiviteyi başlatıyoruz
                    }
                });
                alarm.show();
            }
        }.start();
    }

    public void exit(View view){
        finish();
    }

    public void change_screen(View view){
        Intent intent = new Intent(this, LeaderBoard.class);
        //send_data(score, zorluk_sure);
        startActivity(intent);
    }

    public void send_data(int point, int zorluk){
        Intent intent = new Intent(this, LeaderBoard.class);

        // Zorluk seviyesine göre puanı ekleyin
        if (zorluk == 30000) {intent.putExtra("zorluk_key", "easy");}
        else if (zorluk == 20000) {intent.putExtra("zorluk_key", "medium");}
        else if (zorluk == 10000){intent.putExtra("zorluk_key", "hard");}

        intent.putExtra("point_key", String.valueOf(point));
        startActivity(intent);
    }
}