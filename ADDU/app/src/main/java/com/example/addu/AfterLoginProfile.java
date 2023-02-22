/*
This is the page that the user gets mapped to the first when they login the app.
The user would be able to access the information on them and log out through this activity.
 */
package com.example.addu;

//listed imports required to build the activity

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;

public class AfterLoginProfile extends AppCompatActivity {

    //Declaration of necessary fields, objects and variables are made here.
    TextView detailsProfile;
    TextView changeDisName;
    TextView bioHolder;
    TextView changeDisBorn;
    TextView changeDisUserName;
    TextView listOutUsers;
    ActionSqlConnect sqlConnectLite;
    ImageView outLogAct;
    ImageView blabla;
    ImageView feedLogAct;

    SharedPreferences storeDetails;
    SharedPreferences.Editor makeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_profile);

        // defining the object for ActionSqlConnect class.
        sqlConnectLite = new ActionSqlConnect(this);

        //Fields are initialized with respect to the id so that we would be able to access the data
        //that the field holds in the layout.
        outLogAct = (ImageView) findViewById(R.id.logActOut);
        bioHolder = (TextView) findViewById(R.id.bioHolder);
        detailsProfile = (TextView) findViewById(R.id.infoProfile);
        changeDisName = (TextView) findViewById(R.id.disCurrentName) ;
        changeDisBorn = (TextView) findViewById(R.id.disCurrentBorn);
        changeDisUserName = (TextView) findViewById(R.id.disCurrentUserName);
        blabla = (ImageView) findViewById(R.id.blabla);
        listOutUsers = (TextView) findViewById(R.id.listUserViewer);
        feedLogAct = (ImageView) findViewById(R.id.logFeedAct);

        //Calling this method
        showDisData();

        //Storing the user login details in the temporary storage.
        storeDetails = getSharedPreferences("OnBoardUser",MODE_PRIVATE);
        makeEdit = storeDetails.edit();

        //String to hold the email addresses inorder to compare them logically.
        String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
        String adminEmail = "admin@gmail.com";

        //simple if logic in order to verify if the user is the admin or not.
        //If the user is admin then the list if the users would be visible to admin.
        if(onBoardUserEmail.equals(adminEmail)){
            listOutUsers.setVisibility(View.VISIBLE);
        }

        //method to be implemented when the log button in the menu is clicked.
        feedLogAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Profile feed activity is started by passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent openDetailProfileFeed = new Intent(AfterLoginProfile.this,AfterLoginProfileFeed.class);
                openDetailProfileFeed.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(openDetailProfileFeed);
            }
        });

        //method to be executed when the view more button is clicked.
        detailsProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variable initialization and starting the ViewInfo Class when the button is clicked
                //as well as passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                String onBoardUserBio = getIntent().getStringExtra("onBoardUserBio");
                Intent openDetailProfile = new Intent(AfterLoginProfile.this,ViewInfo.class);
                openDetailProfile.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(openDetailProfile);
            }
        });

        //method to be implemented when the home button in the menu bar is clicked.
        blabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variable initialization and starting the AfterLoginHome Class when the button is clicked
                //as well as passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent openDetailProfile = new Intent(AfterLoginProfile.this,AfterLoginHome.class);
                openDetailProfile.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(openDetailProfile);
            }
        });

        //action to be used when the logout button in the menu bar is clicked.
        outLogAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOut();
            }
        });

        //this method only gets executed if the user is admin and if the admin clicks the user lists
        //button in order to view the lists of the user.
        listOutUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //With the help of the intent admin is mapped to the class that holds the users lists.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent openTheListsOfUser = new Intent(AfterLoginProfile.this,AdminUserInterface.class);
                openTheListsOfUser.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(openTheListsOfUser);
            }
        });
    }

    //this method runs when the activity is started.
    public void showDisData() {

        //String variable inorder to hold the current user email.
        String onBoardUserCurrentEmail = getIntent().getStringExtra("onBoardUserEmail");

        //Cursor variable in order to call the method in the ActionSqlConnect class and store the result.
        Cursor cursor = sqlConnectLite.getSpecificData(onBoardUserCurrentEmail);

        //Null initialization of the String variables.
        String onBoardUserFirstName = null;
        String onBoardUserLastName = null;
        String onBoardUserNickName = null;
        String onBoardUserBornYear = null;
        String onBoardUserBio = null;

        //if logic stating that if the user is available with the given email do following.
        if(cursor.moveToNext()) {

            //Setting the values from the databases into the fields and the string null variables.
            onBoardUserFirstName = cursor.getString(2);
            onBoardUserLastName = cursor.getString(3);
            changeDisUserName.setText(onBoardUserFirstName + " " + onBoardUserLastName);

            onBoardUserNickName = cursor.getString(4);
            changeDisName.setText(onBoardUserNickName);

            onBoardUserBornYear = cursor.getString(5);
            changeDisBorn.setText(onBoardUserBornYear);

            onBoardUserBio = cursor.getString(6);
            bioHolder.setText(onBoardUserBio);
        }
    }

    //when the logout button is clicked this method gets in,lemented
    public void getOut() {
        //Data hold by the sharedPreferences is made empty. And user is forced out of the system into the login page.
        makeEdit.clear();
        makeEdit.apply();
        Intent onLogActOut = new Intent(AfterLoginProfile.this,MainActivity.class);
        startActivity(onLogActOut);
        finish();
    }
}