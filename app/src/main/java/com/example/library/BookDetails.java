package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

import static com.example.library.AppHome.AUTHOR;
import static com.example.library.AppHome.AVAILABLE;
import static com.example.library.AppHome.CATEGORY;
import static com.example.library.AppHome.COVER_URL;
import static com.example.library.AppHome.DESCRIPTIONS;
import static com.example.library.AppHome.ISBN;
import static com.example.library.AppHome.TITLE;
import static com.example.library.AppHome.UPLOADED_BY;

public class BookDetails extends AppCompatActivity {
ImageView background_image, bookCover;
ToggleButton borrowBtn;
TextView bookTitle, bookAuthor, bookISBN, bookCategory, uploadedBy, booksAvailable, descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();
        String coverUrl = intent.getStringExtra(COVER_URL);
        String title = intent.getStringExtra(TITLE);
        String author = intent.getStringExtra(AUTHOR);
        String description = intent.getStringExtra(DESCRIPTIONS);
        String remaining = intent.getStringExtra(AVAILABLE);
        String category = intent.getStringExtra(CATEGORY);
        String isbn = intent.getStringExtra(ISBN);
        String uploader = intent.getStringExtra(UPLOADED_BY);
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



        borrowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(borrowBtn.isChecked()){
                        borrowBtn.setTextOn("Requested");
                }else{
                    borrowBtn.setTextOff("Request");
                }
            }
        });
    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        Intent intent = new Intent(BookDetails.this, AppHome.class);
        intent.putExtra("Mail",logged);
        startActivity(intent);
        finish();
    }
}