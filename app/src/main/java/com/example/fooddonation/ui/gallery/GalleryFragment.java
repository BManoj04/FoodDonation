package com.example.fooddonation.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fooddonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GalleryFragment extends Fragment {

    DatabaseReference rootDatabseref;
    int totalDonation = 0,currentAvailable = 0,received = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        rootDatabseref = FirebaseDatabase.getInstance().getReference();
        rootDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalDonation = Integer.parseInt(snapshot.child("dno").getValue().toString());
                    currentAvailable = Integer.parseInt(snapshot.child("totalNumberOfFood").getValue().toString());
                    received = totalDonation - currentAvailable;

                    TextView one  = root.findViewById(R.id.totalDonationno);
                    TextView two  = root.findViewById(R.id.currentAvailableno);
                    TextView three  = root.findViewById(R.id.receivedno);

                    one.setText(String.valueOf(totalDonation));
                    two.setText(String.valueOf(currentAvailable));
                    three.setText(String.valueOf(received));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return root;
    }


}