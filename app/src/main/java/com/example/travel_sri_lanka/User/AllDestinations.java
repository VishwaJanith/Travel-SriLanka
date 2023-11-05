package com.example.travel_sri_lanka.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.travel_sri_lanka.R;

public class AllDestinations extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_destinations);

        //Hooks
        backBtn = findViewById(R.id.back_pre);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllDestinations.super.onBackPressed();

            }
        });

    }
    public void wild(View view) {
        startActivity(new Intent(this, Wild.class));

    }

    public void heritage(View view) {
        startActivity(new Intent(this, Heritage.class));

    }

    public void surfing(View view) {
        startActivity(new Intent(this, Surfing.class));

    }

    public void bird(View view) {
        startActivity(new Intent(this, Birdwatching.class));

    }

    public void scuba(View view) {
        startActivity(new Intent(this, Scubadive.class));

    }

    public void beach(View view) {
        startActivity(new Intent(this, Beach.class));

    }

    public void waterfall(View view) {
        startActivity(new Intent(this, Waterfalls.class));

    }

    public void hiking(View view) {
        startActivity(new Intent(this, Hiking.class));

    }
}