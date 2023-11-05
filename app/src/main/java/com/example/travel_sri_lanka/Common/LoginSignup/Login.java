package com.example.travel_sri_lanka.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travel_sri_lanka.R;
import com.example.travel_sri_lanka.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView backBtn;
    Button callSignUp, login_btn;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        //Hooks
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        callSignUp = findViewById(R.id.callSignUp);
        backBtn = findViewById(R.id.login_back_button);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.super.onBackPressed();

            }
        });

    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            final String userEnteredUsername = username.getEditText().getText().toString().trim();
            final String userEnteredPassword = password.getEditText().getText().toString().trim();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String passwordFromDB = snapshot.child("password").getValue(String.class);
                            if (passwordFromDB.equals(userEnteredPassword)) {
                                username.setError(null);
                                username.setErrorEnabled(false);
                                String nameFromDB = snapshot.child("name").getValue(String.class);
                                String usernameFromDB = snapshot.child("username").getValue(String.class);
                                String phoneNoFromDB = snapshot.child("phoneNo").getValue(String.class);
                                String emailFromDB = snapshot.child("email").getValue(String.class);
                                Intent intent = new Intent(Login.this, UserDashboard.class);
                                intent.putExtra("name", nameFromDB);
                                intent.putExtra("username", usernameFromDB);
                                intent.putExtra("email", emailFromDB);
                                intent.putExtra("phoneNo", phoneNoFromDB);
                                intent.putExtra("password", passwordFromDB);
                                startActivity(intent);
                                finish(); // To prevent going back to Login activity on back press
                            } else {
                                password.setError("Wrong Password");
                                password.requestFocus();
                            }
                        }
                    } else {
                        username.setError("No such User exist");
                        username.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    public void callSignUpFromLogin(View view){
        startActivity(new Intent(this, SignUp.class));
    }

}