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
import android.widget.Toast;

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

public class Users extends AppCompatActivity {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    String userId;
    FirebaseAuth auth;
  // String currentusername;
    String currentusername;

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

            auth = FirebaseAuth.getInstance();



                    
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

                String status = model.getStatus();

                if (status == "online"){
                    holder.statusimage.setVisibility(View.VISIBLE);
                }





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


                       firebaseFirestore.collection("users").document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                           @Override
                           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                               currentusername = value.getString("Name");




                       Intent i = new Intent(v.getContext(),Users_Blogs.class);
                       i.putExtra("useridforuserblogs",model.getUserUid());
                       i.putExtra("name",currentusername);
                       v.getContext().startActivity(i);
                       Toast.makeText(Users.this, "User "+holder.list_name.getText().toString(), Toast.LENGTH_SHORT).show();

                           }
                       });

                   }
               });
               
               holder.buttonaddfriend.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       userId = auth.getCurrentUser().getUid();
                       DocumentReference docref = firebaseFirestore.collection("Friendsof"+userId).document(model.getUserUid());
                       Map<String,Object> friend = new HashMap<>();
                       friend.put("userIDoffriend",model.getUserUid());
                       docref.set(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(Users.this, "Friend added", Toast.LENGTH_SHORT).show();
                           }
                       });
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
        private Button buttonaddfriend;
        private ImageView statusimage;



        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.nametextformfirestore);
            list_email= itemView.findViewById(R.id.emailtextformfirestore);
            list_mobile = itemView.findViewById(R.id.mobiletextformfirestore);
            list_uid = itemView.findViewById(R.id.uidtextbox);
            list_image = itemView.findViewById(R.id.firebaseprofileimage);
            buttonaddfriend = itemView.findViewById(R.id.addfriendinusers);
            statusimage = itemView.findViewById(R.id.statusimage);

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