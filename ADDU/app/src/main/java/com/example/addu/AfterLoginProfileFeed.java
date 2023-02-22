/*
This activity hold the class and layout that makes up the interface for the user logs where all the
user past made comments are listed. User would be able to make edit and delete the post here.
 */
package com.example.addu;

//Necessary import to be made are listed here.

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.example.addu.db.connectivity.ShowTheLogView;

import java.util.ArrayList;

public class AfterLoginProfileFeed extends AppCompatActivity {

    //Declaration of necessary fields, objects and variables are made here.
    RecyclerView contentLogView;
    ArrayList<String> obtainIdVal,obtainContentVal,obtainDateVal,obtainEmailVal;
    ActionSqlConnect actionSqlConnect;
    String getTheOnBoardEmail = null;
    ShowTheLogView showTheLogView;
    ImageView mapRedirectUseProfile;
    ImageView mapRedirectTheHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_profile_feed);

        //Fields are initialized with respect to the id so that we would be able to access the data
        //that the field holds in the layout.
        mapRedirectUseProfile = (ImageView) findViewById(R.id.mapRedirectUseProfile);
        mapRedirectTheHome = (ImageView) findViewById(R.id.mapRedirectTheHome);

        //Initializing the new array along with defining the object for ActionSqlConnect class.
        contentLogView =findViewById(R.id.showTheLogContent);
        actionSqlConnect = new ActionSqlConnect(this);
        obtainIdVal = new ArrayList<>();
        obtainContentVal = new ArrayList<>();
        obtainDateVal = new ArrayList<>();
        obtainEmailVal = new ArrayList<>();
        showTheLogView = new ShowTheLogView(this,obtainIdVal,obtainContentVal,obtainDateVal,obtainEmailVal);

        //Setting the view for the recycler view in the layout.
        contentLogView.setAdapter(showTheLogView);
        contentLogView.setLayoutManager(new LinearLayoutManager(this));

        //Calling this method.
        finalDataListView();

        //method to be implemented when the profile button in the menu is clicked.
        mapRedirectUseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Profile activity is started by passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent mapRedirectProfile = new Intent(AfterLoginProfileFeed.this, com.example.addu.AfterLoginProfile.class);
                mapRedirectProfile.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(mapRedirectProfile);
            }
        });

        //method to be implemented when the home button in the menu bar is clicked.
        mapRedirectTheHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variable initialization and starting the AfterLoginHome Class when the button is clicked
                //as well as passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent mapRedirectProfile = new Intent(AfterLoginProfileFeed.this, com.example.addu.AfterLoginHome.class);
                mapRedirectProfile.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(mapRedirectProfile);
            }
        });
    }

    //Method that gets the access to all the lists of the comments
    //That the onBoard user has made.
    private void finalDataListView() {
        //String variable in order to hold the email of the current board user.
        String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");

        //Cursor variable calling the method that lists the posts that the user with
        //the provided email has made.
        Cursor obtainReqVal = actionSqlConnect.obtainPostListCommentUser(onBoardUserEmail);

        //Simple if else logic to determine of the user has made any posts or not.
        if(obtainReqVal.getCount() == 0) {
            Toast.makeText(this, "There has been no post made.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            //if the user has made a post the Arrays declared above would be inserted data as long as the
            //posts does not finishes.
            while (obtainReqVal.moveToNext()){
                obtainIdVal.add(obtainReqVal.getString(0));
                obtainContentVal.add(obtainReqVal.getString(1));
                obtainDateVal.add(obtainReqVal.getString(2));
                getTheOnBoardEmail = obtainReqVal.getString(3);
                finalNameDataView();
            }
        }
    }

    //Method to get the nickname of the user that posted the comment is obtained through here.
    private void finalNameDataView() {
        Cursor obtainReqNickName = actionSqlConnect.obtainNickName(getTheOnBoardEmail);
        if(obtainReqNickName.getCount() == 0) {
            Toast.makeText(this, "There has been no post made.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while (obtainReqNickName.moveToNext()){
                obtainEmailVal.add(obtainReqNickName.getString(4));
            }
        }
    }
}