package com.example.library.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox rememberMe;
    TextView signUp;
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
        signUp = findViewById(R.id.lsignUpBtn);
        rememberMe = findViewById(R.id.rememberMe);


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        String mail = preferences.getString("email", "");
        String pass = preferences.getString("password", "");
        lemail.setText(mail);
        lpass.setText(pass);

        if(checkbox.equals("true")){
            getData();
        }



        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                    String email = lemail.getText().toString();
                    String password = lpass.getText().toString();
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();
                }
            }
        });

        logInBtn = findViewById(R.id.mainlogInBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, signUp.class));
                finish();
            }
        });



    }



    private void getData() {


        final String  email, password;

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
                                checkUserRole(email);


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

    private void checkUserRole(final String email) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,   getUserInfo_url, null,
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
                                    startActivity(intent);
                                    finish();
                                }else if (jsonObject.getString("Email").equals(email) && jsonObject.getString("Category").equals("")){
                                    Intent intent = new Intent(LogIn.this, AppHome.class);
                                    intent.putExtra("Mail",email);
                                    intent.putExtra("ROLE","user");
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

    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);

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
            public void onClick(DialogInterface dialog, int which) {
                // When the user click yes button
                // then app will close
                finish();
            }
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
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