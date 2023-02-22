/*
This activity holds the java class and layout that forms up the user profile.
All the information regarding the user with their profile details are displayed here.
 */
package com.example.addu;

//Necessary required imports used in the formation of the class.

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;

public class ViewInfo extends AppCompatActivity {

    //Declaration of the fields inorder to access the layout fields attributes.
    TextView changeMakerBtn;
    TextView redirectMapping;
    TextView onUserBoardName;
    TextView onUserNickName;
    TextView onUserBoardDob;
    TextView onUserBoardBio;
    TextView onUserBoardEmail;
    TextView onUserBoardEditDate;
    ActionSqlConnect sqlConnectLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        //object initialization for the ActionSqlConnect class and other fields initialization.
        sqlConnectLite = new ActionSqlConnect(this);
        changeMakerBtn = (TextView) findViewById(R.id.enableChangeMaker);
        redirectMapping = (TextView) findViewById(R.id.mapRedirect);

        onUserBoardName = (TextView) findViewById(R.id.displayOnBoardName) ;
        onUserNickName = (TextView) findViewById(R.id.displayNickName);
        onUserBoardDob = (TextView) findViewById(R.id.displayOnBoardDob);
        onUserBoardBio = (TextView) findViewById(R.id.displayOnBoardBio);
        onUserBoardEmail = (TextView) findViewById(R.id.displayOnBoardEmail);
        onUserBoardEditDate = (TextView) findViewById(R.id.displayOnBoardUpdate);

        //Method calling.
        showDisData();

        //method to be executed when the button edit profile is clicked.
        changeMakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variables declaration and initialization in order to store the details of the user.
                String onBoardUserName = onUserBoardName.getText().toString().trim();
                String onBoardUserNickName = onUserNickName.getText().toString().trim();
                String onBoardUserDob = onUserBoardDob.getText().toString().trim();
                String onBoardEmail = onUserBoardEmail.getText().toString().trim();
                String onBoardUserBio = onUserBoardBio.getText().toString().trim();
                String onBoardUserEditDate = onUserBoardEditDate.getText().toString().trim();
                String onBoardUserCurrentEmail = getIntent().getStringExtra("onBoardUserEmail");

                //Passing the values stored in above variables to the ChangeSavesProfile class and starting that
                //class with the help of the intent.
                Intent changeMakerEnabled = new Intent(ViewInfo.this,ChangeSavesProfile.class);
                changeMakerEnabled.putExtra("onBoardUserName",onBoardUserName);
                changeMakerEnabled.putExtra("onBoardUserNickName",onBoardUserNickName);
                changeMakerEnabled.putExtra("onBoardUserDob",onBoardUserDob);
                changeMakerEnabled.putExtra("onBoardEmail",onBoardEmail);
                changeMakerEnabled.putExtra("onBoardUserBio",onBoardUserBio);
                changeMakerEnabled.putExtra("onBoardUserEditDate",onBoardUserEditDate);
                changeMakerEnabled.putExtra("onBoardUserEmail",onBoardUserCurrentEmail);
                startActivity(changeMakerEnabled);
            }
        });

        //method to be implemented when the button back is pressed.
        redirectMapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When the button back is pressed the the intent open a new activity leading back towards the
                //afterLoginProfile page.
                String onBoardUserCurrentEmail = getIntent().getStringExtra("onBoardUserEmail");
                Intent changeRedirect = new Intent(ViewInfo.this, com.example.addu.AfterLoginProfile.class);
                changeRedirect.putExtra("onBoardUserEmail",onBoardUserCurrentEmail);
                startActivity(changeRedirect);
            }
        });
    }
    //Method in order to get the data directly from the database.
    public void showDisData() {

        //Storing the data passed by tge intent into a variable.
        String onBoardUserCurrentEmail = getIntent().getStringExtra("onBoardUserEmail");

        //calling the methods in the actionSqlConnect class in order to access the
        //user information by passing the user email as the argument.
        Cursor cursor = sqlConnectLite.getSpecificData(onBoardUserCurrentEmail);

        //Initializing string variables to null.
        String onBoardUserEmail = null;
        String onBoardUserFirstName = null;
        String onBoardUserLastName = null;
        String onBoardUserNickName = null;
        String onBoardUserBornYear = null;
        String onBoardUserBio = null;
        String onBoardUserEditDate = null;

        //Simple if logic that performs the action to store the required data into null variables as long as the
        //cursor in the database has the value.
        if(cursor.moveToNext()) {
            onBoardUserEmail = cursor.getString(0);
            onUserBoardEmail.setText(onBoardUserEmail);

            onBoardUserFirstName = cursor.getString(2);
            onBoardUserLastName = cursor.getString(3);
            onUserBoardName.setText(onBoardUserFirstName + " " + onBoardUserLastName);

            onBoardUserNickName = cursor.getString(4);
            onUserNickName.setText(onBoardUserNickName);

            onBoardUserBornYear = cursor.getString(5);
            onUserBoardDob.setText(onBoardUserBornYear);

            onBoardUserBio = cursor.getString(6);
            onUserBoardBio.setText(onBoardUserBio);

            onBoardUserEditDate = cursor.getString(7);
            onUserBoardEditDate.setText(onBoardUserEditDate);
        }
    }
}
