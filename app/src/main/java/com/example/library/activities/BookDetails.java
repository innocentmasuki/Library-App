package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.BlurTransformation;

import static com.example.library.activities.AppHome.AUTHOR;
import static com.example.library.activities.AppHome.AVAILABLE;
import static com.example.library.activities.AppHome.CATEGORY;
import static com.example.library.activities.AppHome.COVER_URL;
import static com.example.library.activities.AppHome.DESCRIPTIONS;
import static com.example.library.activities.AppHome.ISBN;
import static com.example.library.activities.AppHome.REQUESTS;
import static com.example.library.activities.AppHome.TITLE;
import static com.example.library.activities.AppHome.UPLOADED_BY;

public class BookDetails extends AppCompatActivity {
ImageView background_image, bookCover;
ToggleButton borrowBtn;
TextView bookTitle, bookAuthor, bookISBN, bookCategory, uploadedBy, booksAvailable, descriptions;

    String validate_url = "http://192.168.43.225/library/validate_request.php";
    String addNotificationurl = "http://192.168.43.225/library/add_notification.php";

    String addRequestUrl = "http://192.168.43.225/library/addRequest.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();
        String coverUrl = intent.getStringExtra(COVER_URL);
        String title = intent.getStringExtra(TITLE);
        String author = intent.getStringExtra(AUTHOR);
        String description = intent.getStringExtra(DESCRIPTIONS);
        final String remaining = intent.getStringExtra(AVAILABLE);
        String category = intent.getStringExtra(CATEGORY);
        String isbn = intent.getStringExtra(ISBN);
        String uploader = intent.getStringExtra(UPLOADED_BY);
        final String reqs = intent.getStringExtra(REQUESTS);
        String logged = intent.getStringExtra("Mail");

        background_image = findViewById(R.id.bookCbackground);
        borrowBtn = findViewById(R.id.toggleBtn);
        bookTitle = findViewById(R.id.dTitle);
        bookAuthor = findViewById(R.id.dAuthor);
        bookCover = findViewById(R.id.bookC);
        bookISBN = findViewById(R.id.isbnBook);
        bookCategory = findViewById(R.id.bookCategory);
        booksAvailable = findViewById(R.id.availableBooks);
        uploadedBy = findViewById(R.id.uploadedBy);
        descriptions = findViewById(R.id.details);

        Picasso.get().load(coverUrl).fit().into(bookCover);
        Picasso.get().load(coverUrl).fit().transform(new BlurTransformation(this, 50, 1)).into(background_image);
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookISBN.setText("ISBN: " + isbn);
        bookCategory.setText(category);
        booksAvailable.setText(remaining + " books Available");
        uploadedBy.setText("Uploaded by " + uploader);
        descriptions.setText(description);


        assert remaining != null;

        if(Integer.parseInt(remaining) <= 0){
            borrowBtn.setVisibility(View.GONE);
        }else {
            borrowBtn.setVisibility(View.VISIBLE);
        }




        borrowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(borrowBtn.isChecked()){
                    borrowBtn.setTextOn("Request");
                        validate();
                }else{
                    borrowBtn.setTextOff("Request");
                }
            }
        });
    }




    public  void validate(){
            StringRequest validate = new StringRequest(Request.Method.POST, validate_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Can't create request")){
                                Toast.makeText(BookDetails.this, response, Toast.LENGTH_SHORT).show();
                            }else if(response.equals("dont Exist")){
                                Intent intent = getIntent();
                                final String reqs = intent.getStringExtra(REQUESTS);
                                assert reqs != null;
                                int request = Integer.parseInt(reqs);
                                int r = request + 1;
                                String R = Integer.toString(r);

                                addRequest(R);
                                sendData();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    Intent intent = getIntent();
                    String logged = intent.getStringExtra("Mail");
                    params.put("email",logged);
                    params.put("title",bookTitle.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
            requestQueue.add(validate);

        }

    private void addRequest(final String request) {
        StringRequest validate = new StringRequest(Request.Method.POST, addRequestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error requesting")){
                            Toast.makeText(BookDetails.this, response, Toast.LENGTH_SHORT).show();
                        }else if(response.equals("Requested")){
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                String isbn = intent.getStringExtra(ISBN);
                Map<String, String> params = new HashMap<String, String>();
                params.put("isbn",isbn);
                params.put("requests",request);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
        requestQueue.add(validate);
    }

    public void sendData(){

            StringRequest addNotification = new StringRequest(Request.Method.POST, addNotificationurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("notificationAdded")){
                                borrowBtn.setTextOn("Requested");

                                Toast.makeText(BookDetails.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BookDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    Intent intent = getIntent();
                    String coverUrl = intent.getStringExtra(COVER_URL);
                    String title = intent.getStringExtra(TITLE);
                    String author = intent.getStringExtra(AUTHOR);
                    String isbn = intent.getStringExtra(ISBN);
                    String logged = intent.getStringExtra("Mail");
                    String views = "Admin";
                    params.put("cover", coverUrl);
                    params.put("title", title);
                    params.put("author", author);
                    params.put("requestedby", logged);
                    params.put("views", views);
                    params.put("isbn",isbn);
                    params.put("status","Requested");

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
            requestQueue.add(addNotification);
        }



    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(BookDetails.this, AppHome.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        finish();
    }


}