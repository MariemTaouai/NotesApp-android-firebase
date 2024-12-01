package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signInButton;
    Button signupButton;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





            signInButton = findViewById(R.id.signin);
            signupButton = findViewById(R.id.signup);
        auth= FirebaseAuth.getInstance();


            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*if(auth.getCurrentUser()!=null){
                    Toast.makeText(MainActivity.this, "marhbe bikk", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,acceuil.class));
                     }else{*/
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));

                }

                });

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,SignupActivity.class));
                }
            });
        }
    }
