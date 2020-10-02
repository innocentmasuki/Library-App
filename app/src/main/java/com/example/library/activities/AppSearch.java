package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.library.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_search);
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
        bottomNavigationView.setSelectedItemId(R.id.appSearch);

        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.appHome:
                        Intent home = new Intent(getApplicationContext(), AppHome.class);
                        home.putExtra("Mail",logged);
                        home.putExtra("ROLE",role);
                        startActivity(home);
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.favBooks:
                        Intent fav = new Intent(getApplicationContext(), FavBooks.class);
                        fav.putExtra("Mail",logged);
                        fav.putExtra("ROLE",role);
                        startActivity(fav);
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
                    case R.id.appSearch:
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