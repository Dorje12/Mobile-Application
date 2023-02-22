/*
This activity will be responsible for displaying the edit profile page to the user. USer would be edit
and save their changes through here.
 */
package com.example.addu;

//All the necessary imports required for building the interface is listed here.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;

import java.text.DateFormat;
import java.util.Calendar;

public class ChangeSavesProfile extends AppCompatActivity {

    //Required fields that will extract or set the data from or to the layout.
    TextView textBtnCancel;
    TextView textBtnSave;
    EditText changeOnNameEdit;
    EditText changeOnNickEdit;
    EditText changeOnDobEdit;
    EditText changeOnEmailEdit;
    EditText changeOnBioEdit;
    ActionSqlConnect actionSqlConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_saves_profile);

        //Initializing the fields that will be able to access the attributes if the fields with the below mentioned id's
        //in the xml layout.
        textBtnCancel = (TextView) findViewById(R.id.cancelTheEdit);
        textBtnSave = (TextView) findViewById(R.id.saveTheEdit);

        changeOnNameEdit = (EditText) findViewById(R.id.makeNameChange);
        changeOnNickEdit = (EditText) findViewById(R.id.makeNameChangeNick);
        changeOnDobEdit = (EditText) findViewById(R.id.makeDobChange);
        changeOnEmailEdit = (EditText) findViewById(R.id.makeEmailChange);
        changeOnBioEdit = (EditText) findViewById(R.id.makeBioChange);

        //String variables that would store the data that has been passed as the intent.
        String nameEditOriginal = getIntent().getStringExtra("onBoardUserName");
        String nickEditOriginal = getIntent().getStringExtra("onBoardUserNickName");
        String dobEditOriginal = getIntent().getStringExtra("onBoardUserDob");
        String emailEditOriginal = getIntent().getStringExtra("onBoardEmail");
        String bioEditOriginal = getIntent().getStringExtra("onBoardUserBio");

        //Setting the stored data into the fields.
        changeOnNameEdit.setText(nameEditOriginal);
        changeOnNickEdit.setText(nickEditOriginal);
        changeOnDobEdit.setText(dobEditOriginal);
        changeOnEmailEdit.setText(emailEditOriginal);
        changeOnBioEdit.setText(bioEditOriginal);

        changeOnEmailEdit.setEnabled(false);
        actionSqlConnect = new ActionSqlConnect(this);

        //when the cancel button is clicked then this method is implemented.
        textBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent lets the user to board to the ViewInfo page.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent noEventChange = new Intent(ChangeSavesProfile.this, com.example.addu.ViewInfo.class);
                noEventChange.putExtra("onBoardUserEmail",onBoardUserEmail);
                startActivity(noEventChange);
            }
        });

        //method to be implemented when the user clicks the save button.
        textBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String variables to hold the intent data and the data in the edit text.
                String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");

                String editedDob = changeOnDobEdit.getText().toString().trim();
                String nickName = changeOnNickEdit.getText().toString().trim();
                String editName = changeOnNameEdit.getText().toString().trim();
                String editBio = changeOnBioEdit.getText().toString().trim();

                //Calendar instance in order to provide the date that the profile was edited.
                Calendar obtainDateCurrent = Calendar.getInstance();
                String editDate = DateFormat.getDateInstance(DateFormat.SHORT).format(obtainDateCurrent.getTime());

                //Calling the method in the ActionSqlConnect class inorder to update the user profile based in their email.
                Boolean checkStatus = actionSqlConnect.saveChangeInUserProfile(onBoardUserEmail,editName,nickName,editedDob,editBio,editDate);

                //If the edit is success then this code will get executed.
                if(checkStatus == true) {
                    Toast.makeText(ChangeSavesProfile.this, "Success!", Toast.LENGTH_SHORT).show();
                }
                //If the edit is not success then this code will get executed.
                else{
                    Toast.makeText(ChangeSavesProfile.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                }

                //After this work of editing is done in order to map back to the profile page this intent helps us.
                Intent someEventChange = new Intent(ChangeSavesProfile.this, com.example.addu.AfterLoginProfile.class);
                someEventChange.putExtra("onBoardUserEmail",onBoardUserEmail);
                someEventChange.putExtra("onBoardUserBio",editBio);
                startActivity(someEventChange);
            }
        });
    }
}