package com.example.fooddonation.ui.home;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fooddonation.MainActivity;
import com.example.fooddonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Button btn;
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
                    View view2 = getLayoutInflater().inflate(R.layout.card,null);
                    TextView r = view2.findViewById(R.id.textView8);

                    if(((RelativeLayout) r.getParent()).getChildCount() > 0)
                        ((RelativeLayout) r.getParent()).removeAllViews();
                    if(snapshot.getValue().getClass().getSimpleName().equals("ArrayList")){
                        ArrayList listOfValues = (ArrayList) snapshot.getValue();
                        for (Object x:listOfValues) {

                            //addCard(String.valueOf(x));
                            //name
                            if(!String.valueOf(x).equals("null")){
                                addCard(String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3)));
                            }

                        }
                    }else if (snapshot.getValue().getClass().getSimpleName().equals("HashMap")){
                        HashMap map  = (HashMap) snapshot.getValue();
                        Collection<Integer> values = map.values();

                        // Creating an ArrayList of values
                        ArrayList<Object> listOfValues = new ArrayList<>(values);
                        for (Object x:listOfValues) {

                            if(!String.valueOf(x).equals("null")) {
                                //addCard(String.valueOf(x));
                                addCard(String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3)));
                            }
                        }
                    }else {
                        Log.d("error", (String) snapshot.getValue().getClass().getSimpleName());
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
    private void addCard(String val){
        View view = getLayoutInflater().inflate(R.layout.card,null);
        TextView t = view.findViewById(R.id.textView8);
        t.setText(val);
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