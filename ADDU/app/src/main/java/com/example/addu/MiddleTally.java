/*
This java class will access the data that was passed from the first tally then along with the data
that the user enters here, It will send those data to the final tally class. This class is responsible
for collecting the information regarding users nickname and date of birth.
 */
package com.example.addu;

//Most of the basic required imports are declared here.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addu.db.connectivity.ActionSqlConnect;
import com.google.android.material.textfield.TextInputEditText;

public class MiddleTally extends AppCompatActivity {

    //Necessary fields declaration in order to access certain properties.
    Button sendValThrough;
    TextInputEditText savePassDob;
    ActionSqlConnect sqlConnectLite;
    TextInputEditText savePassNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_tally);

        //declared fields are initialized here so that we would be able to access the attributes of the
        //fields in the layout that has the id given below.
        sqlConnectLite = new ActionSqlConnect(this);
        sendValThrough = (Button) findViewById(R.id.onfinalizeBtn);
        savePassDob = (TextInputEditText) findViewById(R.id.dbBornSaver);
        savePassNickName = (TextInputEditText) findViewById(R.id.dbNickNameSaver);

        //when the button with the id onFinalizeBtn is clicked following method is implemented.
        sendValThrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Declaration and initialization of the string variables in order to access the intent
                //data as well as the data that the user has input.
                String passSendDob = savePassDob.getText().toString().trim();
                String passSendNickName = savePassNickName.getText().toString().trim();
                String firstObtainName = getIntent().getStringExtra("dbFirstName");
                String lastObtainName = getIntent().getStringExtra("dbLastName");

                //simple logic in order to check if the value that the user has provided is not empty and
                //is not already picked by another user.
                if(!passSendNickName.isEmpty()) {
                    Boolean nickNameTakenConfirm = sqlConnectLite.validateOnNewSignUpNickName(passSendNickName);
                    //If the nickname has already been taken this piece of code will be executed.
                    if(nickNameTakenConfirm == true) {
                        savePassNickName.setError("This nickname has already been taken.");
                    }
                    //else this piece of code will be executed, which will pass the necessary data to the final tally class.
                    else{
                        Intent openFinalTally = new Intent(MiddleTally.this, com.example.addu.FinalTally.class);
                        openFinalTally.putExtra("dbFirstName",firstObtainName);
                        openFinalTally.putExtra("dbLastName",lastObtainName);
                        openFinalTally.putExtra("sendDobPass",passSendDob);
                        openFinalTally.putExtra("sendNickName",passSendNickName);
                        startActivity(openFinalTally);
                    }
                }
            }
        });
    }
}