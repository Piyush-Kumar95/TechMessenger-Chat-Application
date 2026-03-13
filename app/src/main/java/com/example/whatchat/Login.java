package com.example.whatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    EditText email, password;
    Button button;
    TextView Signbtn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTexLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        Signbtn = findViewById(R.id.logsignup);


        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Pass = password.getText().toString();

                if ((TextUtils.isEmpty(Email))){
                    Toast.makeText(Login.this,"Enter The Email",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Pass)){
                    Toast.makeText(Login.this,"Enter The Password",Toast.LENGTH_SHORT).show();

                } else if (!Email.matches(emailPattern)) {
                    email.setError("Give Proper Email Address");

                } else if (password.length()<6) {
                    password.setError(" Please Enter More Than 6 Characters");
                    Toast.makeText(Login.this,"Please Enter Password More Than 6 Characters ",Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                try {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }else {
                                Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}