package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView nameText = findViewById(R.id.textView18);
        TextView foodText = findViewById(R.id.textView19);
        TextView qText = findViewById(R.id.textView20);
        TextView phnoText = findViewById(R.id.textView21);
        TextView addText = findViewById(R.id.textView22);
        TextView cityText = findViewById(R.id.textView23);
        Button btn = findViewById(R.id.button);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String address = intent.getStringExtra("address");
        String lon = intent.getStringExtra("lon");
        String lat = intent.getStringExtra("lat");
        String phno = intent.getStringExtra("phno");
        String food = intent.getStringExtra("food");
        String quantity = intent.getStringExtra("quantity");
        String city = intent.getStringExtra("city");

        nameText.setText(name);
        foodText.setText(food);
        qText.setText(quantity);
        phnoText.setText(phno);
        addText.setText(address);
        cityText.setText(city);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}