package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class Play extends AppCompatActivity {

   private RecyclerView playrecyclerview;
   private FirebaseFirestore fstore;
   private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playrecyclerview = findViewById(R.id.playrecyclerview);
        fstore = FirebaseFirestore.getInstance();

        Query query = fstore.collection("play");

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
                String senderid,receiverid;
                senderid = model.getSenderid();
                receiverid = model.getReceiverid();

                DocumentReference docref = fstore.collection("users").document(senderid);
                docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        holder.list_name.setText(value.getString("Name"));
                        holder.list_email.setText(value.getString("email"));
                        holder.list_mobile.setText(value.getString("mobile"));
                        holder.buttonaddfriend.setText("Play The Game");

                        holder.buttonaddfriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(v.getContext(),PlayOnline.class);
                                i.putExtra("senderid",model.getSenderid());
                                i.putExtra("receiverid",model.getReceiverid());
                                v.getContext().startActivity(i);

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

        public PlayViewHolder(@NonNull View itemView) {
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