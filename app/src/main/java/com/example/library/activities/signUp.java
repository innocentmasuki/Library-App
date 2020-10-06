package com.example.library.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class signUp extends AppCompatActivity {
 Button  signUpBtn;
 EditText fullName;
    EditText userMail;
    EditText newPassword;
    EditText confirmedPwd;
ProgressBar progressBar;
ImageView userImage;
ImageView userImage2;
TextView logInbtn;

Uri uri;


//images start here
Bitmap bitmap;

    boolean check = true;

    ProgressDialog progressDialog ;




    String validate_url = "http://192.168.43.225/library/validate.php";
    String Surl = "http://192.168.43.225/library/image_upload.php";
    String Sendurl = "http://192.168.43.225/library/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newPassword = findViewById(R.id.createdPassword);
        confirmedPwd =  findViewById(R.id.confirmedPassword);
        fullName = findViewById(R.id.userName);
        userMail =  findViewById(R.id.userMail);
        logInbtn  =   findViewById(R.id.logInBtn);
        signUpBtn =  findViewById(R.id.signUpBtn);
        progressBar = findViewById(R.id.sProgressBar);
        userImage = findViewById(R.id.profileImage);
        userImage2 = findViewById(R.id.compare);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(signUp.this);
            }
        });


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
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String namePattern = "(?=^.{0,50}$)^[a-zA-Z-]+\\s[a-zA-Z-]+$";

        if(!name.equals("") || !email.equals("") || !newPass.equals("") || !conPass.equals("")){

            if(!(email.matches(emailPattern))){
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }else if(!(name.matches(namePattern))){
                Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
            }

            else{
                StringRequest validate = new StringRequest(Request.Method.POST, validate_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Can't create account! exixting user")){
                                    Toast.makeText(signUp.this, response, Toast.LENGTH_SHORT).show();
                                }else if(response.equals("dont Exist")){
                                    if(!(userImage.getDrawable() == userImage2.getDrawable())){
                                        sendData();
                                        ImageUploadToServerFunction();
                                    }else {
                                        Toast.makeText(signUp.this, "Choose Image", Toast.LENGTH_SHORT).show();
                                    }

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
            }



        }else {
            Toast.makeText(signUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData(){


        final String name, email, password;

        if(newPassword.getText().toString().equals(confirmedPwd.getText().toString())){
            password = confirmedPwd.getText().toString();
        }else{
            password = "wrong";
        }

        name = fullName.getText().toString();
        email = userMail.getText().toString();




            if (!password.equals("wrong")){
                if(!(password.length() < 8)){
                    StringRequest signUprequest = new StringRequest(Request.Method.POST, Sendurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Welcome \uD83E\uDD17")){
                                        userMail.setText("");
                                        newPassword.setText("");
                                        confirmedPwd.setText("");
                                        progressBar.setVisibility(View.VISIBLE);
                                        Toast.makeText(signUp.this, "Log In", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(signUp.this, LogIn.class);
                                        intent.putExtra("fullname", name);
                                        startActivity(intent);
                                        finish();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(signUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("fullname",name);
                            params.put("email",email);
                            params.put("password",password);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(signUp.this);
                    requestQueue.add(signUprequest);
                }else{
                    Toast.makeText(this, "Password must be min 8 characters", Toast.LENGTH_SHORT).show();
                }

}else {
                newPassword.setText("");
                confirmedPwd.setText("");
                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)){
                uri = imageuri;
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                startCrop(imageuri);
            }

        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
//                userImage.setImageURI(result.getUri());

                Uri uri = result.getUri();

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    userImage.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }


    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String response) {

                super.onPostExecute(response);
                // Setting image as transparent after done uploading.
                userImage.setImageResource(android.R.color.transparent);

            }

            @Override
            protected String doInBackground(Void... params) {
                String email = userMail.getText().toString().trim();
                String name = fullName.getText().toString().replace(" ", "");
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("ImageData", ConvertImage);
                HashMapParams.put("email", email);
                HashMapParams.put("fullname", name);

                return imageProcessClass.ImageHttpRequest(Surl, HashMapParams);
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
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