package com.example.library;

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

import java.util.HashMap;
import java.util.Map;

public class EditAccount extends AppCompatActivity {


    Button deleteAccount, saveName, savePwd;
    TextView changedName, newChPassword, conChPassword;

    String update_username_url = "http://192.168.43.225/library/update_userName.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        deleteAccount = (Button) findViewById(R.id.deleteAccount);
        saveName = (Button) findViewById(R.id.saveNewName);
        savePwd = (Button) findViewById(R.id.saveNewPwd);
        changedName = (TextView) findViewById(R.id.changedUName);
        newChPassword = (TextView) findViewById(R.id.changeNewpassword);
        conChPassword = (TextView) findViewById(R.id.changeConfirmUpassword);

         final String logged = (String) getIntent().getStringExtra("userMail");
        final String userName = changedName.getText().toString();


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditAccount.this, logged, Toast.LENGTH_SHORT).show();
//                StringRequest request = new StringRequest(Request.Method.POST, update_username_url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                if(response.equals("Name changed Successfully")){
//                                    Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();
//
//                                }else if(response.equals("Can't change Try again Later ...")){
//                                    Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EditAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                        error.printStackTrace();
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("email", logged);
//                        params.put("fullname",userName);
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(EditAccount.this);
//                requestQueue.add(request);
            }
        });
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
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
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

    public void onBackPressed(){
        startActivity(new Intent(EditAccount.this, librarian_account.class));
        finish();
    }
}

