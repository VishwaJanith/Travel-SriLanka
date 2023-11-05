package com.example.travel_sri_lanka.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_sri_lanka.Common.LoginSignup.Login;
import com.example.travel_sri_lanka.Common.LoginSignup.RetailerStartUpScreen;
import com.example.travel_sri_lanka.Common.LoginSignup.UserProfile;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.CategoriesAdapter;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.CategoriesHelperClass;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.FeaturedAdpater;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.FeaturedHelperClass;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.MostViewedAdpater;
import com.example.travel_sri_lanka.HelperClassess.HomeAdapter.MostViewedHelperClass;
import com.example.travel_sri_lanka.Maps.MapsActivity;
import com.example.travel_sri_lanka.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;
    private String name, username, email, phoneNo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_dashboard);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        naviagtionDrawer();

        // Retrieve user data from intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        phoneNo = intent.getStringExtra("phoneNo");
        password = intent.getStringExtra("password");

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);


        //Functions will be executed automatically when this activity will be created
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();

        // Initialize views and sensors
        TextView textView = findViewById(R.id.actionEvent);
        ImageView imageView = findViewById(R.id.temperatureIcon);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor tempSensor = null;
        boolean isTemperatureSensorAvailable = false;

        // Check if temperature sensor is available
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        } else {
            textView.setText("Temperature sensor is not available");
            isTemperatureSensorAvailable = false;
        }

        // Register sensor listener
        if (isTemperatureSensorAvailable) {
            SensorEventListener temperatureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float temperatureCelsius = event.values[0];
                    textView.setText(String.format("TODAY %.1f °C", temperatureCelsius));

                    if (temperatureCelsius > 32) {
                        imageView.setImageResource(R.drawable.hot);
                    } else if (temperatureCelsius > 21) {
                        imageView.setImageResource(R.drawable.warm);
                    } else if (temperatureCelsius > 10) {
                        imageView.setImageResource(R.drawable.mild);
                    }else if (temperatureCelsius >-1) {
                        imageView.setImageResource(R.drawable.cool);
                    }else {
                        imageView.setImageResource(R.drawable.cold);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Do nothing
                }
            };
            sensorManager.registerListener(temperatureListener, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.hiking_1,"Worlds End","Find a pleasing utopia in the grasslands of Hortons Plains where Mother "));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.beach_5,"Unawatuna","Unawatuna and its beach are near the colonial town of Galle, which is a great city for sightseeing. "));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.wild_1,"Yala ","Located on Sri Lanka's south east coast and established as a protected area in 1938, "));

        adapter = new FeaturedAdpater(featuredLocations);
        featuredRecycler.setAdapter(adapter);


    }

    private void mostViewedRecycler(){

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.wild_2,"Sinharaja Forest","A UNESCO-listed World Heritage Site and Biosphere Reserve, Sinharaja Forest Reserve"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.surfing_1,"Arugam Bay","116 km from Colombo is another fishing village known as Arugam Bay. Identified as the "));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.dive_3,"Mirissa","Initially famous for whale watching but gradually gaining popularity for a great diving spot in Sri Lanka."));

        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void categoriesRecycler() {

        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.category_1, "Destinations"));
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.category_2, "FOOD"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.category_3, "HOSPITAL"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.category_4, "Transport"));

        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);

        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);
    }

    public void map(View view) {
        startActivity(new Intent(this, MapsActivity.class));

    }

    public void past_All(View view) {
        startActivity(new Intent(this, AllCategories.class));

    }

    public void callRetailerScreens(View view){
        startActivity(new Intent(this, RetailerStartUpScreen.class));
    }


    public void openUserProfile(View view) {
        Intent intent = new Intent(UserDashboard.this, UserProfile.class);
        intent.putExtra("name", name);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("password", password);
        startActivity(intent);
    }



    //Navigation Drawer Functions
    private void naviagtionDrawer() {
        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                openUserProfile(item.getActionView());
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }
        return false;
    }

}