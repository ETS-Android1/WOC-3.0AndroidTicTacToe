package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainFragment.onFragmentBtnSelected,Fragment_local.onFragmentLocalHumanselected,MainFragment.onFragmentPhotochange{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToogle;
    Toolbar toolbar;
    ImageView profileImage;

    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        storageReference = FirebaseStorage.getInstance().getReference();

        profileImage= findViewById(R.id.profilephoto);


        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToogle);
        actionBarDrawerToogle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToogle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() ==R.id.local){
            Toast.makeText(this, "Local", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_local());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.play){
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_play());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.blogs){
            Toast.makeText(this, "Blogs", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_blogs());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.users){
            Toast.makeText(this, "Users", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_users());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.friends){
            Toast.makeText(this, "Friends", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_friends());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.tournament){
            Toast.makeText(this, "Tournaments", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_tournament());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.contactus){
            Toast.makeText(this, "Mail us at pranaypandeyoffical@gmail.com", Toast.LENGTH_SHORT).show();

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
                profileImage.setImageURI(imageuri);



                uploadImagetoFirebase(imageuri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageuri) {
        StorageReference fileref = storageReference.child("profile.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Successfully uploaded image", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed! try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}