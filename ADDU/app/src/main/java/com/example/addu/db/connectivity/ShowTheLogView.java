package com.example.addu.db.connectivity;


//All the necessary imports that are required in order to implementing the class.
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addu.DeleteValidation;
import com.example.addu.EditThePanePost;
import com.example.addu.R;

import java.util.ArrayList;

//This class will be extending the recycler view inorder to access the required properties and attributes.
public class ShowTheLogView extends RecyclerView.Adapter<ShowTheLogView.ListOwnObatiner> {

    //Declaration of the context variables along with the arraylist that will hold all the data required
    // to be displayed in the activity interface.
    private Context provideGateway;
    private ArrayList idShowingField,contentShowingField,dateShowingField_create,emailShowingField;
    private String idHolder;
    ActionSqlConnect sqlConnect;

    //Constructor creation in order to store the data in their respective fields and
    //accessing them as required.
    public ShowTheLogView(Context provideGateway, ArrayList idShowingField,ArrayList contentShowingField, ArrayList dateShowingField_create, ArrayList emailShowingField) {
        this.provideGateway = provideGateway;
        this.idShowingField = idShowingField;
        this.contentShowingField = contentShowingField;
        this.dateShowingField_create = dateShowingField_create;
        this.emailShowingField = emailShowingField;
    }

    //Default onCreate method in order to declare the the layout that will hold the design for the items.
    @NonNull
    @Override
    public ListOwnObatiner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View interfaceDesign = LayoutInflater.from(provideGateway).inflate(R.layout.viewlogown,parent,false);
        return new ListOwnObatiner(interfaceDesign);
    }

    //Default onBindViewHolder being used in order to set the values to the layout view with the help of the
    //activity that contains the recycler view.
    @Override
    public void onBindViewHolder(@NonNull ListOwnObatiner holder, int position) {
        holder.idShowingField.setText(String.valueOf(idShowingField.get(position)));
        holder.dateShowingField_create.setText(String.valueOf(dateShowingField_create.get(position)));
        holder.contentShowingField.setText(String.valueOf(contentShowingField.get(position)));
        holder.emailShowingField.setText(String.valueOf(emailShowingField.get(position)));

        //implementation of the onclick method that will open a new activity when the button edit is
        //clicked. String declaration holding the value of the onBoard user posts id.
        String idHolder = String.valueOf(idShowingField.get(position));
        holder.paneOpenEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openThePaneEdit = new Intent(provideGateway, EditThePanePost.class);
                openThePaneEdit.putExtra("idHolder",idHolder);
                provideGateway.startActivity(openThePaneEdit);
            }
        });

        //method that will be executed when the delete button is clicked in the posts log by a user.
        holder.paneOpenDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openThePaneEdit = new Intent(provideGateway, DeleteValidation.class);
                openThePaneEdit.putExtra("idHolder",idHolder);
                provideGateway.startActivity(openThePaneEdit);
            }
        });
    }

    //Method that holds the count of the size of the item.
    @Override
    public int getItemCount() {
        return contentShowingField.size();
    }

    //Class creation for declaring the fields in order to gain the attributes access of the layout
    //that holds the items design.
    public class ListOwnObatiner extends RecyclerView.ViewHolder{

        //Declaration of the fields and assigning them the id of the fields in the layout
        //in order to access the attributes that those field in the layout holds.
        TextView contentShowingField,dateShowingField_create,emailShowingField,idShowingField;
        TextView paneOpenDel,paneOpenEdit;
        public ListOwnObatiner(@NonNull View itemView) {
            super(itemView);

            idShowingField = (TextView) itemView.findViewById(R.id.openPaneSave);
            contentShowingField = (TextView) itemView.findViewById(R.id.setThePostContent);
            dateShowingField_create = (TextView) itemView.findViewById(R.id.setThePostedDate);
            emailShowingField = (TextView) itemView.findViewById(R.id.setThePostName);
            paneOpenDel = (TextView) itemView.findViewById(R.id.openPaneDel);
            paneOpenEdit = (TextView) itemView.findViewById(R.id.openPaneEdit);
        }
    }
}
