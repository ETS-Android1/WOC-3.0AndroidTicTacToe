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
    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,recordmymove,loadmove;
    private TextView top;
    //TextView sendertextid,receivertextid;
    String mysymbol;
    String opponentsymbol;
    int filledposition[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);

        Intent data = getIntent();
        senderid = data.getStringExtra("senderid");
        receiverid = data.getStringExtra("receiverid");
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
       /* sendertextid = findViewById(R.id.senderidtext);
        receivertextid = findViewById(R.id.receiveridtext);*/

        if (senderid.equals(userID)) {
            mysymbol = "X";//0
            opponentsymbol = "O";//1
        }
        else {
            mysymbol = "0";//1
            opponentsymbol = "X";//0
        }




    /*    DocumentReference docref = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                senderid = value.getString("Senderid");
                receiverid = value.getString("Receiverid");
                sendertextid.setText(value.getString("Senderid"));
                receivertextid.setText(value.getString("Receiverid"));
            }
        });
*/



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
        recordmymove = findViewById(R.id.recordmymove);
        loadmove = findViewById(R.id.loadmove);


        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);


        loadmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettagfromfirestore();
            }
        });

        recordmymove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> tag = new HashMap<>();
                tag.put("tag0",filledposition[0]);
                tag.put("tag1",filledposition[1]);
                tag.put("tag2",filledposition[2]);
                tag.put("tag3",filledposition[3]);
                tag.put("tag4",filledposition[4]);
                tag.put("tag5",filledposition[5]);
                tag.put("tag6",filledposition[6]);
                tag.put("tag7",filledposition[7]);
                tag.put("tag8",filledposition[8]);
                fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1")
                        .update(tag).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PlayOnline.this, "Your move recorded", Toast.LENGTH_SHORT).show();
                    }
                });

                gettagfromfirestore();


                fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1")
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String officiallyconfirmeduser = value.getString("currentuser");

                                if (officiallyconfirmeduser.equals(userID)){
                                    if (userID.equals(senderid)){ updatecurrentuser(receiverid); }
                                    else { updatecurrentuser(senderid); }

                                }

                            }
                        });

            }
        });



    }

    private void gettagfromfirestore() {
        DocumentReference docref =  fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                int filled0,filled1,filled2,filled3,filled4,filled5,filled6,filled7,filled8;
                filled0 = value.getLong("tag0").intValue();
                filled1 = value.getLong("tag1").intValue();
                filled2 = value.getLong("tag2").intValue();
                filled3 = value.getLong("tag3").intValue();
                filled4 = value.getLong("tag4").intValue();
                filled5 = value.getLong("tag5").intValue();
                filled6 = value.getLong("tag6").intValue();
                filled7 = value.getLong("tag7").intValue();
                filled8 = value.getLong("tag8").intValue();

                if (filled0 == 1){but1.setText("O");}
                if (filled1== 1){but1.setText("O");}
                if (filled2== 1){but1.setText("O");}
                if (filled3== 1){but1.setText("O");}
                if (filled4== 1){but1.setText("O");}
                if (filled5== 1){but1.setText("O");}
                if (filled6== 1){but1.setText("O");}
                if (filled7== 1){but1.setText("O");}
                if (filled8== 1){but1.setText("O");}

                if (filled0==0){but1.setText("X");}
                if (filled1==0){but1.setText("X");}
                if (filled2==0){but1.setText("X");}
                if (filled3==0){but1.setText("X");}
                if (filled4==0){but1.setText("X");}
                if (filled5==0){but1.setText("X");}
                if (filled6==0){but1.setText("X");}
                if (filled7==0){but1.setText("X");}
                if (filled8==0){but1.setText("X");}

                if (filled0==-1){but1.setText("");}
                if (filled1==-1){but1.setText("");}
                if (filled2==-1){but1.setText("");}
                if (filled3==-1){but1.setText("");}
                if (filled4==-1){but1.setText("");}
                if (filled5==-1){but1.setText("");}
                if (filled6==-1){but1.setText("");}
                if (filled7==-1){but1.setText("");}
                if (filled8==-1){but1.setText("");}
            }
        });
    }


    @Override
    public void onClick(View v) {

        Button clickedbutton = findViewById(v.getId());
        int clickedtag = Integer.parseInt((v.getTag().toString()));

        fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String officiallyconfirmeduser = value.getString("currentuser");
                        if (officiallyconfirmeduser.equals(userID)){
                            if (userID.equals(senderid)){
                                clickedbutton.setText(mysymbol);
                                filledposition[clickedtag] = 0;
                                //updatecurrentuser(receiverid);

                            }
                            else {clickedbutton.setText(mysymbol);
                                filledposition[clickedtag] = 1;
                               // updatecurrentuser(senderid);
                            }

                        }
                    }
                });


/*
       Button clickedbutton = findViewById(v.getId());
       DocumentReference docref = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String officiallyconfirmeduser = value.getString("currentuser");
            }
        });

        getcurrentuser();



                if (top.getText().toString().equals(senderid)){
                    clickedbutton.setText("O");

                    updatecurrentuser(receiverid);

                    getcurrentuser();


                    DocumentReference doccref2 = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
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
                    });

                }
                else {
                    clickedbutton.setText("X");

                    updatecurrentuser(senderid);

                    getcurrentuser();
                    DocumentReference docref3 = fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1");
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
                    });
                }




*/

    }

   /* private void getcurrentuser() {
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
    }*/

   /* private void updatecurrentuser(String id) {
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
    }*/

    public void updatecurrentuser(String id){
        Map<String,Object> user = new HashMap<>();
        user.put("currentuser",id);
        fstore.collection("play").document("1AKTiVTlm7NTBUbtoR5yhPmcBET2-95CeM66Z2EgrEiUa9i6XLB5RzLg1")
                .update(user);

    }


}