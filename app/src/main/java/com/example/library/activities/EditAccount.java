package com.example.library.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.R;

import java.util.HashMap;
import java.util.Map;

public class EditAccount extends AppCompatActivity {


    Button deleteAccount, saveName, savePwd;
    TextView changedName, newChPassword, conChPassword;

    String update_username_url = "http://192.168.43.225/library/update_userName.php";
    String update_password_url = "http://192.168.43.225/library/update_password.php";
    String deleteAccount_url = "http://192.168.43.225/library/deleteAccount.php";

//    String update_username_url = "http://192.168.137.1/library/update_userName.php";
//    String update_password_url = "http://192.168.137.1/library/update_password.php";
//    String deleteAccount_url = "http://192.168.137.1/library/deleteAccount.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        deleteAccount =  findViewById(R.id.deleteAccount);
        saveName =  findViewById(R.id.saveNewName);
        savePwd =  findViewById(R.id.saveNewPwd);
        changedName =  findViewById(R.id.changedUName);



        savePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeName();

            }
        });


    }

    public void changePassword() {
        newChPassword =  findViewById(R.id.changeNewpassword);
        conChPassword =  findViewById(R.id.changeConfirmUpassword);
        final String newPass, conPass;
        newPass = newChPassword.getText().toString();
        conPass = conChPassword.getText().toString();


        final String logged =  getIntent().getStringExtra("Mail");

        if (!newPass.equals("") || !conPass.equals("")){
            if(!(newPass.length() < 8) || !(conPass.length() < 8)){
                if(newPass.equals(conPass)){
                    StringRequest request = new StringRequest(Request.Method.POST, update_password_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Succesfully update")){
                                        newChPassword.setText("");
                                        conChPassword.setText("");
                                        Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();

                                    }else if(response.equals("Try again Later ...")){
                                        Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", logged);
                            params.put("password",conPass);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(EditAccount.this);
                    requestQueue.add(request);

                    newChPassword.setText("");
                    conChPassword.setText("");
                    Toast.makeText(EditAccount.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                }

                else{
                    newChPassword.setText("");
                    conChPassword.setText("");
                    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Password must be min 8 characters", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeName() {
        final String logged =  getIntent().getStringExtra("Mail");
        final String userName = changedName.getText().toString();

        String namePattern = "(?=^.{0,50}$)^[a-zA-Z-]+\\s[a-zA-Z-]+$";
        if(userName.matches(namePattern)){
            StringRequest request = new StringRequest(Request.Method.POST, update_username_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Name changed Successfully")){
                                changedName.setText("");

                            }else if(response.equals("Can't change Try again Later ...")){
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", logged);
                    params.put("fullname",userName);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(EditAccount.this);
            requestQueue.add(request);
            changedName.setText("");
            Toast.makeText(EditAccount.this, "Name changed successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Enter a valid Full Name", Toast.LENGTH_SHORT).show();
        }


    }


    public void deleteDialog() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(EditAccount.this);

        // Set the message show for the Alert time
        builder.setMessage("This action can not be undone");

        // Set Alert Title
        builder.setTitle("⚠ Delete Account");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                deleleUserInfo();
                                Toast.makeText(EditAccount.this, "Account Deleted, Create New", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditAccount.this, signUp.class));
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
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleleUserInfo() {
        final String logged =  getIntent().getStringExtra("Mail");
        StringRequest request = new StringRequest(Request.Method.POST, deleteAccount_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", logged);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditAccount.this);
        requestQueue.add(request);

    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(EditAccount.this, librarian_account.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        finish();
    }


}

