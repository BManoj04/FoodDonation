package com.example.fooddonation.ui.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fooddonation.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GalleryFragment extends Fragment {

    DatabaseReference rootDatabseref;
    int totalDonation = 0,currentAvailable = 0,received = 0;
    PieChart pieChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        rootDatabseref = FirebaseDatabase.getInstance().getReference();

        pieChart = root.findViewById(R.id.piechart);
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

                    initPieChart();
                    showPie();
                }
            }

            private void initPieChart(){
                //using percentage as values instead of amount
                pieChart.setUsePercentValues(true);

                //remove the description label on the lower left corner, default true if not set
                pieChart.getDescription().setEnabled(false);

                //enabling the user to rotate the chart, default true
                pieChart.setRotationEnabled(true);
                //adding friction when rotating the pie chart
                pieChart.setDragDecelerationFrictionCoef(0.9f);
                //setting the first entry start from right hand side, default starting from top
                pieChart.setRotationAngle(0);

                //highlight the entry when it is tapped, default true if not set
                pieChart.setHighlightPerTapEnabled(true);
                //adding animation so the entries pop up from 0 degree
                pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                //setting the color of the hole in the middle, default white
                pieChart.setHoleColor(Color.parseColor("#000000"));

            }
            private void showPie() {
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                String label = "";
                Map<String, Integer> typeAmountMap = new HashMap<>();
                typeAmountMap.put("Available",currentAvailable);
                typeAmountMap.put("Acquired",received);

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#304567"));
                colors.add(Color.parseColor("#309967"));

                for(String type: typeAmountMap.keySet()){
                    pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
                }

                //collecting the entries with label name
                PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
                //setting text size of the value
                pieDataSet.setValueTextSize(15f);
                //providing color list for coloring different entries
                pieDataSet.setColors(colors);
                //grouping the data set from entry to chart
                PieData pieData = new PieData(pieDataSet);
                //showing the value of the entries, default true if not set
                pieData.setDrawValues(true);

                pieChart.setData(pieData);
                pieChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return root;
    }


}