package com.example.travel_sri_lanka.User.WildDestination;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travel_sri_lanka.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class wilpattu extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private double latitude = 8.48505829773603; // Replace with your latitude 8.48505829773603, 80.0490060243827
    private double longitude = 80.0490060243827; // Replace with your longitude

    private Button navigation_btn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yala);

        //Hooks
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        navigation_btn = findViewById(R.id.map_dir);
        backBtn = findViewById(R.id.back_pre);

        navigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //double latitude = 6.368464528651551;
                //double longitude = 81.51667381313274;

                String uri = "http://maps.google.com/maps?saddr=&daddr=" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Google Maps app is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wilpattu.super.onBackPressed();

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Set the map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Create a LatLng object for the given coordinates
        LatLng locationLatLng = new LatLng(latitude, longitude);

        // Add a marker for the given location
        MarkerOptions markerOptions = new MarkerOptions()
                .position(locationLatLng)
                .title("My Location");
        googleMap.addMarker(markerOptions);

        // Move the camera to the location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(locationLatLng, 10.0f);
        googleMap.moveCamera(cameraUpdate);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
