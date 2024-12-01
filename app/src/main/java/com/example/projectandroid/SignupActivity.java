package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    Button signupButton;
    TextView email;
    TextView password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup);
        auth=FirebaseAuth.getInstance();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill= email.getText().toString().trim();
                String passwordd = password.getText().toString().trim();
                if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(SignupActivity.this, "Remplir les champs", Toast.LENGTH_SHORT).show();
                }else {
                    SignupUser(emaill,passwordd);
                }
            }
        });
    }
private void SignupUser(String email,String password){


    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this,new OnCompleteListener<AuthResult>() {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isComplete()) {
                Toast.makeText(SignupActivity.this, "user cree par succes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            } else {

                Toast.makeText(SignupActivity.this, "authentification failed", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
}





