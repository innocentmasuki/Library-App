package com.example.library.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {
    TextView lemail;
    Button logInBtn;
    TextView lpass;
    ProgressBar progressBar;
    String logIn_url = "http://192.168.43.225/library/login.php";
String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        lemail =  findViewById(R.id.luserMail);
        lpass = findViewById(R.id.luserPassword);
        progressBar = findViewById(R.id.lprogressBar);

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
                                checkUserRole(email, fullname);


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

    private void checkUserRole(final String email, final String fullName) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getUserInfo_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                if(jsonObject.getString("Email").equals(email) && jsonObject.getString("Category").equals("Admin")){
                                    Intent intent = new Intent(LogIn.this, AppNotification.class);
                                    intent.putExtra("Mail",email);
                                    intent.putExtra("ROLE","Admin");
                                    intent.putExtra("fullname",fullName);
                                    startActivity(intent);
                                    finish();
                                }else if (jsonObject.getString("Email").equals(email) && jsonObject.getString("Category").equals("")){
                                    Intent intent = new Intent(LogIn.this, AppHome.class);
                                    intent.putExtra("Mail",email);
                                    intent.putExtra("ROLE","user");
                                    intent.putExtra("fullname",fullName);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
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
        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
        requestQueue.add(jsonArrayRequest);
    }

    public void onBackPressed(){
        startActivity(new Intent(LogIn.this, signUp.class));
        finish();
    }


}