package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothServerSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {
    TextView lemail;
    Button logInBtn;
    TextView lpass;
    ProgressBar progressBar;
//    String logIn_url = "http://192.168.43.225/library/login.php";
    String logIn_url = "http://192.168.137.1/library/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        lemail = (TextView) findViewById(R.id.luserMail);
        lpass = (TextView) findViewById(R.id.luserPassword);
        progressBar = (ProgressBar) findViewById(R.id.lprogressBar);

        logInBtn = findViewById(R.id.mainlogInBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        }); 
    }

    private void getData() {


        final String  email, password;
        final String fullname =  getIntent().getStringExtra("fullname");

        email = lemail.getText().toString();
        password = lpass.getText().toString();

        if(!email.equals("") || !password.equals(""))
        {
            StringRequest request = new StringRequest(Request.Method.POST, logIn_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Welcome")){
                                progressBar.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(LogIn.this, AppHome.class);
                                intent.putExtra("Mail",email);
                                intent.putExtra("fullname",fullname);
                                startActivity(intent);
                                finish();

                            }else if(response.equals("Incorrect Password or Mail")){
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LogIn.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email",email);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
            requestQueue.add(request);

        }else {
            Toast.makeText(LogIn.this, "All Blanks must be filled", Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed(){
        startActivity(new Intent(LogIn.this, signUp.class));
        finish();
    }


}