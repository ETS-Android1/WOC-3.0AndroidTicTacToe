package com.example.bloggerstictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainFragment.onFragmentBtnSelected{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToogle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
            Toast.makeText(this, "You clicked Local", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MainFragment());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.play){
            Toast.makeText(this, "You clicked Play", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_play());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.blogs){
            Toast.makeText(this, "You clicked Blogs", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_blogs());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.users){
            Toast.makeText(this, "You clicked Users", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_users());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.friends){
            Toast.makeText(this, "You clicked Friends", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_friends());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.tournament){
            Toast.makeText(this, "You clicked Tournaments", Toast.LENGTH_SHORT).show();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_tournament());
            fragmentTransaction.commit();
        }
        if (item.getItemId() ==R.id.contactus){
            Toast.makeText(this, "You clicked Contact US", Toast.LENGTH_SHORT).show();

        }



        return true;
    }

    @Override
    public void onButtonSelected() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish(); 

    }
}