package com.example.travel_sri_lanka.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travel_sri_lanka.R;
import com.example.travel_sri_lanka.User.Wild;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);

        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        backBtn = findViewById(R.id.signup_back_button);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.super.onBackPressed();

            }
        });

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{0,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
    public void registerUser(View view) {
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
            return;
        }

        // Get user input values
        String name = regName.getEditText().getText().toString().trim();
        String username = regUsername.getEditText().getText().toString().trim();
        String email = regEmail.getEditText().getText().toString().trim();
        String phoneNo = regPhoneNo.getEditText().getText().toString().trim();
        String password = regPassword.getEditText().getText().toString();

        // Create an instance of FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Create a new user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User creation successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Save additional user data to the Realtime Database
                            if (user != null) {
                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // Username already exists, prompt the user to choose a different username
                                            regUsername.setError("Username already exists. Please choose a different username.");
                                            regUsername.requestFocus();
                                        } else {
                                            // Username is available, save user data to the database
                                            UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                                            usersRef.child(username).setValue(helperClass);

                                            // Display a success message to the user
                                            Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                            // Redirect the user to the login page
                                            Intent intent = new Intent(SignUp.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle the error
                                        Toast.makeText(SignUp.this, "Failed to register user: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            // User creation failed
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                // Password is too weak
                                regPassword.setError("Password is too weak");
                                regPassword.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                // Email address is badly formatted
                                regEmail.setError("Invalid email address");
                                regEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                // Email already exists
                                regEmail.setError("Email already exists");
                                regEmail.requestFocus();
                            } catch (Exception e) {
                                // Display an error message to the user
                                Toast.makeText(SignUp.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(this, Login.class));

    }


}
