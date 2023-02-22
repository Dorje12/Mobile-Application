/*
When the user tries to delete their posts their post. They would get redirected to this
activities interface.
 */
package com.example.addu;

//All the necessary imports required for building the interface is listed here.

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;

public class DeleteValidation extends AppCompatActivity {

    //Required fields that will extract or set the data from or to the layout.
    TextView delPost;
    TextView complyDeny;
    ActionSqlConnect actionSqlConnect;
    String onBoardUserEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_validation);

        //Initializing the fields that will be able to access the attributes if the fields with the below mentioned id's
        //in the xml layout
        actionSqlConnect = new ActionSqlConnect(this);
        delPost = (TextView) findViewById(R.id.delThisPostOnCommand);
        complyDeny = (TextView) findViewById(R.id.comeBackToRedirect);

        //Variables in order to get the id of the posts made and the email of the user.
        String valOfTheReqId = getIntent().getStringExtra("idHolder");
        Cursor onBoardUserGetEmail = actionSqlConnect.obtainPostListCommentUserId(valOfTheReqId);
        if(onBoardUserGetEmail.moveToNext()){
            onBoardUserEmail = onBoardUserGetEmail.getString(3);
        }

        //WHen the delete button is clicked this method gets executed.
        delPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variable to hold the id of the post.
                String valOfTheReqId = getIntent().getStringExtra("idHolder");

                //Calling of the method that deletes the post of the given id.
                Boolean checkStatus = actionSqlConnect.statusGoneDeletePost(valOfTheReqId);
                if(checkStatus == true) {
                    Toast.makeText(DeleteValidation.this, "Success!", Toast.LENGTH_SHORT).show();

                    //if the post is deleted then the user gets redirected back to the feed class with the help of the intent.
                    Intent openTheRedirect = new Intent(DeleteValidation.this, com.example.addu.AfterLoginProfileFeed.class);
                    openTheRedirect.putExtra("onBoardUserEmail",onBoardUserEmail);
                    startActivity(openTheRedirect);
                }
                else{
                    Toast.makeText(DeleteValidation.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //If the no button is clicked the this method will get executed.
        complyDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //With the help of intent on the click of the no, user will get mapped back to the profile feed page.
                Intent openTheRedirect = new Intent(DeleteValidation.this, com.example.addu.AfterLoginProfileFeed.class);
                openTheRedirect.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(openTheRedirect);
            }
        });
    }
}