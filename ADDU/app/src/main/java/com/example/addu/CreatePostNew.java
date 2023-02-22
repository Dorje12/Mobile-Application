/*
This activity is very much responsible to create a new post. User would be able to enter the comment
they want to post here.
 */
package com.example.addu;

//All the necessary imports required for building the interface is listed here.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class CreatePostNew extends AppCompatActivity {

    //Required fields that will extract or set the data from or to the layout.
    TextInputEditText writeTheCon;
    Button push;
    ActionSqlConnect actionSqlConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_new);

        //Initializing the fields that will be able to access the attributes if the fields with the below mentioned id's
        //in the xml layout
        push = (Button) findViewById(R.id.btnSmart);
        writeTheCon = (TextInputEditText) findViewById(R.id.writeHere);
        actionSqlConnect = new ActionSqlConnect(this);

        //String variable to hold the content displayed over the screen.
        String getCon = writeTheCon.getText().toString().trim();

        //When the post Button is clicked this methods gets implemented.
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createThis();
            }
        });
    }

    //method executed when a post is made.
    public void createThis() {

        //Variables to hold the email and content.
        String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
        String getCon = writeTheCon.getText().toString().trim();

        //Calendar instance to record the date that the post was made.
        Calendar obtainDateCurrent = Calendar.getInstance();
        String provideToView = DateFormat.getDateInstance(DateFormat.SHORT).format(obtainDateCurrent.getTime());

        //Calling method form the ActionSqlConnect class in order to create a new post.
        Boolean status = actionSqlConnect.saveChangeInDataPosts(getCon,provideToView,onBoardUserEmail);

        //simple if else logic statements to be executed as per the result above.
        if(status == true) {
            Toast.makeText(this, "ok done", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "oh no not done.", Toast.LENGTH_SHORT).show();
        }

        //After a post is made the intent passes the email and maps user to the home page.
        Intent mapBackRedirect = new Intent(CreatePostNew.this, com.example.addu.AfterLoginHome.class);
        mapBackRedirect.putExtra("onBoardUserEmail",onBoardUserEmail);
        startActivity(mapBackRedirect);
    }
}