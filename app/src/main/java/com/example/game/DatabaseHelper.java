package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
static String name="Game";
static int version=1;
String usertable="CREATE TABLE  if not exists `userscore`(\n" +
        "\t`score`\tINTEGER\n" +
        ");";
    public DatabaseHelper( Context context) {
        super(context, name,null, version);
        getWritableDatabase().execSQL(usertable);
    }
public void inserscore(ContentValues contentValues){
        getWritableDatabase().insert("userscore","",contentValues);
}
public  ArrayList<highest> highests(){
        String sql="SELECT MAX(score) AS Largestscore From userscore";
    Cursor cursor=getReadableDatabase().rawQuery(sql,null);

    ArrayList<highest> helo=new ArrayList<>();
    while ( cursor.moveToFirst()){


        highest hi=new highest();
            hi.setScore(cursor.getString(cursor.getColumnIndex("score")));
        helo.add(hi);
    }


cursor.close();
    return helo;
}
    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL(usertable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
