package com.example.whatchat;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    TextView loginBut;
    EditText rUsername,rEmail,rPassword, rrPaswword;
    Button rgSignUp;
//    CircleImageView rg_profileImg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseDatabase database;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        loginBut = findViewById(R.id.loginbut);
        rUsername = findViewById(R.id.rgusername);
        rEmail = findViewById(R.id.rgemail);
        rPassword = findViewById(R.id.rgpassword);
        rrPaswword = findViewById(R.id.rgrepassword);
        rgSignUp = findViewById(R.id.signupbutton);
//        rg_profileImg = findViewById(R.id.profilerg0);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        rgSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namee = rUsername.getText().toString();
                String emaill = rEmail.getText().toString();
                String password = rPassword.getText().toString();
                String cpassword = rrPaswword.getText().toString();
                String status = "Hey I am Using This Application";

                if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(Register.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailPattern)) {
                    rEmail.setError("Type A Valid Email Here");
                } else if (password.length() < 6) {
                    rrPaswword.setError("Password Must Be 6 Characters Or More");
                } else if (!password.equals(cpassword)) {
                    rPassword.setError("The Password Doesn't Match");
                } else {
                    auth.createUserWithEmailAndPassword(emaill,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                Users users = new Users(id,namee,emaill,password,cpassword,status);
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(Register.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }else {
                                            Toast.makeText(Register.this,"Error in Creating the User",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }
            }
        });

    }
}