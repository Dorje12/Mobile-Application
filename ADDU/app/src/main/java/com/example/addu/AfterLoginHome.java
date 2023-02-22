/*
This java class hold the interface that a user is mapped towards when clicked the home button icon
in the menu. The display of all the posts made in th application by all the users will be visible here.
And The user will also be able to post a comment through here.
 */
package com.example.addu;

//Necessary import to be made are listed here.

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.addu.db.connectivity.ActionSqlConnect;
import com.example.addu.db.connectivity.ShowTheView;
import java.util.ArrayList;
public class AfterLoginHome extends AppCompatActivity {
    //Declaration of necessary fields, objects and variables are made here.
    Button makePost;
    RecyclerView obtainToBeShown;
    ArrayList<String> obtainContentVal,obtainDateVal,obtainEmailVal;
    ActionSqlConnect actionSqlConnect;
    String getTheOnBoardEmail = null;
    ShowTheView showTheView;
    ImageView mapRedirectProfile;
    ImageView mapRedirectFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_home);

        //Fields are initialized with respect to the id so that we would be able to access the data
        //that the field holds in the layout.
        makePost = (Button) findViewById(R.id.makePostComment);
        mapRedirectProfile = (ImageView) findViewById(R.id.mapBackRedirectProfile);
        mapRedirectFeed = (ImageView) findViewById(R.id.mapRedirectFeed);

        //Initializing the new array along with defining the object for ActionSqlConnect class.
        actionSqlConnect = new ActionSqlConnect(this);
        obtainContentVal = new ArrayList<>();
        obtainDateVal = new ArrayList<>();
        obtainEmailVal = new ArrayList<>();
        obtainToBeShown = findViewById(R.id.showTheObtained);

        //Setting the view for the recycler view in the layout.
        showTheView = new ShowTheView(this,obtainContentVal,obtainDateVal,obtainEmailVal);
        obtainToBeShown.setAdapter(showTheView);
        obtainToBeShown.setLayoutManager(new LinearLayoutManager(this));

        //Calling this method.
        finalDataListView();

        //method to be executed when the make new post button is clicked.
        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When the button is clicked then a new activity is opened with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent makingNew = new Intent(AfterLoginHome.this,CreatePostNew.class);
                makingNew.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(makingNew);
            }
        });

        //method to be implemented when the log button in the menu is clicked.
        mapRedirectFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Profile feed activity is started by passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent makingNew = new Intent(AfterLoginHome.this,AfterLoginProfileFeed.class);
                makingNew.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(makingNew);
            }
        });

        //method to be implemented when the profile button in the menu is clicked.
        mapRedirectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Profile activity is started by passing the email with the help of the intent.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent makingNew = new Intent(AfterLoginHome.this, com.example.addu.AfterLoginProfile.class);
                makingNew.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(makingNew);
            }
        });
    }

    //Method that lists the posts that has been made in the application.
    private void finalDataListView() {

        //Cursor variable calling the methods from the ActionSqlConnect in order to
        //store the lists of the posts and display them.
        Cursor obtainReqVal = actionSqlConnect.obtainPostListComment();

        //Simple if else logic to check whether any posts has been made or not.
        if(obtainReqVal.getCount() == 0) {
            Toast.makeText(this, "There has been no post made.", Toast.LENGTH_SHORT).show();
            return;
        }
        //If the post has been made. The fields in the layout is assigned the value.
        else{
            while (obtainReqVal.moveToNext()){
                obtainContentVal.add(obtainReqVal.getString(1));
                obtainDateVal.add(obtainReqVal.getString(2));
                getTheOnBoardEmail = obtainReqVal.getString(3);
                finalNameDataView();
            }
        }
    }

    //This method is used for obtaining the nickname of the user that posted the comment.
    private void finalNameDataView() {
        //Cursor variable calling the methods from the ActionSqlConnect in order to
        //store the nickname of the user that posted the comment on the basis of their email.
        Cursor obtainReqNickName = actionSqlConnect.obtainNickName(getTheOnBoardEmail);

        //Simple if else logic to check whether any posts has been made or not.
        if(obtainReqNickName.getCount() == 0) {
            Toast.makeText(this, "There has been no post made.", Toast.LENGTH_SHORT).show();
            return;
        }
        //If the post has been made. The fields in the layout is assigned the value of the nickname of the user.
        else{
            while (obtainReqNickName.moveToNext()){
                obtainEmailVal.add(obtainReqNickName.getString(4));
            }
        }
    }
}