package com.example.travel_sri_lanka.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.travel_sri_lanka.R;
import com.example.travel_sri_lanka.User.WildDestination.Yala;
import com.example.travel_sri_lanka.User.WildDestination.wilpattu;

public class Wild extends AppCompatActivity {
    ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild);


        //Hooks
        backBtn = findViewById(R.id.back_pre);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wild.super.onBackPressed();

            }
        });

    }
    public void Yala(View view) {
        startActivity(new Intent(this, Yala.class));

    }

    public void wil(View view) {
        startActivity(new Intent(this, wilpattu.class));

    }
}