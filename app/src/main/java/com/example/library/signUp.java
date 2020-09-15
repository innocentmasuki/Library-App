package com.example.library;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



public class signUp extends AppCompatActivity {
 Button logInbtn , signUpBtn;
 ProgressBar progressbar;
 EditText fullName, userMail,regNumber,newPassword,confirmedPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newPassword = (EditText) findViewById(R.id.createdPassword);
        confirmedPwd = (EditText) findViewById(R.id.confirmedPassword);
        fullName = (EditText) findViewById(R.id.userName);
        userMail = (EditText) findViewById(R.id.userMail);
        regNumber = (EditText) findViewById(R.id.regNo);
        logInbtn  =  (Button) findViewById(R.id.logInBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);



        openLogin();
    }


    public void openLogin() {

        logInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp.this, LogIn.class));
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp.this, AppHome.class));
                finish();
            }
        });
    }

    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(signUp.this);

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

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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