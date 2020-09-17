package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class LogIn extends AppCompatActivity {
    Button logInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInBtn = findViewById(R.id.mainlogInBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, AppHome.class));
                finish();
            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(LogIn.this, signUp.class));
        finish();
    }


}