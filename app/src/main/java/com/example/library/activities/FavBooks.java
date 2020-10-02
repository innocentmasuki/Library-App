package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.library.R;
import com.example.library.activities.AppHome;
import com.example.library.activities.AppNotification;
import com.example.library.activities.AppSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_books);

        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        assert role != null;
        if(role.equals("Admin")){
            bottomNavigationView.setVisibility(View.GONE);

        }else if(role.equals("user")){
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.favBooks).setVisible(false);

        }

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.favBooks);

        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.appSearch:
                        Intent search = new Intent(getApplicationContext(), AppSearch.class);
                        search.putExtra("Mail",logged);
                        search.putExtra("ROLE",role);
                        startActivity(search);
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appHome:
                        Intent home = new Intent(getApplicationContext(), AppHome.class);
                        home.putExtra("Mail",logged);
                        home.putExtra("ROLE",role);
                        startActivity(home);
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appNotification:
                        Intent notification = new Intent(getApplicationContext(), AppNotification.class);
                        notification.putExtra("Mail",logged);
                        notification.putExtra("ROLE",role);
                        startActivity(notification);
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
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(getApplicationContext(), AppHome.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        overridePendingTransition(1,1);
        finish();
    }
}