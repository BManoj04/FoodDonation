package com.example.fooddonation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddonation.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    Dialog dialog;
    private DatabaseReference rootDatabseref;
    private DatabaseReference rootDatabseref2,rootDatabseref3;
    int num;
    long totalNumberOfFood;
    LocationManager locationManager;
    double lat = 0,lon = 0;
    String address = "";
    Boolean locationBollean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ////////////////////////////////////////////////////////////////////////////////////////////////



        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },100);
        }
        getLocation();

        setSupportActionBar(binding.appBarMain.toolbar);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbox));
        dialog.setCancelable(false);


        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");
        rootDatabseref2 = FirebaseDatabase.getInstance().getReference().child("dno");
        rootDatabseref3 = FirebaseDatabase.getInstance().getReference();
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
        rootDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalNumberOfFood = snapshot.getChildrenCount() -1 ;
                    rootDatabseref3.child("totalNumberOfFood").setValue(++totalNumberOfFood);
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

                EditText addEditText = dialog.findViewById(R.id.address);
                addEditText.setText(address);
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
                EditText nameEditText = dialog.findViewById(R.id.receivername);
                String name = nameEditText.getText().toString();
                EditText foodEditText = dialog.findViewById(R.id.mailid);
                String food = foodEditText.getText().toString();
                EditText qEditText = dialog.findViewById(R.id.quantity);
                String quantity = qEditText.getText().toString();
                EditText addEditText = dialog.findViewById(R.id.address);
                String address = addEditText.getText().toString();
                EditText phEditText = dialog.findViewById(R.id.phoneNumber);
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
                map.put("id",++num);
                map.put("lat",lat);
                map.put("lon",lon);

                rootDatabseref2.setValue(num);

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

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1, MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address = addresses.get(0).getAddressLine(0).replace(",","-");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(locationBollean) {
            Toast.makeText(MainActivity.this, "Location Detected", Toast.LENGTH_SHORT).show();
        }
        locationBollean = false;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}