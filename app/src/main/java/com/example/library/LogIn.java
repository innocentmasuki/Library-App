package com.example.library;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {
    Button logInBtn;
    TextView lemail, lpass;
    String logIn_url = "http://192.168.137.1/library/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        lemail = (TextView) findViewById(R.id.luserMail);
        lpass = (TextView) findViewById(R.id.luserPassword);

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

        email = lemail.getText().toString();
        password = lpass.getText().toString();

        if(!email.equals("") || !password.equals("")){
            StringRequest request = new StringRequest(Request.Method.POST, logIn_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            lemail.setText("");
                            lpass.setText("");

                            if(response.equals("Welcome \uD83E\uDD17")){
                                Toast.makeText(LogIn.this, response, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LogIn.this, AppHome.class));
                                finish();
                            }else if(response.equals("Incorrect Password or Mail")){
                                Toast.makeText(LogIn.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_LONG).show();
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