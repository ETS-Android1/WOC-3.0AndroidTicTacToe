package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Blogs extends AppCompatActivity {

    private FirebaseFirestore fstore;
    private String userID;
    private FirebaseAuth auth;
    private  FirestoreRecyclerAdapter adapter;
    private RecyclerView blogsrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        fstore = FirebaseFirestore.getInstance();
        blogsrecyclerview = findViewById(R.id.savedblogsfromfirestore);

        Query query = fstore.collection("blogs"+userID);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateNewBlog.class));
            }
        });

        FirestoreRecyclerOptions<BlogsModel> options = new FirestoreRecyclerOptions.Builder<BlogsModel>()
                .setQuery(query,BlogsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<BlogsModel, BlogsViewHolder>(options) {
            @NonNull
            @Override
            public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogsviewer,parent,false);
                return new BlogsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BlogsViewHolder holder, int position, @NonNull BlogsModel model) {
                holder.texttitle.setText(model.getTitle());
                holder.textcontent.setText(model.getContent());
            }
        };

        blogsrecyclerview.setHasFixedSize(true);
        blogsrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        blogsrecyclerview.setAdapter(adapter);

    }

    private class BlogsViewHolder extends RecyclerView.ViewHolder {
            private TextView texttitle;
            private TextView textcontent;

        public BlogsViewHolder(@NonNull View itemView) {
            super(itemView);
            texttitle = itemView.findViewById(R.id.titleforblog);
            textcontent = itemView.findViewById(R.id.contentofblog);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}