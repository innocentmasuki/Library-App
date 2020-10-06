package com.example.library.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class librarian_account extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    ImageView imageView;
    TextView userMail, fullName;

//    String getUserInfo_url = "http://192.168.137.1/library/retrieve_user_info.php";


    String getUserInfo_url = "http://192.168.43.225/library/retrieve_user_info.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_account);
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
//        final String fullname = (String) getIntent().getStringExtra("fullname");

        imageView =  findViewById(R.id.images);
        userMail =  findViewById(R.id.auseremail);
        fullName =  findViewById(R.id.ausername);

//        final List<Object> object = new ArrayList<Object>();
        final JSONArray jsonArray = new JSONArray();





        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getUserInfo_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for(int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    if(jsonObject.getString("Email").equals(logged)){
                                            userMail.setText(logged);
                                            fullName.setText(jsonObject.getString("Name"));
                                            Picasso.get().load(jsonObject.getString("Imagepath")).into(imageView);
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

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(librarian_account.this);
        requestQueue.add(jsonArrayRequest);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(librarian_account.this, "", Toast.LENGTH_SHORT).show();
            }
        });


        navigationView = findViewById(R.id.userAccountMenu);

        assert role != null;
        if(role.equals("user")){

            navigationView.getMenu().findItem(R.id.addBooks).setVisible(false);

        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        final String logged = (String) getIntent().getStringExtra("Mail");
        final String role = (String) getIntent().getStringExtra("ROLE");


        int id = menuItem.getItemId();
        if (id == R.id.addBooks){
            Intent addbooks = new Intent(librarian_account.this, AddBooks.class);
            addbooks.putExtra("Mail",logged);
            addbooks.putExtra("ROLE",role);
            startActivity(addbooks);
            finish();
        }else if(id == R.id.requests){
            Toast.makeText(this, "check requests", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.editProfile){
            Intent edit = new Intent(librarian_account.this, EditAccount.class);
            edit.putExtra("Mail",logged);
            edit.putExtra("ROLE",role);
            startActivity(edit);
            finish();
        }else if(id == R.id.logOut){
            logOutAlert();
        }
        return false;
    }

    public void logOutAlert() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(librarian_account.this);

        // Set the message show for the Alert time
        builder.setMessage("Want to Logout?");

        // Set Alert Title
        builder.setTitle("📙 Library");

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
                                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember", "false");
                                editor.putString("email", "");
                                editor.putString("password", "");
                                editor.apply();
                                startActivity(new Intent(librarian_account.this, LogIn.class));
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
        String role =  getIntent().getStringExtra("ROLE");
        final String logged =  getIntent().getStringExtra("Mail");

        assert role != null;
        if(role.equals("Admin")){
            Intent intent = new Intent(librarian_account.this, AppNotification.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            startActivity(intent);
            finish();
        }else if(role.equals("user")){
            Intent intent = new Intent(librarian_account.this, AppHome.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            startActivity(intent);
            finish();
        }

    }
}