package com.example.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private DatabaseReference rootDatabseref;
    String mailid;
    String receivername;
    private GoogleMap mymap;
    double latitude=0,longitude=0;
    String food = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.dialogbox2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbox));
        dialog.setCancelable(false);
        Button btnpost = dialog.findViewById(R.id.button3);
        Button btncancel = dialog.findViewById(R.id.button2);

        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");

        TextView nameText = findViewById(R.id.textView18);
        TextView foodText = findViewById(R.id.textView19);
        TextView qText = findViewById(R.id.textView20);
        TextView phnoText = findViewById(R.id.textView21);
        TextView addText = findViewById(R.id.textView22);
        TextView cityText = findViewById(R.id.textView23);
        Button btn = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button4);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String address = intent.getStringExtra("address");
        longitude = Double.parseDouble(intent.getStringExtra("lon"));
        latitude = Double.parseDouble(intent.getStringExtra("lat"));
        String phno = intent.getStringExtra("phno");
        food = intent.getStringExtra("food");
        String quantity = intent.getStringExtra("quantity");
        String city = intent.getStringExtra("city");

        StringBuffer a = new StringBuffer(address);
        String b = String.valueOf(a.insert(40, "\n"));

        nameText.setText(name);
        foodText.setText(food);
        qText.setText(quantity);
        phnoText.setText(phno);
        addText.setText(b);
        cityText.setText(city);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.show();

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = dialog.findViewById(R.id.receivername);
                receivername = nameEditText.getText().toString();
                EditText mailEditText = dialog.findViewById(R.id.mailid);
                mailid = mailEditText.getText().toString();
                String mailmsg = "Dear " + receivername + ",\n" + "\n" +
                        "We hope this email finds you well. We are reaching out to inform you that the food you ordered is now available through our Food Donation platform. We understand that you are in need of "+ food + "." +
                        "\n" +
                        "Details of the donation:\n" +
                        "- Food : "+ food + "\n" +
                        "- Quantity : " + quantity + "\n" +
                        "- Donar Name : " + name + " \n" +
                        "- Phone Number : " + phno + " \n" +
                        "- Address : " + address + " \n" +
                        "- City : " + city + " \n" +
                        "\n" +
                        "You can collect the donation at " + address + " during our operating hours. Please bring along a form of identification, such as a driver's license or ID card, for verification purposes.\n" +
                        "\n" +
                        "We are committed to ensuring that nutritious food reaches those who need it most, and we sincerely hope that this donation provides some relief during your time of need.\n" +
                        "\n" +
                        "If you have any questions or concerns, please do not hesitate to contact us at [contact information].\n" +
                        "\n" +
                        "Best regards,\n" +
                        "\n" +
                        "Manoj B\n" +
                        "Food Donation\n" +
                        "bmanoj2364@gmail,com";
                JavaMailAPI mail = new JavaMailAPI(getApplicationContext(),mailid,"Food Details",mailmsg);
                mail.execute();
                Toast.makeText(getApplicationContext(),"Details Send To Your Mail",Toast.LENGTH_SHORT).show();
                rootDatabseref.child(id).removeValue();
                nameEditText.setText("");
                mailEditText.setText("");
                dialog.dismiss();
                finish();
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mymap = googleMap;
        LatLng obj = new LatLng(latitude,longitude);
        mymap.addMarker(new MarkerOptions().position(obj).title(food));
        mymap.moveCamera(CameraUpdateFactory.newLatLng(obj));
        mymap.animateCamera( CameraUpdateFactory.zoomTo(15.0f));
        mymap.setTrafficEnabled(true);
    }
}