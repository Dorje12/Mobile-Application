/*
This java activity will be the interface of the login page that will allow a registered user to get
access of the system.
 */
package com.example.addu;

//all the necessary required imports that make this activity whole are listed here.

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //Required fields that will extract or set the data from or to the layout.
    TextView takeMapSign;
    Button mapAfterLogin;
    ActionSqlConnect sqlConnectLite;
    TextInputEditText checkEmailInput;
    TextInputEditText checkPassInput;
    SharedPreferences storeDetails;
    SharedPreferences.Editor makeEdit;

    //String variable that holds the gmail account for the admin.
    String adminUserEmail = "admin@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the fields that will be able to access the attributes if the fields with the below mentioned id's
        //in the xml layout.
        sqlConnectLite = new ActionSqlConnect(this);
        takeMapSign = (TextView) findViewById(R.id.logMapSign);
        mapAfterLogin = (Button) findViewById(R.id.complyBtn);
        checkEmailInput = (TextInputEditText) findViewById(R.id.checkEmailVal);
        checkPassInput = (TextInputEditText) findViewById(R.id.checkPassVal);

        storeDetails = getSharedPreferences("OnBoardUser",MODE_PRIVATE);
        makeEdit = storeDetails.edit();

        //Button with the id logMapSign if is clicked then this method will be started.
        takeMapSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Opening the new activity named getTailed class when user wants to create a new account.
                Intent logSignMapper = new Intent(MainActivity.this,GetTallied.class);
                startActivity(logSignMapper);
            }
        });

        //When the login button is clicked the this method will get implemented.
        mapAfterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Declaration of the string variables in order to store the user given details
                String obtainValEmail = checkEmailInput.getText().toString().trim();
                String obtainValPass = checkPassInput.getText().toString().trim();

                //simple logics to verify if the user has filled the details or not.
                if(obtainValPass.isEmpty()) {
                    checkPassInput.setError("This field needs to be filled.");
                }
                if(obtainValEmail.isEmpty()){
                    checkEmailInput.setError("This field needs to be filled.");
                }
                else{
                    //Calling the method from the ActionSqlConnect in order to validate the user details.
                    Boolean allowAccess = sqlConnectLite.validateOnNewSignUp(obtainValEmail,obtainValPass);
                    //If the user provide details are incorrect they would not be allowed the access.
                    if(allowAccess == false) {
                        Toast.makeText(MainActivity.this, "Wrong login details.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        makeEdit.putString("userEmailOnBoard",obtainValEmail);
                        makeEdit.putString("userPassOnBoard",obtainValPass);
                        makeEdit.apply();
                        //If the user trying to login is the admin then this logic will map the admin.
                        if(obtainValEmail.equals(adminUserEmail)){
                            Intent mapLoginAfter = new Intent(MainActivity.this, com.example.addu.AfterLoginProfile.class);
                            mapLoginAfter.putExtra("onBoardUserEmail",obtainValEmail);
                            startActivity(mapLoginAfter);
                        }
                        //If the user is normal user then this else statement will map them to after login profile.
                        else{
                            Intent mapLoginAfter = new Intent(MainActivity.this, com.example.addu.AfterLoginProfile.class);
                            mapLoginAfter.putExtra("onBoardUserEmail",obtainValEmail);
                            startActivity(mapLoginAfter);
                        }
                    }
                }
            }
        });
    }
}