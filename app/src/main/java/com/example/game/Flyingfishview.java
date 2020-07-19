package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Flyingfishview extends View {

    private Paint scorepaint=new Paint();
    private Bitmap background;
    private int fishX=10;
    private int fishY;
    int score;
    private Intent service;
    MediaPlayer mediaPlayer;
    DatabaseHelper databaseHelper;
    private int lifecounter=3;
    private int YellowX,YellowY,Yellowspeed=5;
    private  Paint yellowpaint=new Paint();
    private int GreenX,GreenY,Greenspeed=5;
    private  Paint Greenpaint=new Paint();
    private int redY,redX,redspeed=5;
    private  Paint redpaint=new Paint();
    private int fishspeed;
        SharedPreferences sharedPreferences;
    private int canvasheight,canvaswidth;

private boolean touch=false;

    private Bitmap life[]=new Bitmap[2];
    private Bitmap fish[]=new Bitmap[2];
    public Flyingfishview(Context context) {
        super(context);
        databaseHelper=new DatabaseHelper(getContext());
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        background=BitmapFactory.decodeResource(getResources(),R.drawable.splash);
        scorepaint.setColor(Color.WHITE);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);
        scorepaint.setTextSize(70);
        yellowpaint.setColor(Color.YELLOW);
        service=new Intent(getContext(),myservice.class);
        getContext().startService(new Intent(getContext(),myservice.class));
        sharedPreferences=getContext().getSharedPreferences("scorerr",0) ;
        //SharedPreferences.Editor editor=sharedPreferences.edit();
        //editor.putInt("score",score);
        Greenpaint.setAntiAlias(false);
        Greenpaint.setColor(Color.GREEN);
        yellowpaint.setAntiAlias(false);
        redpaint.setColor(Color.RED);
        redpaint.setAntiAlias(false);
        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
fishY=550;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasheight=canvas.getHeight();
        canvaswidth=canvas.getWidth();

        canvas.drawBitmap(background,0,0,null);
    ContentValues contentValues=new ContentValues();
    contentValues.put("score",score);

        sharedPreferences=getContext().getSharedPreferences("score",0) ;
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putInt("score",score);
      int minfishY=fish[0].getHeight();
int maxfishY=canvasheight-fish[0].getHeight()*3;
fishY=fishY+fishspeed;
if (fishY<minfishY){
    fishY=minfishY;
}
fishspeed=fishspeed+2;
        if (fishY>maxfishY){
            fishY=maxfishY;
        }
        if(touch){
canvas.drawBitmap(fish[1],fishX,fishY,null);
touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);

        }


       canvas.drawCircle(YellowX,YellowY,25,yellowpaint);
        YellowX=YellowX-Yellowspeed;
        if(YellowX<0){
            YellowX=canvaswidth+21;


            YellowY=(int)Math.floor(Math.random()*(maxfishY-minfishY)+minfishY);

        }
        GreenX=GreenX-Greenspeed;
        if(GreenX<0){
            GreenX=canvaswidth+21;
            GreenY=(int)Math.floor(Math.random()*(maxfishY-minfishY)+minfishY);
            
        }
        if(hitball(GreenX,GreenY)){
            score=score+20;
            GreenX=-100;
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.bell);
            mediaPlayer.start();

            Greenspeed=Greenspeed+0;
        }
        redX=redX-redspeed;
        if(redX<0){
            redX=canvaswidth+21;
            redY=(int)Math.floor(Math.random()*(maxfishY-minfishY)+minfishY);

        }
        if(hitball(redX,redY)) {
            lifecounter--;
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.faling);
            mediaPlayer.start();
            if (lifecounter == 0) {

                mediaPlayer=MediaPlayer.create(getContext(),R.raw.faling);
                mediaPlayer.start();
                databaseHelper.inserscore(contentValues);
                Toast.makeText(getContext(), "gameOver", Toast.LENGTH_SHORT).show();
                getContext().startActivity(new Intent(getContext(),GameOver.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra("score",score)
                );

            }
            redX = -100;
        }

        canvas.drawCircle(GreenX,GreenY,25,Greenpaint);
        canvas.drawCircle(redX,redY,25,redpaint);

        canvas.drawText("Score:"+score,10,60,scorepaint);

        for(int i=0;i<3;i++){
            int x=(int)(400+life[0].getWidth()*1.5*i);
            int y=30;
            if(i <lifecounter){
                canvas.drawBitmap(life[0],x,y,null);
            }else {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

if(hitball(YellowX,YellowY)){
    score=score+10;
    YellowX=-100;

    Yellowspeed=Yellowspeed+0;
    mediaPlayer=MediaPlayer.create(getContext(),R.raw.bell);
    mediaPlayer.start();
}
}

public boolean hitball(int x, int y){
if(fishX< x && x <(fishX +fish[0].getWidth() )&& fishY< y && y <(fishY +fish[0].getHeight())){
    mediaPlayer=MediaPlayer.create(getContext(),R.raw.faling);
    mediaPlayer.start();
return true;
}
return false;
}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            touch=true;
            fishspeed=-22;

        }
        return true;
    }


}

