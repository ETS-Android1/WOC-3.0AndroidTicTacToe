package com.example.bloggerstictactoe;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class Fragment_tournament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tournament,container,false);
        TextView tourname,tourmobile,touremail,tourplayed,tourwins;
        ImageView tourimg;

        tourname = view.findViewById(R.id.tourname);
        tourmobile = view.findViewById(R.id.tourmobile);
        touremail = view.findViewById(R.id.touremail);
        tourplayed = view.findViewById(R.id.tourplayed);
        tourwins = view.findViewById(R.id.tourwins);
        tourimg = view.findViewById(R.id.tourimg);
        FirebaseFirestore.getInstance().collection("users").orderBy("wins", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot =  task.getResult().getDocuments().get(0);
                String documentID = documentSnapshot.getId();
                FirebaseFirestore.getInstance().collection("users").document(documentID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        tourname.setText(value.getString("Name"));
                        tourmobile.setText(value.getString("mobile"));
                        touremail.setText(value.getString("email"));
                        tourplayed.setText(value.getLong("played").toString());
                        tourwins.setText(value.getLong("wins").toString());

                        FirebaseStorage.getInstance().getReference().child("users/"+documentID+"profile.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(tourimg);
                            }
                        });

                    }
                });
            }
        });

        return view;
    }
}
