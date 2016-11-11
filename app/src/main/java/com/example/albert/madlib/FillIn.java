package com.example.albert.madlib;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by albert on 8/11/2016.
 */

public class FillIn extends AppCompatActivity{

    Story madlib;
    String[] stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in);

        AssetManager madlibTXTs = this.getAssets();
        Random rand = new Random();
        for(int i = 0; i< 100; i++ ){
            int random = rand.nextInt(5);
            Log.d("Random_Test", "Value: " + Integer.toString(random));
        }
        madlib = getNextStory(madlibTXTs);
        setHint(madlib.getNextPlaceholder());
        setEditHint(madlib.getNextPlaceholder());
        wordsLeft(madlib.getPlaceholderRemainingCount());
    }

    public void collectWord(View view) {
        EditText nextWord = (EditText) findViewById(R.id.word_collec);
        String word = nextWord.getText().toString();
        if (word.length() > 0){
            String wordType = madlib.getNextPlaceholder();


            madlib.fillInPlaceholder(word);
            nextWord.getText().clear();
            setHint(wordType);
            setEditHint(wordType);
            wordsLeft(madlib.getPlaceholderRemainingCount());

            if (madlib.isFilledIn()){
                Intent storyIntent = new Intent(FillIn.this, Reader.class);
                storyIntent.putExtra("theStory", madlib.toString());
                FillIn.this.startActivity(storyIntent);
                finish();
            }
        }else{
            Toast noWord = Toast.makeText(getBaseContext(), "Please type a word and click ok!", Toast.LENGTH_SHORT);
            noWord.show();
        }
    }

    private void setHint(String wordType){
        Character start = wordType.charAt(0);
        TextView hint = (TextView) findViewById(R.id.textHint);
        if ("AEIOUaeoiu".indexOf(start) < 0) {
            hint.setText("Please enter a " + wordType);
        } else {
            hint.setText("Please enter an " + wordType);
        }
    }

    private void wordsLeft(int wordCount){
        TextView wordsLeft = (TextView) findViewById(R.id.wordsLeft);
        wordsLeft.setText(Integer.toString(wordCount) + " word(s) left");

    }
    private void setEditHint(String wordType){
        Character start = wordType.charAt(0);
        EditText hint = (EditText) findViewById(R.id.word_collec);
        hint.setHint(wordType);
    }

    private Story getNextStory(AssetManager files){
        Random rand = new Random();
        try {
            stories = files.list("stories");
            int random = rand.nextInt(stories.length);
            InputStream is = files.open("stories/" + stories[random]);
            madlib = new Story(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return madlib;


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putBoolean("MyBoolean", true);
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
    }

}
