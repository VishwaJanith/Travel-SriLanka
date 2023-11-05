package com.example.travel_sri_lanka.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.travel_sri_lanka.R;

public class Waterfalls extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfalls);

        //Hooks
        backBtn = findViewById(R.id.back_pre);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Waterfalls.super.onBackPressed();

            }
        });
    }
}