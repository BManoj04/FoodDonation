package com.example.fooddonation.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.fooddonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public class HomeFragment extends Fragment {
    private LinearLayout linear;
    private DatabaseReference rootDatabseref;

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

                            //addCard(String.valueOf(x));
                            //name
                            if(!String.valueOf(x).equals("null")){
                                String foodSubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 6) + 1, IndexOfOccurence(String.valueOf(x), "}", 1));
                                String quantitySubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 2) + 1, IndexOfOccurence(String.valueOf(x), ",", 2));
                                String city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                String shortMsg = quantitySubString + " - " + city;
                                addCard(foodSubString,shortMsg);
                            }

                        }
                    }else if (snapshot.getValue().getClass().getSimpleName().equals("HashMap")){
                        HashMap map  = (HashMap) snapshot.getValue();
                        Collection<Integer> values = map.values();

                        // Creating an ArrayList of values
                        ArrayList<Object> listOfValues = new ArrayList<>(values);
                        for (Object x:listOfValues) {

                            if(!String.valueOf(x).equals("null")) {
                                String foodSubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 6) + 1, IndexOfOccurence(String.valueOf(x), "}", 1));
                                String quantitySubString = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 2) + 1, IndexOfOccurence(String.valueOf(x), ",", 2));
                                String city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                String shortMsg = quantitySubString + " - " + city;
                                addCard(foodSubString,shortMsg);
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
    private void addCard(String food,String quantity){
        View view = getLayoutInflater().inflate(R.layout.card,null);
        TextView foodtext = view.findViewById(R.id.textView8);
        TextView quantitytext = view.findViewById(R.id.textView11);
        foodtext.setText(food);
        quantitytext.setText(quantity);
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