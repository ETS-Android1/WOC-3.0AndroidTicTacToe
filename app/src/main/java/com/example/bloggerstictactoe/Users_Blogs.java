package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Users_Blogs extends AppCompatActivity {

    private RecyclerView userblogrecycler;
    private FirebaseFirestore fstore;
    String userId;
    String currentuserid;
    String currentusername;
    private  FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users__blogs);

        Intent data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        userId = data.getStringExtra("useridforuserblogs");
        userblogrecycler = findViewById(R.id.UserBlogRecyclerViewer);

        currentuserid = FirebaseAuth.getInstance().getCurrentUser().getUid();



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


                fstore.collection("blogs"+userId).whereEqualTo("title",model.getTitle()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String documentID = documentSnapshot.getId();
                        DocumentReference docref = fstore.collection("blogs"+userId).document(documentID);
                        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                holder.likecount.setText(value.getLong("likes").toString());
                            }
                        });
                    }
                });

                holder.likeimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fstore.collection("blogs"+userId).whereEqualTo("title",model.getTitle()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String documentID = documentSnapshot.getId();
                                fstore.collection("blogs"+userId).document(documentID).update("likes", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        holder.likeimage.setColorFilter(Color.GREEN);
                                    }
                                });
                            }
                        });
                    }
                });
                holder.optionsimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                        popupMenu.getMenu().add("Send Collaboration Request").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                Map<String, Object> sendingrequest = new HashMap<>();
                                sendingrequest.put("approve","false");
                                sendingrequest.put("name", data.getStringExtra("name"));
                                sendingrequest.put("requestid",currentuserid);
                                fstore.collection("blogs"+userId).whereEqualTo("title",model.getTitle())
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        String documentID = documentSnapshot.getId();
                                        fstore.collection("blogs"+userId).document(documentID).update(sendingrequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Users_Blogs.this, "Request Send", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });

                                return false;
                            }
                        });
                        if (model.getRequestid()!=null){
                        if (model.getRequestid().equals(currentuserid) && model.getApprove().equals("true")){
                        popupMenu.getMenu().add("Collaborate").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent i = new Intent(v.getContext(),EditBlog.class);
                                i.putExtra("title",model.getTitle());
                                i.putExtra("content",model.getContent());
                                i.putExtra("useridofblogowner",data.getStringExtra("useridforuserblogs"));
                                v.getContext().startActivity(i);
                                return false;

                            }
                        });}}
                        popupMenu.show();
                    }
                });
            }
        };
         userblogrecycler.setHasFixedSize(true);
         userblogrecycler.setLayoutManager(new LinearLayoutManager(this));
         userblogrecycler.setAdapter(adapter);
    }

    private class UserBlogsViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private ImageView optionsimage;
        private ImageView likeimage;
        private TextView likecount;
        public UserBlogsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleforblog);
            content = itemView.findViewById(R.id.contentofblog);
            optionsimage = itemView.findViewById(R.id.optionsimage);
            likeimage = itemView.findViewById(R.id.likeimage);
            likecount = itemView.findViewById(R.id.likecount);
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