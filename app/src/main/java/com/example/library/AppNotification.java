package com.example.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppNotification extends AppCompatActivity implements NotificationAdapter.OnItemClickListener{

    private NotificationAdapter notificationAdapter;
    private ArrayList<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_notification);
        final String logged =  getIntent().getStringExtra("Mail");


        RecyclerView notificationRecycler = findViewById(R.id.nRecyclerView);
        notificationRecycler.setHasFixedSize(true);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false ));

        notificationList = new ArrayList<>();







        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.appNotification);

        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.appSearch:
                        Intent search = new Intent(getApplicationContext(), AppSearch.class);
                        search.putExtra("Mail",logged);
                        startActivity(search);
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.favBooks:
                        Intent fav = new Intent(getApplicationContext(), FavBooks.class);
                        fav.putExtra("Mail",logged);
                        startActivity(fav);
                        overridePendingTransition(1,1);
                        finish();
                        return true;
                    case R.id.appHome:
                        Intent home = new Intent(getApplicationContext(), AppHome.class);
                        home.putExtra("Mail",logged);
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

    //creating on Back pressed

    @Override
    public void onBackPressed() {
        final String logged =  getIntent().getStringExtra("Mail");
        Intent intent = new Intent(getApplicationContext(), AppHome.class);
        intent.putExtra("Mail",logged);
        startActivity(intent);
        overridePendingTransition(1,1);
        finish();
    }

    private void parseJSON(String url, final RecyclerView recyclerView, final ArrayList<Books> bookList) {
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
                                String requestedBy = jsonObject.getString("RequestedBy");

                                notificationList.add(new Notification(Cover, Title,Author, requestedBy));
                                notificationAdapter = new NotificationAdapter(AppNotification.this, notificationList);
                                recyclerView.setAdapter(notificationAdapter);
                                notificationAdapter.setOnItemClickListener(AppNotification.this);
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

    @Override
    public void onItemClick(int position) {
        Notification notification;
        notification = notificationList.get(position);

        clickedBook(notification);

    }


    public void clickedBook(@NonNull Notification clickedItem){
        final String logged =  getIntent().getStringExtra("Mail");
        Toast.makeText(this, "Item Clicked", Toast.LENGTH_SHORT).show();
    }
}