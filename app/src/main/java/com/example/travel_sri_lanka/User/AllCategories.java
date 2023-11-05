package com.example.travel_sri_lanka.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.Manifest;
import com.example.travel_sri_lanka.User.UserDashboard;
import com.example.travel_sri_lanka.R;

public class AllCategories extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        //Hooks

        backBtn = findViewById(R.id.back_pre);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCategories.super.onBackPressed();

            }
        });

    }

    public void destination(View view) {
        startActivity(new Intent(this, AllDestinations.class));

    }

    public void hospital(View view) {
        startActivity(new Intent(this, HospitalDetails.class));

    }

    public void food(View view) {
        startActivity(new Intent(this, Food.class));

    }




}