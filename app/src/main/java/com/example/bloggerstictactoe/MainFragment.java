package com.example.bloggerstictactoe;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainFragment extends Fragment {
    private onFragmentBtnSelected listner;
    private  onFragmentPhotochange listner2;

    TextView fname,femail,fmobile;
    FirebaseAuth Auth;
    FirebaseFirestore fstore;
    String userID;

    ImageView profileImage;

    StorageReference storageReference;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button logout = view.findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onButtonSelected();
            }
        });

        Button changeprofilephoto = view.findViewById(R.id.changephoto);
        changeprofilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner2.onPhotoChangeSelected();
            }
        });


        fname = view.findViewById(R.id.fstorename);
        fmobile = view.findViewById(R.id.fstoremobile);
        femail = view.findViewById(R.id.fstoreemail);

        Auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userID = Auth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname.setText(value.getString("Name"));
                femail.setText(value.getString("email"));
                fmobile.setText(value.getString("mobile"));
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();



        profileImage= view.findViewById(R.id.profilephoto);


        StorageReference profileRef = storageReference.child("users/"+Auth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });





        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof  onFragmentBtnSelected){
            listner = (onFragmentBtnSelected) context;
        }else { throw new ClassCastException(context.toString() + "must implement listner");
        }

        if(context instanceof  onFragmentPhotochange){
            listner2 =(onFragmentPhotochange) context;
        }else {throw new ClassCastException(context.toString()+ " must implement listner");
        }

    }

    public interface onFragmentBtnSelected{
        public void onButtonSelected();
    }



    public interface  onFragmentPhotochange{
        public void onPhotoChangeSelected();
    }


}
