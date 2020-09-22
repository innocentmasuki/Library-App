package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppHome extends AppCompatActivity {

    Button userAcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

         final String logged = (String) getIntent().getStringExtra("userMail");
         final String fullname = (String) getIntent().getStringExtra("fullname");

        userAcount = (Button) findViewById(R.id.userAccount);
        userAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppHome.this, librarian_account.class);
                intent.putExtra("Mail",logged);
                intent.putExtra("fullname",fullname);
                startActivity(intent);
            }
        });

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.appHome);

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
                    case R.id.favBooks:
                        startActivity(new Intent(getApplicationContext(), FavBooks.class));
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appNotification:
                        startActivity(new Intent(getApplicationContext(), AppNotification.class));
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appHome:
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(AppHome.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ? 😢");

        // Set Alert Title
        builder.setTitle("📙 Library");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}




