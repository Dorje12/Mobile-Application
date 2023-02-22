/*
This class is basically the class that is responsible for holding the interface that allows a user to
update or edit their posts.
 */
package com.example.addu;

//Necessary imports to be made to build the class.

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.google.android.material.textfield.TextInputEditText;

public class EditThePanePost extends AppCompatActivity {

    //Field and object declaration in order to access certain methods and attributes.
    Button saveTheContentButton;
    ActionSqlConnect sqlConnectLite;
    TextInputEditText contentStoreField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_the_pane_post);

        //Initializing the object ad fields in order to access certain methods and attributes.
        sqlConnectLite = new ActionSqlConnect(this);
        saveTheContentButton = (Button) findViewById(R.id.buttonSaveTheContent);
        contentStoreField = (TextInputEditText) findViewById(R.id.haveTheValPost);

        //Method calling.
        showTheStoreContent();

        //Action to be taken when the button save is clicked
        saveTheContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveShowContent();
            }
        });

    }
    //This method is implemented when the save button is clicked in the log of the user posts.
    public void saveShowContent() {

        //String variable declaration and initialization in order to store the user email and post id.
        String valOfTheReqId = getIntent().getStringExtra("idHolder");
        String obtainTheEditedContent = contentStoreField.getText().toString().trim();

        //Calling the method in the ActionSqlConnect class in order to saving the edited data.
        Boolean checkStatus = sqlConnectLite.saveChangeInDataPostsEdit(valOfTheReqId,obtainTheEditedContent);

        //Simple logic to distinguish if saving the edited content was successful or not.
        if(checkStatus == true) {
            Toast.makeText(EditThePanePost.this, "Success!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(EditThePanePost.this, "something went wrong.", Toast.LENGTH_SHORT).show();
        }

        //IF the comment is saved, Then the cursor below is used for getting the onBoard user email and it
        //is passed to the afterLoginProfileFeed class and that class is opened with the help of the intent.
        Cursor onBoardUserGetEmail = sqlConnectLite.obtainPostListCommentUserId(valOfTheReqId);
        String onBoardUserEmail = null;
        if(onBoardUserGetEmail.moveToNext()){
            onBoardUserEmail = onBoardUserGetEmail.getString(3);
        }
        Intent openTheRedirect = new Intent(EditThePanePost.this,AfterLoginProfileFeed.class);
        openTheRedirect.putExtra("onBoardUserEmail",onBoardUserEmail);
        startActivity(openTheRedirect);
    }

    //Method that stores and gets the content that was posted and needs to be edited.
    private void showTheStoreContent() {
        String valOfTheReqId = getIntent().getStringExtra("idHolder");
        Cursor openTheData = sqlConnectLite.obtainPostListCommentUserId(valOfTheReqId);
        String onBoardContent = null;
        if(openTheData.moveToNext()) {
            onBoardContent = openTheData.getString(1);
            contentStoreField.setText(onBoardContent);
        }
    }

}