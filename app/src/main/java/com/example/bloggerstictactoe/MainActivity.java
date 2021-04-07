package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainFragment.onFragmentBtnSelected,Fragment_local.onFragmentLocalHumanselected,MainFragment.onFragmentPhotochange,Fragment_local.onFragmentLocalComselected{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToogle;
    Toolbar toolbar;
    //ImageView profileImage;

    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    StorageReference storageReference;

    FirebaseAuth auth;

    ImageView drawerphoto;

    public Uri profileImageuri;

    FirebaseAuth Auth;
    FirebaseFirestore fstore;
    String userID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.blogtitle);
        setSupportActionBar(toolbar);


        storageReference = FirebaseStorage.getInstance().getReference();

        auth = FirebaseAuth.getInstance();

      //  profileImage= findViewById(R.id.profilephoto);


        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        fstore = FirebaseFirestore.getInstance();





        actionBarDrawerToogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToogle);
        actionBarDrawerToogle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToogle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();

        drawerphoto = findViewById(R.id.drawerphoto);

        Auth = FirebaseAuth.getInstance();
       // userID = Auth.getCurrentUser().getUid();







     /*   fstore.collection("blogs"+userID).whereEqualTo("approve","false").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String docid = documentSnapshot.getId();
                NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("New Collaboration Request").setContentText("hi");

               Intent intent = new Intent(MainActivity.this,MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());
            }
        });
*/


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() ==R.id.local){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_local());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.play){
            startActivity(new Intent(getApplicationContext(),Play.class));
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_play());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.blogs){
            startActivity(new Intent(getApplicationContext(),Blogs.class));
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_blogs());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.users){
            Toast.makeText(this, "Click on User profile photo to see his Blogs", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Users.class));

           fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_users());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.friends){
            startActivity(new Intent(getApplicationContext(),Friends.class));

        }
        if (item.getItemId() ==R.id.tournament){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_tournament());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.contactus){
           startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:pranaypandeyofficial@gmail.com")));


        }



        return true;
    }

    @Override
    public void onButtonSelected() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish(); 

    }

    @Override
    public void OnHumanselected() {
        startActivity(new Intent(getApplicationContext(),LocalplayHuman.class));

    }

    @Override
    public void onPhotoChangeSelected() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent,1000);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1000){
            if (resultCode==Activity.RESULT_OK){
                Uri imageuri = data.getData();
               // profileImage.setImageURI(imageuri);

                profileImageuri = imageuri;

                uploadImagetoFirebase(imageuri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageuri) {
        StorageReference fileref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"profile.jpg");

       fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(MainActivity.this, "Photo added Successfully! Start the application again to see your profile photo", Toast.LENGTH_SHORT).show();
                     //  Picasso.get().load(uri).into(drawerphoto);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed! try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCompselected() {
        startActivity(new Intent(getApplicationContext(),LocalplayComputer.class));
    }

   /*private void updateuserstat(String state){
        Map<String,Object> status = new HashMap<>();
        status.put("status",state);
        fstore.collection("users").document(userID).update(status); }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!= null){
            updateuserstat("online"); } }

   @Override
    protected void onStop() {
        super.onStop();
        if (auth.getCurrentUser()!= null){
        updateuserstat("offline");} }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        if (auth.getCurrentUser()!= null){
        updateuserstat("offline");} }*/
}