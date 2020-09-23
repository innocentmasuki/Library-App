package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddBooks extends AppCompatActivity {

    String addbooks_url = "http://192.168.137.1/library/add_book.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);


    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        Intent intent = new Intent(AddBooks.this, librarian_account.class);
        intent.putExtra("Mail",logged);
        startActivity(intent);
        finish();
    }
}