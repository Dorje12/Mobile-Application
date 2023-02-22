/*
This activity contains the class that is responsible for deleting the user.
Only admin is able access this page and they would be able to delete the user along with their posts.
 */
package com.example.addu;

//All the necessary imports required for building the interface is listed here.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;

public class DeleteUserVal extends AppCompatActivity {

    //Required fields that will extract or set the data from or to the layout.
    TextView delPost;
    TextView complyDeny;
    ActionSqlConnect actionSqlConnect;
    String onBoardUserEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_val);

        //Initializing the fields that will be able to access the attributes if the fields with the below mentioned id's
        //in the xml layout
        actionSqlConnect = new ActionSqlConnect(this);
        delPost = (TextView) findViewById(R.id.delThisPostOnCommand);
        complyDeny = (TextView) findViewById(R.id.comeBackToRedirect);
        onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");

        //method to be implemented when the admin clicks delete button
        delPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valOfTheReqId = getIntent().getStringExtra("emailHolder");

                //On the click of the delete button by the admin the method to delete all the posts of the user
                //and the user itself in the ActionSqlConnect class is called.
                Boolean checkDel = actionSqlConnect.statusGoneDeleteUserEmail(valOfTheReqId);
                Boolean checkStatus = actionSqlConnect.statusGoneDeleteUser(valOfTheReqId);

                //simple if else logic statements to be executed as per the result above.
                if(checkStatus == true) {
                    Toast.makeText(DeleteUserVal.this, "Success!", Toast.LENGTH_SHORT).show();

                    //if the user has been deleted then the admin is mapped back to the profile page.
                    Intent openTheRedirect = new Intent(DeleteUserVal.this, com.example.addu.AfterLoginProfile.class);
                    openTheRedirect.putExtra("onBoardUserEmail",onBoardUserEmail);
                    startActivity(openTheRedirect);
                }
                else{
                    Toast.makeText(DeleteUserVal.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}