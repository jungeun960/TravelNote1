package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_resultActivity extends AppCompatActivity {

    int highScore;
    ImageView medal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        TextView highscoreLabel = (TextView)findViewById(R.id.highScoreLabel);
        TextView gamesPlayedLabel = (TextView)findViewById(R.id.gamesPlayedLabel);
        medal = (ImageView)findViewById(R.id.medal);

        int score = getIntent().getIntExtra("score",0);
        scoreLabel.setText(""+score);

        if(score < 10){
            medal.setImageResource(R.drawable.bronze);
        }else if(score>=30){
            medal.setImageResource(R.drawable.gold);
        }else{
            medal.setImageResource(R.drawable.silver);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("highscore", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("highscore",0);

        if(score > highScore){
            highscoreLabel.setText("최고점 : "+score);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highscore",score);
            editor.commit();
        }else {
            highscoreLabel.setText("최고점 : "+highScore);
        }

        SharedPreferences preferences = getSharedPreferences("Games",Context.MODE_PRIVATE);
        int games = preferences.getInt("Games",0);
        gamesPlayedLabel.setText("Games Played : " +(games+1));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Games", (games+1));
        editor.commit();
    }

    public  void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), Game_startActivity.class));
        finish();
    }

    public  void gohome(View view){
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
    }

    //disable return button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case  KeyEvent.KEYCODE_BACK:
                    return  true;
            }
        }
        return  super.dispatchKeyEvent(event);
    }
}
