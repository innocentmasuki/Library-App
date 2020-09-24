package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AppHome extends AppCompatActivity {

    ImageView userAcount;
    String getUserInfo_url = "http://192.168.137.1/library/retrieve_user_info.php";
//    String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";


    String bookInfoUrl = "http://192.168.137.1/library/get_books.php";
    Context context;
    ArrayList<Books> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

         final String logged =  getIntent().getStringExtra("Mail");
         final String fullname =  getIntent().getStringExtra("fullname");


        userAcount = findViewById(R.id.userAccount);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getUserInfo_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                if(jsonObject.getString("Email").equals(logged)){

                                    Picasso.get().load(jsonObject.getString("Imagepath")).into(userAcount);

                                }
                            } catch (JSONException  e) {
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
        RequestQueue requestQueue = Volley.newRequestQueue(AppHome.this);
        requestQueue.add(jsonArrayRequest);





        userAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppHome.this, librarian_account.class);
                intent.putExtra("Mail",logged);
                intent.putExtra("fullname",fullname);
                startActivity(intent);
                finish();
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




