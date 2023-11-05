package com.example.travel_sri_lanka.Common.LoginSignup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_sri_lanka.R;
import com.example.travel_sri_lanka.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;


    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        //Hooks
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //showalldata
        showAllUserData();
        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sign out user from Firebase Auth
                FirebaseAuth.getInstance().signOut();

                // clear user data from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // navigate to RetailerStartUpScreen activity
                Intent intent = new Intent(UserProfile.this, UserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteUserData();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteUserData() {
        // remove user data from Firebase Database
        reference.child(_USERNAME).removeValue();

        // sign out user from Firebase Auth
        FirebaseAuth.getInstance().signOut();

        // clear user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // navigate to LoginActivity activity
        Intent intent = new Intent(UserProfile.this, UserDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phoneNo.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);
    }

    public void update(View view){
        if(isNameChanged() || isPasswordChanged()){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Data is same and cannot be updated",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPasswordChanged() {
        String newPassword = password.getEditText().getText().toString().trim();
        if (!_PASSWORD.equals(newPassword)) {
            reference.child(_USERNAME).child("password").setValue(newPassword);
            _PASSWORD = newPassword;
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        String newName = fullName.getEditText().getText().toString().trim();
        if (!_NAME.equals(newName)) {
            reference.child(_USERNAME).child("name").setValue(newName);
            _NAME = newName;
            return true;
        } else {
            return false;
        }
    }
}