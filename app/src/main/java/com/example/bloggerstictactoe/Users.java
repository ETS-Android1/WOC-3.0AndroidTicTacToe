package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Users extends AppCompatActivity {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    TextView textviewofimageurlfromfirestore;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        storageReference = FirebaseStorage.getInstance().getReference();
            mFirestoreList = findViewById(R.id.firestore_list);
            firebaseFirestore = FirebaseFirestore.getInstance();
            textviewofimageurlfromfirestore = findViewById(R.id.uidtextbox);

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
                holder.list_uid.setText(model.getUserUid());






               if( holder.list_uid.getText().toString().isEmpty()){return;}
               else {StorageReference profileref = storageReference.child("users/"+ holder.list_uid.getText().toString()+"profile.jpg");
                profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(holder.list_image);

                    }
                });}

               holder.list_image.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent i = new Intent(v.getContext(),Users_Blogs.class);
                       i.putExtra("useridforuserblogs",model.getUserUid());
                       v.getContext().startActivity(i);
                       Toast.makeText(Users.this, "User "+holder.list_name.getText().toString(), Toast.LENGTH_SHORT).show();

                   }
               });

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
        private TextView list_uid;
        private ImageView list_image;



        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.nametextformfirestore);
            list_email= itemView.findViewById(R.id.emailtextformfirestore);
            list_mobile = itemView.findViewById(R.id.mobiletextformfirestore);
            list_uid = itemView.findViewById(R.id.uidtextbox);
            list_image = itemView.findViewById(R.id.firebaseprofileimage);

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