package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;

public class librarian_account extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_account);

        navigationView = (NavigationView) findViewById(R.id.userAccountMenu);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.addBooks){
            Toast.makeText(this, "adding books...", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.requests){
            Toast.makeText(this, "check requests", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.editProfile){
            Toast.makeText(this, "editProfile", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.logOut){
            startActivity(new Intent(librarian_account.this, signUp.class));
            finish();
        }
        return false;
    }
}