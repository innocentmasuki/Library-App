package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.Books;
import com.example.library.R;
import com.example.library.adapters.BooksAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppSearch extends AppCompatActivity {
    private BooksAdapter myBooksAdapter;
    String get_All_booksUrl = "http://192.168.43.225/library/get_all_books.php";
    RecyclerView allRecyclerView;
    ArrayList<com.example.library.Books> allBookList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_search);
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        EditText searchbar = findViewById(R.id.search_bar);

         allRecyclerView = findViewById(R.id.allbookrecycler);
        allRecyclerView.setHasFixedSize(true);
        allBookList = new ArrayList<>();

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
               filter(s.toString());
            }
        });


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            allRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }else{
            searchbar.setText(null);
            allRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        }




        parseJSON(get_All_booksUrl,allRecyclerView, allBookList);





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

    public void filter(String text) {
        ArrayList<com.example.library.Books> filteredList = new ArrayList<>();
        for (com.example.library.Books item : allBookList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }else if(item.getAuthor().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }else if(item.getIsbn().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }else if(item.getCategory().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        myBooksAdapter.filterList(filteredList);
    }


    private void parseJSON(String url, final RecyclerView recyclerView, final ArrayList<Books> bookList) {
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        bookList.clear();
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                String Title = jsonObject.getString("Title");
                                String Cover = jsonObject.getString("Cover");
                                String Author = jsonObject.getString("Author");
                                String isbn = jsonObject.getString("isbn");
                                String remaining = jsonObject.getString("Remaining");
                                String category = jsonObject.getString("Category");
                                String descripts = jsonObject.getString("Description");
                                String uploadedby  = jsonObject.getString("Uploadedby");
                                String requests  = jsonObject.getString("Requests");
                                bookList.add(new Books(Title, Author,Cover, isbn, category, uploadedby, remaining, descripts, requests));
                                myBooksAdapter = new BooksAdapter(AppSearch.this, bookList, logged, role);
                                recyclerView.setAdapter(myBooksAdapter);
                                myBooksAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(AppSearch.this);
        requestQueue.add(jsonArrayRequest);

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