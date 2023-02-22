/*
This activity defines the java class and the layout that display the splash screen when the app is started.
 */
package com.example.addu;

//Necessary imports to be made when the implementing gif splash screen

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class PopFirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_first_screen);

        //Handler method that allows to wait for a certain time.
        //After the waiting time is finished then this will start the activity named mapdecider.
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent openThePopUp = new Intent(PopFirstScreen.this,MapDecider.class);
                startActivity(openThePopUp);
                finish();
            }
        },4000);
    }
}