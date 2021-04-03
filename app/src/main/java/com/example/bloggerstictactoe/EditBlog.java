package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditBlog extends AppCompatActivity {
    EditText edittexttitle,edittextcontent;
    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);

        Intent data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();

        edittexttitle = findViewById(R.id.edittexttitle);
        edittextcontent = findViewById(R.id.edittextcontent);

        edittexttitle.setText(data.getStringExtra("title"));
        edittextcontent.setText(data.getStringExtra("content"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nTitle = edittexttitle.getText().toString();
                String nContent = edittextcontent.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(EditBlog.this, "The note cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                 fstore.collection("blogs"+data.getStringExtra("useridofblogowner")).whereEqualTo("title",data.getStringExtra("title"))
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String documentID = documentSnapshot.getId();
                                fstore.collection("blogs"+data.getStringExtra("useridofblogowner")).document(documentID).update(note)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(EditBlog.this, "Blog updated", Toast.LENGTH_SHORT).show();
                                        if (data.getStringExtra("useridofblogowner") == userID){startActivity(new Intent(getApplicationContext(),Blogs.class));}
                                        else{startActivity(new Intent(getApplicationContext(),Users.class));}
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditBlog.this, "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


            }
        });
    }
}