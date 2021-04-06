package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Play extends AppCompatActivity {

   private RecyclerView playrecyclerview;
   private FirebaseFirestore fstore;
   private FirestoreRecyclerAdapter adapter;
   String userId;
   FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playrecyclerview = findViewById(R.id.playrecyclerview);
        fstore = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        auth = FirebaseAuth.getInstance();

        Query query = fstore.collection("play"+userId);


        FirestoreRecyclerOptions<PlayModel> options = new FirestoreRecyclerOptions.Builder<PlayModel>()
                .setQuery(query,PlayModel.class).build();

        adapter = new FirestoreRecyclerAdapter<PlayModel, PlayViewHolder>(options) {
            @NonNull
            @Override
            public PlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_item,parent,false);
                return new PlayViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PlayViewHolder holder, int position, @NonNull PlayModel model) {
                String senderid, receiverid;
                senderid = model.getSenderid();
                receiverid = model.getReceiverid();

                if (receiverid.equals(userId)){
                    DocumentReference docref = fstore.collection("users").document(senderid);
                docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        holder.list_name.setText(value.getString("Name"));
                        holder.list_email.setText(value.getString("email"));
                        holder.list_mobile.setText(value.getString("mobile"));
                        holder.list_uid.setText(value.getString("userUid"));
                        holder.buttonaddfriend.setText("Play The Game");

                        if( holder.list_uid.getText().toString().isEmpty()){return;}
                        else {
                            StorageReference profileref = FirebaseStorage.getInstance().getReference().child("users/"+ holder.list_uid.getText().toString()+"profile.jpg");
                            profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(uri).into(holder.list_image);

                                }
                            });}



                        holder.buttonaddfriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(v.getContext(), PlayOnline.class);
                                i.putExtra("senderid", model.getSenderid());
                                i.putExtra("receiverid", model.getReceiverid());
                                v.getContext().startActivity(i);

                            }
                        });
                    }
                });
                                                                                                    }else{
                    DocumentReference docref = fstore.collection("users").document(receiverid);
                    docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            holder.list_name.setText(value.getString("Name"));
                            holder.list_email.setText(value.getString("email"));
                            holder.list_mobile.setText(value.getString("mobile"));
                            holder.list_uid.setText(value.getString("userUid"));
                            holder.buttonaddfriend.setText("Play The Game");

                            if( holder.list_uid.getText().toString().isEmpty()){return;}
                            else {
                                StorageReference profileref = FirebaseStorage.getInstance().getReference().child("users/"+ holder.list_uid.getText().toString()+"profile.jpg");
                                profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).into(holder.list_image);

                                    }
                                });}

                            holder.buttonaddfriend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(v.getContext(), PlayOnline.class);
                                    i.putExtra("senderid", model.getSenderid());
                                    i.putExtra("receiverid", model.getReceiverid());
                                    v.getContext().startActivity(i);

                                }
                            });
                        }
                    });

                }
                    holder.closegame.setVisibility(View.VISIBLE);
                    holder.closegame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fstore.collection("play"+senderid).document(receiverid+"-"+senderid).delete();
                            fstore.collection("play"+receiverid).document(receiverid+"-"+senderid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   // startActivity(new Intent(getApplicationContext(),Play.class));
                                }
                            });
                        }
                    });
            }
        };

        playrecyclerview.setHasFixedSize(true);
        playrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        playrecyclerview.setAdapter(adapter);
    }

    private class PlayViewHolder extends RecyclerView.ViewHolder {
        private TextView list_name;
        private TextView list_email;
        private TextView list_mobile;
        private TextView list_uid;
        private ImageView list_image;
        private Button buttonaddfriend;
        private Button closegame;

        public PlayViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.nametextformfirestore);
            list_email= itemView.findViewById(R.id.emailtextformfirestore);
            list_mobile = itemView.findViewById(R.id.mobiletextformfirestore);
            list_uid = itemView.findViewById(R.id.uidtextbox);
            list_image = itemView.findViewById(R.id.firebaseprofileimage);
            buttonaddfriend = itemView.findViewById(R.id.addfriendinusers);
            closegame = itemView.findViewById(R.id.closegame);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
       /* if (auth.getCurrentUser()!= null){
            updateuserstat("offline");}*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
       /* if (auth.getCurrentUser()!= null){
            updateuserstat("online"); }*/
    }
 /*  private void updateuserstat(String state){
        Map<String,Object> status = new HashMap<>();
        status.put("status",state);
        fstore.collection("users").document(userId).update(status); }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (auth.getCurrentUser()!= null){
            updateuserstat("offline");} }*/
}