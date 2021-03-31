package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.Map;

public class PlayOnline extends AppCompatActivity implements View.OnClickListener {

   private String senderid,receiverid;
   private FirebaseFirestore fstore;
   private FirebaseAuth auth;
    String userID;
   private String officiallyconfirmedcurrentuser;
    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,reset;
    private TextView top;
    TextView sendertextid,receivertextid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);

        /*Intent data = getIntent();
        senderid = data.getStringExtra("senderid");
        receiverid = data.getStringExtra("receiverid");*/
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        sendertextid = findViewById(R.id.senderidtext);
        receivertextid = findViewById(R.id.receiveridtext);


        DocumentReference docref = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                senderid = value.getString("Senderid");
                receiverid = value.getString("Receiverid");
                sendertextid.setText(value.getString("Senderid"));
                receivertextid.setText(value.getString("Receiverid"));
            }
        });




        but1 = findViewById(R.id.but1);
        but2 = findViewById(R.id.but2);
        but3 = findViewById(R.id.but3);
        but4 = findViewById(R.id.but4);
        but5 = findViewById(R.id.but5);
        but6 = findViewById(R.id.but6);
        but7 = findViewById(R.id.but7);
        but8 = findViewById(R.id.but8);
        but9 = findViewById(R.id.but9);
        top = findViewById(R.id.top);


        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);








    }



    @Override
    public void onClick(View v) {


       Button clickedbutton = findViewById(v.getId());
      /*  DocumentReference docref = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String officiallyconfirmeduser = value.getString("currentuser");
            }
        });*/

        getcurrentuser();



                if (top.getText().toString().equals(senderid)){
                    clickedbutton.setText("O");

                    updatecurrentuser(receiverid);

                    getcurrentuser();


                    /*DocumentReference doccref2 = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
                    doccref2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            Map<String,Object> updatingcurrentuser = new HashMap<>();
                            updatingcurrentuser.put("currentuser",receiverid);
                            doccref2.update(updatingcurrentuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(PlayOnline.this, "your turn recorder", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PlayOnline.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });*/

                }
                else {
                    clickedbutton.setText("X");

                    updatecurrentuser(senderid);

                    getcurrentuser();
                  /*  DocumentReference docref3 = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
                    docref3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            Map<String,Object> updatingcurrentuser2 = new HashMap<>();
                            updatingcurrentuser2.put("currentuser",senderid);
                            docref3.update(updatingcurrentuser2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(PlayOnline.this, "your turn recorded 2 ", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PlayOnline.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });*/
                }






    }

    private void getcurrentuser() {
        DocumentReference document = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                officiallyconfirmedcurrentuser = documentSnapshot.getString("currentuser");
                top.setText(documentSnapshot.getString("currentuser"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PlayOnline.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatecurrentuser(String id) {
        DocumentReference docref = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Map<String,Object> currentuser = new HashMap<>();
                currentuser.put("currentuser",id);
                docref.update(currentuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PlayOnline.this, "user updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlayOnline.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}