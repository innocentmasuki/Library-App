package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppHome extends AppCompatActivity {
    ImageView userAcount;
    com.example.library.Books recentBook, popularBook, novelBook, programmingBook;
        public  static final String COVER_URL = "coverUrl";
        public  static final String TITLE = "title";
        public  static final String AUTHOR = "author";
//        public  static final String LOGGED = "logged";
        public  static final String ISBN = "isbn";
        public  static final String UPLOADED_BY = "uploadedBy";
        public  static final String AVAILABLE = "available";
        public  static final String CATEGORY = "category";
        public  static final String DESCRIPTIONS = "descriptions";
        public  static final String REQUESTS = "requests";


//    String bookInfoUrl = "http://192.168.137.1/library/get_books.php";
//        String getUserInfo_url = "http://192.168.137.1/library/retrieve_user_info.php";


//these are the URLs for the APIs
    String bookInfoUrl = "http://192.168.43.225/library/get_books.php";
    String get_popular_booksUrl = "http://192.168.43.225/library/get_popular_books.php";
    String get_programming_booksUrl = "http://192.168.43.225/library/get_programing_books.php";
    String get_electronics_booksUrl = "http://192.168.43.225/library/get_electronics_books.php";
    String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";


    private BooksAdapter myBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

         final String role =  getIntent().getStringExtra("ROLE");
         final String logged =  getIntent().getStringExtra("Mail");




//these are recycler views
        RecyclerView recentRecyclerView = (RecyclerView) findViewById(R.id.recentRecyclerView);
        RecyclerView popularRecyclerView = (RecyclerView)  findViewById(R.id.popularRecyclerView);
        RecyclerView programmingRecyclerView = (RecyclerView)  findViewById(R.id.programmingRecyclerView);
        RecyclerView novelRecyclerView = (RecyclerView)  findViewById(R.id.novelRecyclerView);

        recentRecyclerView.setHasFixedSize(false);
        popularRecyclerView.setHasFixedSize(false);
        programmingRecyclerView.setHasFixedSize(false);
        novelRecyclerView.setHasFixedSize(false);

        recentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
        programmingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
        novelRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));


        //these are the array lists
        ArrayList<com.example.library.Books> recentBookList = new ArrayList<>();
        ArrayList<com.example.library.Books> popularBookList = new ArrayList<>();
        ArrayList<com.example.library.Books> programmingBookList = new ArrayList<>();
        ArrayList<com.example.library.Books> novelBookList = new ArrayList<>();


        //I called the function parseJSON I created below
         parseJSON(bookInfoUrl,recentRecyclerView, recentBookList);
         parseJSON(get_programming_booksUrl,programmingRecyclerView, programmingBookList);
         parseJSON(get_electronics_booksUrl,novelRecyclerView, novelBookList);
         parseJSON(get_popular_booksUrl,popularRecyclerView, popularBookList);


         // creating the user accout section
        userAcount = findViewById(R.id.userAccount);
        getUserImage();
        userAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logged =  getIntent().getStringExtra("Mail");
                Intent intent = new Intent(AppHome.this, librarian_account.class);
                intent.putExtra("Mail",logged);
                intent.putExtra("ROLE",role);
                startActivity(intent);
                finish();
            }
        });

        //these are for the bottom navigation
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
        bottomNavigationView.setSelectedItemId(R.id.appHome);
        //perform itemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                final String logged =  getIntent().getStringExtra("Mail");
                String role =  getIntent().getStringExtra("ROLE");



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
                    case R.id.appNotification:
                        Intent notification = new Intent(getApplicationContext(), AppNotification.class);
                        notification.putExtra("Mail",logged);
                        notification.putExtra("ROLE",role);
                        startActivity(notification);
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
        RequestQueue requestQueue = Volley.newRequestQueue(AppHome.this);
        requestQueue.add(jsonArrayRequest);
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
                                myBooksAdapter = new BooksAdapter(AppHome.this, bookList, logged, role);
                                recyclerView.setAdapter(myBooksAdapter);
                                myBooksAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (JSONException  e) {
                                e.printStackTrace();
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





}




