package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_books);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.favBooks);

        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.appSearch:
                        startActivity(new Intent(getApplicationContext(), AppSearch.class));
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appHome:
                        startActivity(new Intent(getApplicationContext(), AppHome.class));
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appNotification:
                        startActivity(new Intent(getApplicationContext(), AppNotification.class));
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.favBooks:
                        return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AppHome.class));
        overridePendingTransition(1,1);
        finish();
    }
}