package com.example.addu.db.connectivity;


//All the required imports are declared here in order to implement the class.
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addu.DeleteUserVal;
import com.example.addu.R;

import java.util.ArrayList;

//This class will be extending the recycler view inorder to access the required properties and attributes.
public class ShowUserListAdmin extends RecyclerView.Adapter<ShowUserListAdmin.ListUserForAdmin> {

    //Declaration of the context variables along with the arraylist that will hold all the data required
    // to be displayed in the activity interface.
    private Context provideGateway;
    private ArrayList emailShowingField,nickNameShowingField;

    //Constructor creation in order to store the data in their respective fields and
    //accessing them as required.
    public ShowUserListAdmin(Context provideGateway, ArrayList emailShowingField, ArrayList nickNameShowingField) {
        this.provideGateway = provideGateway;
        this.emailShowingField = emailShowingField;
        this.nickNameShowingField = nickNameShowingField;
    }

    //Default onCreate method in order to declare the the layout that will hold the design for the items.
    @NonNull
    @Override
    public ListUserForAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View interfaceDesign = LayoutInflater.from(provideGateway).inflate(R.layout.viewlistuser,parent,false);
        return new ListUserForAdmin(interfaceDesign);
    }

    //Default onBindViewHolder being used in order to set the values to the layout view with the help of the
    //activity that contains the recycler view.
    @Override
    public void onBindViewHolder(@NonNull ListUserForAdmin holder, int position) {
        holder.emailShowingField.setText((String.valueOf(emailShowingField.get(position))));
        holder.nickNameShowingField.setText(String.valueOf(nickNameShowingField.get(position)));

        //Method that will run when the button delete is clicked in the layout.
        //It will open the new activity.
        String emailHolder = String.valueOf(emailShowingField.get(position));
        holder.paneOpenUserDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String onBoardUserEmail = "admin@gmail.com";
                Intent openThePaneEdit = new Intent(provideGateway, DeleteUserVal.class);
                openThePaneEdit.putExtra("onBoardUserEmail",onBoardUserEmail);
                openThePaneEdit.putExtra("emailHolder",emailHolder);
                provideGateway.startActivity(openThePaneEdit);
            }
        });
    }

    //Method that holds the count of the size of the item.
    @Override
    public int getItemCount() {
        return emailShowingField.size();
    }

    //Class creation for declaring the fields in order to gain the attributes access of the layout
    //that holds the items design.
    public class ListUserForAdmin extends RecyclerView.ViewHolder {

        //Declaration of the fields and assigning them the id of the fields in the layout
        //in order to access the attributes that those field in the layout holds.
        TextView emailShowingField,nickNameShowingField;
        TextView paneOpenUserDel;
        public ListUserForAdmin(@NonNull View itemView) {
            super(itemView);

            emailShowingField = (TextView) itemView.findViewById(R.id.holdTheUserEmail);
            nickNameShowingField = (TextView) itemView.findViewById(R.id.holdTheUserName);
            paneOpenUserDel = (TextView) itemView.findViewById(R.id.openPaneUserDel);
        }
    }
}
