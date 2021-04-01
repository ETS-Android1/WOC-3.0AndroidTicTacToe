package com.example.bloggerstictactoe;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class CreateNewBlog extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText contentblog;
    EditText titleforblog;
    String userID;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_blog);
        Toolbar toolbar = findViewById(R.id.blogtitle);
        setSupportActionBar(toolbar);

        fstore = FirebaseFirestore.getInstance();

        Intent data = getIntent();
        if (data.getStringExtra("title") != null) {

            titleforblog.setText(data.getStringExtra("title"));
            contentblog.setText(data.getStringExtra("content"));
        }



        titleforblog = findViewById(R.id.blogtitleforfirestore);
        contentblog = findViewById(R.id.blogcontent);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String nTitle = titleforblog.getText().toString();
                String nContent = contentblog.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(CreateNewBlog.this, "The note cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docref = fstore.collection("blogs"+userID).document();
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);
                
                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateNewBlog.this, "Blog Saved Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Blogs.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewBlog.this, "Failed! Pls try again", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}