package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Users extends AppCompatActivity {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

            mFirestoreList = findViewById(R.id.firestore_list);
            firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("users");

        FirestoreRecyclerOptions<UsersModel> options = new FirestoreRecyclerOptions.Builder<UsersModel>()
                .setQuery(query,UsersModel.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<UsersModel, UsersViewHolder>(options) {
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_item,parent,false);

                return new UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UsersModel model) {
                holder.list_name.setText(model.getName());
                holder.list_email.setText(model.getEmail());
                holder.list_mobile.setText(model.getMobile());
                //holder.list_image.setImageURI(Uri.parse("gs://blogger-s-tictactoe.appspot.com/users/DKLCLxXV0GNBw87qX4pEijcBuzn2profile.jpg"));
            }
        };

         mFirestoreList.setHasFixedSize(true);
         mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
         mFirestoreList.setAdapter(adapter);

    }

    private class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_email;
        private TextView list_mobile;
        //private ImageView list_image;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.nametextformfirestore);
            list_email= itemView.findViewById(R.id.emailtextformfirestore);
            list_mobile = itemView.findViewById(R.id.mobiletextformfirestore);
         //   list_image = itemView.findViewById(R.id.firebaseprofileimage);
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