package com.example.albert.madlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by albert on 8/11/2016.
 */

public class Reader extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Bundle extras = getIntent().getExtras();

        String story = extras.getString("theStory");
        TextView storyView = (TextView) findViewById(R.id.story);
        storyView.setText(story);
    }

    public void onRestart(View view){
        Intent restart = new Intent(Reader.this, FillIn.class);
        startActivity(restart);
        finish();
    }
}
