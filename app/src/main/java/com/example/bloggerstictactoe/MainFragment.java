package com.example.bloggerstictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainFragment extends Fragment {
    private onFragmentBtnSelected listner;
    private  onFragmentPhotochange listner2;

    TextView name,email,number;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;



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

        name = view.findViewById(R.id.fullnameshow);
        email = view.findViewById(R.id.showemailid);
        number = view.findViewById(R.id.phonenumbershow);

        fAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);

       /* documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                number.setText(value.getString("mobile"));
                name.setText(value.getString("Name"));
                email.setText(value.getString("email"));

            }
        })*/

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
