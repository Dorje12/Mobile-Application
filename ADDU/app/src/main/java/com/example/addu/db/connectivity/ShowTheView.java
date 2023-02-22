package com.example.addu.db.connectivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.addu.R;


//This class will be extending the recycler view inorder to access the required properties and attributes.
public class ShowTheView extends RecyclerView.Adapter<ShowTheView.ListViewObtainer> {

    //Declaration of the context variables along with the arraylist that will hold all the data required
    // to be displayed in the activity interface.
    private Context provideGateway;
    private ArrayList contentShowingField,dateShowingField_create,emailShowingField;

    //Constructor creation in order to store the data in their respective fields and
    //accessing them as required.
    public ShowTheView(Context provideGateway, ArrayList contentShowingField, ArrayList dateShowingField_create,ArrayList emailShowingField) {
        this.provideGateway = provideGateway;
        this.contentShowingField = contentShowingField;
        this.dateShowingField_create = dateShowingField_create;
        this.emailShowingField = emailShowingField;
    }

    //Default onCreate method in order to declare the the layout that will hold the design for the items.
    @NonNull
    @Override
    public ListViewObtainer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View interfaceDesign = LayoutInflater.from(provideGateway).inflate(R.layout.viewint,parent,false);
        return new ListViewObtainer(interfaceDesign);
    }

    //Default onBindViewHolder being used in order to set the values to the layout view with the help of the
    //activity that contains the recycler view.
    @Override
    public void onBindViewHolder(@NonNull ListViewObtainer holder, int position) {
        holder.dateShowingField_create.setText(String.valueOf(dateShowingField_create.get(position)));
        holder.contentShowingField.setText(String.valueOf(contentShowingField.get(position)));
        holder.emailShowingField.setText(String.valueOf(emailShowingField.get(position)));
    }

    //Method that holds the count of the size of the item.
    @Override
    public int getItemCount() {
        return contentShowingField.size();
    }

    //Implementing class creation for declaring the fields in order to gain the attributes access of the layout
    //that holds the items design.
    public class ListViewObtainer extends RecyclerView.ViewHolder {

        //Declaration of the fields and assigning them the id of the fields in the layout
        //in order to access the attributes that those field in the layout holds.
        TextView contentShowingField,dateShowingField_create,emailShowingField;
        public ListViewObtainer(@NonNull View itemView) {
            super(itemView);

            contentShowingField = (TextView) itemView.findViewById(R.id.setThePostContent);
            dateShowingField_create = (TextView) itemView.findViewById(R.id.setThePostedDate);
            emailShowingField = (TextView) itemView.findViewById(R.id.setThePostName);
        }
    }
}
