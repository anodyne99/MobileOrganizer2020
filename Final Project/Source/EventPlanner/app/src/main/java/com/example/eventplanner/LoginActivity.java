package com.example.eventplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn,registerBtn,lunch,update,save;
    EditText name,email,password;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        lunch = (Button) findViewById(R.id.launch);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //Initializing firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //checking if user has already been created before or already logged in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        }


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(mPassword)) {
                    password.setError("Password is required.");
                    return;

                }

                if (password.length() < 6) {
                    password.setError("Password must contain at least 6 characters");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "User not validated" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void redirectToHome(View v) {


        // Store values at the time of the login attempt.
        String mEmail = email.getText().toString().trim();
        String mPassword = password.getText().toString().trim();

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword) ) {
            password.setError("Password is required.");

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            email.setError("Email is required");
        }

        //authenticate user
        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    public void redirectToHomeActivity (View v) {
        Intent redirect = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(redirect);

    }


}
