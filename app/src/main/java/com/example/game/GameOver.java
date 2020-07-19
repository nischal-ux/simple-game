package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOver extends AppCompatActivity {
    Button play;
    String id;
        TextView score;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //databaseHelper=new DatabaseHelper(this);
        setContentView(R.layout.activity_game_over);
//      ArrayList<highest> hi=databaseHelper.highests();
//      for (int i=0;i<hi.size();i++){
//        highest he=hi.get(i);
//          score.setText(he.getScore());
//      }
      score=findViewById(R.id.score);
//        id = getIntent().getStringExtra("score");
//
        score.setText(id);
        score.setTextColor(Color.BLACK);
        play=findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this,MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK)
                );
            }
        });
    }
}
