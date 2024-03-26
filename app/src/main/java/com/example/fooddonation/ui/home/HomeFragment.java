package com.example.fooddonation.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fooddonation.MainActivity2;
import com.example.fooddonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public class HomeFragment extends Fragment{
    private LinearLayout linear;
    private DatabaseReference rootDatabseref;
    private Button viewbtn;

    SearchView editsearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        linear = root.findViewById(R.id.linear);
        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");
        rootDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    linear.removeAllViews();
                    if(snapshot.getValue().getClass().getSimpleName().equals("ArrayList")){
                        ArrayList listOfValues = (ArrayList) snapshot.getValue();
                        for (Object x:listOfValues) {
                            if(!String.valueOf(x).equals("null")){
                                String foodSubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 8) + 1, IndexOfOccurence(String.valueOf(x), ",", 8));
                                String quantitySubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 2) + 1, IndexOfOccurence(String.valueOf(x), ",", 2));
                                String city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                String shortMsg = quantitySubString + " - " + city;
                                String id = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 6) + 1, IndexOfOccurence(String.valueOf(x), ",", 6));
                                String address = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 1) + 1, IndexOfOccurence(String.valueOf(x), ",", 1));
                                String name = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 4) + 1, IndexOfOccurence(String.valueOf(x), ",", 4));
                                String lon = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 5) + 1, IndexOfOccurence(String.valueOf(x), ",", 5));
                                String lat = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 9) + 1, IndexOfOccurence(String.valueOf(x), "}", 1));
                                String phno = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 7) + 1, IndexOfOccurence(String.valueOf(x), ",", 7));
                                String metadata = id + ";" + address + ";" + name + ";" + lon + ";" + lat + ";" + phno;
                                addCard(foodSubString,shortMsg,metadata);
                            }

                        }
                    }else if (snapshot.getValue().getClass().getSimpleName().equals("HashMap")){
                        HashMap map  = (HashMap) snapshot.getValue();
                        Collection<Integer> values = map.values();

                        // Creating an ArrayList of values
                        ArrayList<Object> listOfValues = new ArrayList<>(values);
                        for (Object x:listOfValues) {

                            if(!String.valueOf(x).equals("null")) {
                                String foodSubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 8) + 1, IndexOfOccurence(String.valueOf(x), ",", 8));
                                String quantitySubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 2) + 1, IndexOfOccurence(String.valueOf(x), ",", 2));
                                String city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                String shortMsg = quantitySubString + " - " + city;
                                String id = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 6) + 1, IndexOfOccurence(String.valueOf(x), ",", 6));
                                String address = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 1) + 1, IndexOfOccurence(String.valueOf(x), ",", 1));
                                String name = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 4) + 1, IndexOfOccurence(String.valueOf(x), ",", 4));
                                String lon = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 5) + 1, IndexOfOccurence(String.valueOf(x), ",", 5));
                                String lat = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 9) + 1, IndexOfOccurence(String.valueOf(x), "}", 1));
                                String phno = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 7) + 1, IndexOfOccurence(String.valueOf(x), ",", 7));
                                String metadata = id + ";" + address + ";" + name + ";" + lon + ";" + lat + ";" + phno;
                                addCard(foodSubString,shortMsg,metadata);
                            }
                        }
                    }
                    Log.d("d", (String) snapshot.getValue().getClass().getSimpleName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    private void addCard(String food,String quantity,String metadata){
        View view = getLayoutInflater().inflate(R.layout.card,null);
        viewbtn = view.findViewById(R.id.button5);
        TextView foodtext = view.findViewById(R.id.textView8);
        TextView quantitytext = view.findViewById(R.id.textView11);
        TextView invisibletext = view.findViewById(R.id.metadata);
        invisibletext.setText(metadata);
        foodtext.setText(food);
        quantitytext.setText(quantity);
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout r = (RelativeLayout) view.getParent();
                TextView foodtxt = r.findViewById(R.id.textView8);
                String food = foodtxt.getText().toString();
                TextView shorttxt = r.findViewById(R.id.textView11);
                String quantity = shorttxt.getText().toString().substring(0,IndexOfOccurence(shorttxt.getText().toString(),"-",1));
                String city = shorttxt.getText().toString().substring(IndexOfOccurence(shorttxt.getText().toString(),"-",1)+1,shorttxt.getText().toString().length());
                String id = metadata.substring(0,IndexOfOccurence(metadata,";",1));
                String address = metadata.substring(IndexOfOccurence(metadata,";",1)+1,IndexOfOccurence(metadata,";",2));
                String name = metadata.substring(IndexOfOccurence(metadata,";",2)+1,IndexOfOccurence(metadata,";",3));
                String lon = metadata.substring(IndexOfOccurence(metadata,";",3)+1,IndexOfOccurence(metadata,";",4));
                String lat = metadata.substring(IndexOfOccurence(metadata,";",4)+1,IndexOfOccurence(metadata,";",5));
                String phno = metadata.substring(IndexOfOccurence(metadata,";",5)+1,metadata.length());

                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("name",name);
                intent.putExtra("id",id);
                intent.putExtra("address",address);
                intent.putExtra("lon",lon);
                intent.putExtra("lat",lat);
                intent.putExtra("phno",phno);
                intent.putExtra("food",food);
                intent.putExtra("quantity",quantity);
                intent.putExtra("city",city);
                startActivity(intent);
            }
        });
        linear.addView(view);
    }
    private int IndexOfOccurence(String s, String match, int occurence)
    {
        int i = 1;
        int index = 0;
        while (i <= occurence && (index = s.indexOf(match, index + 1)) != -1)
        {
            if (i == occurence)
                return index;
            i++;
        }
        return -1;
    }
}