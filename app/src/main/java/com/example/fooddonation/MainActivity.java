package com.example.fooddonation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fooddonation.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddonation.databinding.ActivityMainBinding;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    Dialog dialog;
    private DatabaseReference rootDatabseref;
    private DatabaseReference rootDatabseref2;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ////////////////////////////////////////////////////////////////////////////////////////////////


        setSupportActionBar(binding.appBarMain.toolbar);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbox));
        dialog.setCancelable(false);


        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");
        rootDatabseref2 = FirebaseDatabase.getInstance().getReference().child("dno");

        rootDatabseref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    num = Integer.parseInt(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();

                dialog.show();
            }
        });
        Button btnpost = dialog.findViewById(R.id.button3);
        Button btncancel = dialog.findViewById(R.id.button2);
        Spinner dropdown = dialog.findViewById(R.id.spinner1);
        String[] districts = {"Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kallakurichi", "Kanchipuram", "Kanniyakumari", "Karur", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", "Ranipet", "Salem", "Sivaganga", "Tenkasi", "Thanjavur", "Theni", "Thoothukudi", "Tiruchirappalli", "Tirunelveli", "Tirupathur", "Tiruppur", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", "Viluppuram", "Virudhunagar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, districts);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(13);


        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = dialog.findViewById(R.id.editTextText);
                String name = nameEditText.getText().toString();
                EditText foodEditText = dialog.findViewById(R.id.editTextText2);
                String food = foodEditText.getText().toString();
                EditText qEditText = dialog.findViewById(R.id.editTextText3);
                String quantity = qEditText.getText().toString();
                EditText addEditText = dialog.findViewById(R.id.editTextText5);
                String address = addEditText.getText().toString();
                EditText phEditText = dialog.findViewById(R.id.editTextText6);
                String phno = phEditText.getText().toString();
                Spinner spinner = dialog.findViewById(R.id.spinner1);
                String city = spinner.getSelectedItem().toString();

                nameEditText.setText("");
                foodEditText.setText("");
                qEditText.setText("");
                addEditText.setText("");
                nameEditText.setText("");
                phEditText.setText("");
                spinner.setSelection(13);

                HashMap map = new HashMap<>();
                map.put("name",name);
                map.put("food",food);
                map.put("quantity",quantity);
                map.put("address",address);
                map.put("phno",phno);
                map.put("city",city);

                rootDatabseref2.setValue(++num);

                rootDatabseref.child(String.valueOf(num)).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this,"Donation Added",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Donation Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}