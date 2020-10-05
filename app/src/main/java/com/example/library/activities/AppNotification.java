package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.Notification;
import com.example.library.R;
import com.example.library.adapters.NotificationAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppNotification extends AppCompatActivity{

    String get_Admin_alert = "http://192.168.43.225/library/get_notification_admin.php";
    String get_User_alert = "http://192.168.43.225/library/get_notification_user.php";
    String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";


    private NotificationAdapter notificationAdapter;
    RecyclerView notificationRecycler;
    ImageView userAcount;
    View userAcct;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_notification);
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");


        userAcount = findViewById(R.id.userAccount);
        userAcct = findViewById(R.id.notificationprofile);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        getUserImage();

        userAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logged =  getIntent().getStringExtra("Mail");
                Intent intent = new Intent(AppNotification.this, librarian_account.class);
                intent.putExtra("Mail",logged);
                intent.putExtra("ROLE",role);
                startActivity(intent);
                finish();
            }
        });

        notificationRecycler = findViewById(R.id.nRecyclerView);
        notificationRecycler.setHasFixedSize(true);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false ));



        final ArrayList<Notification> notificationList = new ArrayList<>();

        assert role != null;
        if(role.equals("Admin")){
            parseJSON(get_Admin_alert,notificationRecycler, notificationList, "Admin");
        }else if(role.equals("user")){
            parseJSON(get_User_alert,notificationRecycler, notificationList, "user");
        }



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(role.equals("Admin")){
                    notificationList.clear();
                    parseJSON(get_Admin_alert,notificationRecycler, notificationList, "Admin");
                }else if(role.equals("user")){
                    notificationList.clear();

                    parseJSON(get_User_alert,notificationRecycler, notificationList, "user");
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });






        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        if(role.equals("Admin")){
            bottomNavigationView.setVisibility(View.GONE);
            userAcct.setVisibility(View.VISIBLE);

        }else if(role.equals("user")){
            bottomNavigationView.setVisibility(View.VISIBLE);
            userAcct.setVisibility(View.INVISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.favBooks).setVisible(false);

        }

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.appNotification);

        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            String role =  getIntent().getStringExtra("ROLE");


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
                    case R.id.favBooks:
                        Intent fav = new Intent(getApplicationContext(), FavBooks.class);
                        fav.putExtra("Mail",logged);
                        fav.putExtra("ROLE",role);
                        startActivity(fav);
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
                        return true;
                }
                return false;
            }
        });



    }



    private void getUserImage() {
        final String logged =  getIntent().getStringExtra("Mail");


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
                        error.printStackTrace();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(AppNotification.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJSON(String url, final RecyclerView recyclerView, final ArrayList<Notification> bookList, final String role) {
        Intent intent = getIntent();
        final String logged = intent.getStringExtra("Mail");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                String Title = jsonObject.getString("Title");
                                String Cover = jsonObject.getString("Cover");
                                String Author = jsonObject.getString("Author");
                                String requestedBy = jsonObject.getString("Requestedby");
                                String nId = jsonObject.getString("Id");
                                String status = jsonObject.getString("Status");
                                String isbn = jsonObject.getString("Isbn");
                                String approvedBy = jsonObject.getString("ApprovedBy");
                                    if (role.equals("user") && requestedBy.equals(logged)){
                                        bookList.add(new Notification(Cover, Title,Author, requestedBy, nId, status, isbn, approvedBy));
                                    }else if(role.equals("Admin")){
                                        bookList.add(new Notification(Cover, Title,Author, requestedBy, nId, status, isbn, approvedBy));
                                    }
                                notificationAdapter = new NotificationAdapter(AppNotification.this, bookList, isbn, role, logged);
                                recyclerView.setAdapter(notificationAdapter);


                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(AppNotification.this);
        requestQueue.add(jsonArrayRequest);

    }


    public void onBackPressed(){
        String role =  getIntent().getStringExtra("ROLE");
        final String logged =  getIntent().getStringExtra("Mail");

        assert role != null;
        if(role.equals("Admin")){
            // Create the object of
            // AlertDialog Builder class
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(AppNotification.this);

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

            builder.setPositiveButton("Yes", new DialogInterface
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
            builder.setNegativeButton("No", new DialogInterface
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

    else if(role.equals("user")){
            Intent intent = new Intent(AppNotification.this, AppHome.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            startActivity(intent);
            finish();
        }

    }
}