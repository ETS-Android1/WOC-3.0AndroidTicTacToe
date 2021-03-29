package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Friends extends AppCompatActivity {

    private RecyclerView friendrecyclerview;
    private FirebaseFirestore fstore;
    FirebaseAuth auth;
    String userID;
    private FirestoreRecyclerAdapter adapter;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friendrecyclerview = findViewById(R.id.friendslist);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        Query query = fstore.collection("Friendsof"+userID);

        FirestoreRecyclerOptions<FriendsModel> options = new FirestoreRecyclerOptions.Builder<FriendsModel>()
                .setQuery(query,FriendsModel.class).build();

         adapter = new FirestoreRecyclerAdapter<FriendsModel, FriendsViewHolder>(options) {
            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_item,parent,false);
                return new FriendsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FriendsViewHolder holder, int position, @NonNull FriendsModel model) {
                String uidofuser;
                uidofuser = model.getUserIDoffriend();

                DocumentReference docref = fstore.collection("users").document(uidofuser);
                docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        holder.list_name.setText(value.getString("Name"));
                        holder.list_email.setText(value.getString("email"));
                        holder.list_mobile.setText(value.getString("mobile"));
                        holder.buttonaddfriend.setVisibility(View.INVISIBLE);

                        if( holder.list_uid.getText().toString().isEmpty()){return;}
                        else {
                            StorageReference profileref = storageReference.child("users/"+uidofuser+"profile.jpg");
                            profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(uri).into(holder.list_image);

                                }
                            });}
                    }
                });

            }
        };

        friendrecyclerview.setHasFixedSize(true);
        friendrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        friendrecyclerview.setAdapter(adapter);
    }

    private class FriendsViewHolder extends RecyclerView.ViewHolder {
        private TextView list_name;
        private TextView list_email;
        private TextView list_mobile;
        private TextView list_uid;
        private ImageView list_image;
        private Button buttonaddfriend;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.nametextformfirestore);
            list_email= itemView.findViewById(R.id.emailtextformfirestore);
            list_mobile = itemView.findViewById(R.id.mobiletextformfirestore);
            list_uid = itemView.findViewById(R.id.uidtextbox);
            list_image = itemView.findViewById(R.id.firebaseprofileimage);
            buttonaddfriend = itemView.findViewById(R.id.addfriendinusers);
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