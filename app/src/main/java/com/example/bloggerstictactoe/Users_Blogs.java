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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Users_Blogs extends AppCompatActivity {

    private RecyclerView userblogrecycler;
    private FirebaseFirestore fstore;
    String userId;
    private  FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users__blogs);

        Intent data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        userId = data.getStringExtra("useridforuserblogs");
        userblogrecycler = findViewById(R.id.UserBlogRecyclerViewer);

        Query query = fstore.collection("blogs"+userId);
        FirestoreRecyclerOptions<UserBlogsModel> options = new FirestoreRecyclerOptions.Builder<UserBlogsModel>()
                .setQuery(query,UserBlogsModel.class).build();

         adapter = new FirestoreRecyclerAdapter<UserBlogsModel, UserBlogsViewHolder>(options) {
            @NonNull
            @Override
            public UserBlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogsviewer,parent,false);
                return new UserBlogsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserBlogsViewHolder holder, int position, @NonNull UserBlogsModel model) {
                holder.title.setText(model.getTitle());
                holder.content.setText(model.getContent());
            }
        };
         userblogrecycler.setHasFixedSize(true);
         userblogrecycler.setLayoutManager(new LinearLayoutManager(this));
         userblogrecycler.setAdapter(adapter);
    }

    private class UserBlogsViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        public UserBlogsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleforblog);
            content = itemView.findViewById(R.id.contentofblog);
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