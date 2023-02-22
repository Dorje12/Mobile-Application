/*
This java class holds one of the major key concept for the entire project. This class is responsible for registering
a user into the system.
 */
package com.example.addu;

//All the required necessary imports are written here.

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class FinalTally extends AppCompatActivity {

    //Required fields in order to access the attributes from the layout are declared here.
    Button logReverseMap;
    TextInputEditText dbPushEmail;
    TextInputEditText dbPushPassword;
    ActionSqlConnect sqlConnectLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_tally);

        //declared fields are initialized here so that we would be able to access the attributes of the
        //fields in the layout that has the id given below.
        logReverseMap = (Button) findViewById(R.id.mapReverseLog);
        sqlConnectLite = new ActionSqlConnect(this);
        dbPushEmail = (TextInputEditText) findViewById(R.id.dbSaveEmail);
        dbPushPassword = (TextInputEditText) findViewById(R.id.dbSavePass);

        //method that will be executed when the button finish will be executed.
        logReverseMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The method that will save the data into the database.
                pushDetails();
            }
        });
    }

    //Not return method executed when the finish button is clicked.
    private void pushDetails() {

        //String variables that will hold all the data passed through the intent and get
        //all the data texts from the fields in the layout.
        String firstObtainName = getIntent().getStringExtra("dbFirstName");
        String lastObtainName = getIntent().getStringExtra("dbLastName");
        String dobObtainName = getIntent().getStringExtra("sendDobPass");
        String nickObtainName = getIntent().getStringExtra("sendNickName");
        String emailObtain = dbPushEmail.getText().toString().trim();
        String passObtain = dbPushPassword.getText().toString().trim();

        //Simple if logic to verify the patterns of the email and password.
        if(emailObtain.isEmpty()) {
            dbPushEmail.setError("Email address is required.");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailObtain).matches()){
            dbPushEmail.setError("Valid email address is required.");
        }

        if(passObtain.isEmpty()) {
            dbPushPassword.setError("Password field cannot be empty.");
        }
        if(passObtain.length() < 6) {
            dbPushPassword.setError("passwords length has to be at least 6 letter.");
        }

        //if all the necessary fields are filled correctly then these codes will be implemented.
        else if(!emailObtain.isEmpty() && !passObtain.isEmpty()) {

            //Calling the method on the java class that holds the method to validate the details into the database.
            Boolean userDetailsConfirm = sqlConnectLite.validateOnNewSignUpEmail(emailObtain);

            //If the user email that the user has entered already has ann account then this
            //piece of code is implemented.
            if(userDetailsConfirm == true) {
                dbPushEmail.setError("This email has already an account.");
            }

            //If the user email doesn't has any account the new account would be created.
            else{
                String defaultBio = "";

                //Calendar instance declared in order to store the current date.
                Calendar obtainDateCurrent = Calendar.getInstance();
                String provideToView = DateFormat.getDateInstance(DateFormat.SHORT).format(obtainDateCurrent.getTime());

                //Calling the methods that would insert the data that the user has entered into the database.
                Boolean saveOnBoardUserData = sqlConnectLite.saveChangeInData(emailObtain,passObtain,firstObtainName,lastObtainName
                ,nickObtainName,dobObtainName,defaultBio,provideToView);

                //If the account creation is not successful then this code will be executed.
                if(saveOnBoardUserData ==  false) {
                    Toast.makeText(this, "Opps something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                }
                //If the creation is successful the this code will be executed and the login page will open.
                else{
                    Toast.makeText(FinalTally.this, "You have been registered.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent logReverseMapper = new Intent(FinalTally.this, MainActivity.class);
                            startActivity(logReverseMapper);
                            finish();
                        }
                    }, 2000);
                }
            }
        }
    }
}