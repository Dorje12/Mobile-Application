/*
This java class holds the interface where user is first mapped to. After the completion of the splash
screen user would be able to access this page.
 */
package com.example.addu;

//Necessary imports for fulfilling the class.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MapDecider extends AppCompatActivity {

    //Fields Declaration for accessing the attributes from the layout.
    TextView logMapper;
    TextView signMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_decider);

        //Declared fields are initialized here with id so that we would be able to access the required information.
        logMapper = (TextView) findViewById(R.id.mapperLog);
        signMapper = (TextView) findViewById(R.id.mapperSign);

        //When the login button is clicked then this method will get implemented and map us to login page.
        logMapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent apperanceLog = new Intent(MapDecider.this,MainActivity.class);
                startActivity(apperanceLog);
            }
        });

        //When the signup button is clicked then this method will get implemented and map us to signup page.
        signMapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent apperanceSign = new Intent(MapDecider.this,GetTallied.class);
                startActivity(apperanceSign);
            }
        });
    }
}