package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import pl.droidsonroids.gif.GifImageView;

public class Login extends AppCompatActivity {
    EditText user,pass;
    Button loginbutton;
    FirebaseAuth Auth;
    TextView Registerbutton;
    GifImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.user2);
        pass = findViewById(R.id.pass2);
        loginbutton = findViewById(R.id.butlogin);
        Registerbutton = findViewById(R.id.logintext);
        image = findViewById(R.id.gif);

        Auth = FirebaseAuth.getInstance();

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    user.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    pass.setError("Password is required!!");
                    return;
                }
                if(password.length() < 6){
                    pass.setError("Password must be greater than or equal to 6 character long!");
                    return;
                }
                image.setVisibility(View.VISIBLE);




                Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "User Logged IN Sussessfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); }
                        else { Toast.makeText(Login.this, "Error! " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            image.setVisibility(View.INVISIBLE);
                        }


                    }

                });
            }

        });



    }
}