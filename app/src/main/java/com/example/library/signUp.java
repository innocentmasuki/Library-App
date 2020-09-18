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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class signUp extends AppCompatActivity {
 Button logInbtn , signUpBtn;
 EditText fullName;
    EditText userMail;
    RadioGroup category;
    EditText newPassword;
    EditText confirmedPwd;
    RadioButton selectedCat;


//    ArrayList<UserProfile> arrayList= new ArrayList<>();
    RequestQueue requestQueue;


    String validate_url = "http://192.168.137.1/library/validate.php";
    String url = "http://192.168.137.1/library/register.php";
    String userCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newPassword = (EditText) findViewById(R.id.createdPassword);
        confirmedPwd = (EditText) findViewById(R.id.confirmedPassword);
        fullName = (EditText) findViewById(R.id.userName);
        userMail = (EditText) findViewById(R.id.userMail);
        category = (RadioGroup) findViewById(R.id.category);
        logInbtn  =  (Button) findViewById(R.id.logInBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        int selectedId = category.getCheckedRadioButtonId();
        selectedCat = (RadioButton) findViewById(selectedId);


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
                validate();
            }

        });
    }


    public  void validate(){
        final String name, email, newPass, conPass;
        name = fullName.getText().toString();
        email = userMail.getText().toString();
        newPass = newPassword.getText().toString();
        conPass = confirmedPwd.getText().toString();

        if(!name.equals("") || !email.equals("") || !newPass.equals("") || !conPass.equals("")){
            StringRequest validate = new StringRequest(Request.Method.POST, validate_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Can't create account! exixting user")){
                                Toast.makeText(signUp.this, response, Toast.LENGTH_LONG).show();
                            }else if(response.equals("dont Exist")){
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
                    params.put("email",email);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(signUp.this);
            requestQueue.add(validate);

        }else {
            Toast.makeText(signUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData(){

         final String name, email, category, password;
        if(newPassword.getText().toString().equals(confirmedPwd.getText().toString())){
            password = confirmedPwd.getText().toString();
        }else{
            password = "wrong";
        }
        name = fullName.getText().toString();
        email = userMail.getText().toString();
        category = selectedCat.getText().toString();

            if (!password.equals("wrong")){
    StringRequest signUprequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    fullName.setText("");
                    userMail.setText("");
                    newPassword.setText("");
                    confirmedPwd.setText("");
                    if(response.equals("Welcome \uD83E\uDD17")){
                        Toast.makeText(signUp.this, response, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(signUp.this, LogIn.class));
                        finish();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(signUp.this, "Can' create account try again later", Toast.LENGTH_LONG).show();
            error.printStackTrace();
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("fullname",name);
            params.put("email",email);
            params.put("category",category);
            params.put("password",password);
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(signUp.this);
    requestQueue.add(signUprequest);
}else{
    newPassword.setText("");
    newPassword.setText("");
    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
}




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