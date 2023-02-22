/*
This activity holds the java class and xml layout that manifests the interface for displaying
the lists of the users currently available in the system.
 */
package com.example.addu;
//All the necessary imports required for building the interface is listed here.
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.addu.db.connectivity.ActionSqlConnect;
import com.example.addu.db.connectivity.ShowUserListAdmin;
import java.util.ArrayList;

public class AdminUserInterface extends AppCompatActivity {

    //Declaration of the arraylist variables, objects, String variables and fields.
    RecyclerView listShowAdminUser;
    ActionSqlConnect actionSqlConnect;
    String getTheOnBoardEmail = null;
    ShowUserListAdmin showUserListAdmin;
    ArrayList<String> emailObtainVal,nickNameObtainVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_interface);

        //Initialization of the objects in order to access the methods,fields in order to access the
        //attributes of the layout.
        listShowAdminUser = (RecyclerView) findViewById(R.id.viewTheuserListAdmin);

        actionSqlConnect = new ActionSqlConnect(this);
        emailObtainVal = new ArrayList<>();
        nickNameObtainVal = new ArrayList<>();
        showUserListAdmin = new ShowUserListAdmin(this,emailObtainVal,nickNameObtainVal);

        //Setting the view for the recycler view in the layout
        listShowAdminUser.setAdapter(showUserListAdmin);
        listShowAdminUser.setLayoutManager(new LinearLayoutManager(this));

        //Calling method.
        disTheListUser();
    }

    //method that displays the lists of all the registered user in the system.
    public void disTheListUser() {

        //String variable initialization and declaration in order to store the email.
        String onBoardUserEmail = getIntent().getStringExtra("onBoardUserEmail");

        //Cursor variable in order to store the data that is received when
        //The method in the actionSqlConnect class is called by passing the email as an argument.
        Cursor obtainReqVal = actionSqlConnect.getUserExcept(onBoardUserEmail);

        //Simple logic inorder to handle the situation when the list of the user is nil.
        if(obtainReqVal.getCount() == 0) {
            Toast.makeText(this, "There has been no post made.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //Up until a user is available put the user in the layout field and populate it.
            while (obtainReqVal.moveToNext()) {
                emailObtainVal.add(obtainReqVal.getString(0));
                nickNameObtainVal.add(obtainReqVal.getString(4));
            }
        }
    }
}