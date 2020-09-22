package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class librarian_account extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    ImageView imageView;
    TextView userMail, fullName;

    String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_account);
        final String logged = (String) getIntent().getStringExtra("Mail");
        final String fullname = (String) getIntent().getStringExtra("fullname");

        imageView =  findViewById(R.id.images);
        userMail =  findViewById(R.id.auseremail);
        fullName =  findViewById(R.id.ausername);
        userMail.setText(logged);
        fullName.setText(fullname);
        final JSONObject[] userJsonObject = new JSONObject[1];

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getUserInfo_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for(int i = 0; i < response.length(); i++){
                                try {
                                    userJsonObject[0] = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(librarian_account.this, "logged", Toast.LENGTH_SHORT).show();
            }
        });


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
            final String logged = (String) getIntent().getStringExtra("Mail");
            final String passed = (String) getIntent().getStringExtra("userPass");

//            Toast.makeText(this, logged, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(librarian_account.this, EditAccount.class);
            intent.putExtra("userMail",logged);
            startActivity(intent);
        }else if(id == R.id.logOut){
            logOutAlert();
        }
        return false;
    }

    public void logOutAlert() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(librarian_account.this);

        // Set the message show for the Alert time
        builder.setMessage("Want to Logout?");

        // Set Alert Title
        builder.setTitle("📙 Library");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

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
                                startActivity(new Intent(librarian_account.this, signUp.class));
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
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}