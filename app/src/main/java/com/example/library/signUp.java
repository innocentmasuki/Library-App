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

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

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
                final String username;
                final String email;
                final String regNo;
                final String confirmed;
                final String newpwd;
                String pwd = null;
                username = fullName.getText().toString();
                regNo = regNumber.getText().toString();
                email = userMail.getText().toString();
                newpwd = newPassword.getText().toString();
                confirmed = newPassword.getText().toString();

                if(newpwd.equals(confirmed)) pwd = confirmed;


                if(!username.equals("") && !regNo.equals("") && !email.equals("") && !newpwd.equals("") && !confirmed.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressbar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    final String finalPwd = pwd;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "regnumber";
                            field[2] = "email";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = regNo;
                            data[2] = email;
                            data[3] = finalPwd;
                            PutData putData = new PutData("http://192.168.137.1/loginRegister/signup.php?_ijt=g017u4ohengs4672n57f2872nq", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressbar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(signUp.this, LogIn.class));
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All Fields Must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(signUp.this);

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