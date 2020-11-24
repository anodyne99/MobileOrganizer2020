package com.example.eventplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn,registerBtn,lunch,update,save;
    EditText name,email,password;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn=(Button)findViewById(R.id.loginBtn);
        lunch=(Button)findViewById(R.id.launch);
        update=(Button)findViewById(R.id.update);
        save=(Button)findViewById(R.id.save);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //Initializing firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //checking if user has already been created before or already logged in
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));

        }


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(mPassword)) {
                    password.setError("Password is required.");
                    return;

                }

                if(password.length() < 6){
                    password.setError("Password must be >= 6 characters");
                    return;
                }
              mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(LoginActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                      }

                      else{
                        Toast.makeText(LoginActivity.this,"User not validated" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                      }
                  }
              });

            }
        });





    }


    public void redirectToHomeActivity (View v) {
        Intent redirect = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(redirect);

    }


}
