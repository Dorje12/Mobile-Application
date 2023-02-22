/*
This java class holds all the necessary fields that are important for inserting user details while signing up.
This java activity class basically is the interface that first pops up when a user is trying to sign up in the system.
Here the data fields that holds the users name and last name are present.
 */
package com.example.addu;

//All necessary imports that makes up the interface is present here.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class GetTallied extends AppCompatActivity {

    //Necessary field variables of objects being declared.
    Button btnFinalize;
    TextInputEditText saveNameDb;
    TextInputEditText saveLastNameDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tallied);

        //declared fields are initialized here so that we would be able to access the attributes of the
        //fields in the layout that has the id given below.
        btnFinalize = (Button) findViewById(R.id.finalizeBtn);
        saveNameDb = (TextInputEditText) findViewById(R.id.dbNameSaver);
        saveLastNameDb = (TextInputEditText) findViewById(R.id.dbLastNameSaver);

        //method to be implemented when the button with the id finalize button is clicked.
        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling for the method that passes the data that the user has entered in this interface
                //to another interface.
                saveDetailsDb();
            }
        });
    }

    //method that would be called when the button continue is clicked.
    private void saveDetailsDb() {
        //Declaration and initialization of the string variables in order to store the data that the user has entered.
        String obtainNameFirst = saveNameDb.getText().toString().trim();
        String obtainNameLast = saveLastNameDb.getText().toString().trim();

        //simple if else logic in order to handle situation when the user tries to access the upcoming page
        //without passing any value.
        if(obtainNameLast.isEmpty()) {
            saveLastNameDb.setError("Your Last name cannot be empty.");
        }
        if(obtainNameFirst.isEmpty()) {
            saveNameDb.setError("Your first name cannot be empty.");
        }
        //If all the values are properly entered then the user is allowed to access the other page and the details
        //passed with the help of the intent.
        else if(!obtainNameFirst.isEmpty() && !obtainNameLast.isEmpty()){
            Intent finishRegi = new Intent(GetTallied.this, com.example.addu.MiddleTally.class);
            finishRegi.putExtra("dbFirstName",obtainNameFirst);
            finishRegi.putExtra("dbLastName",obtainNameLast);
            startActivity(finishRegi);
        }
    }
}