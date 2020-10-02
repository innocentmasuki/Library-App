package com.example.library.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class AddBooks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//      String add_info_url = "http://192.168.137.1/library/book_info_upload.php";
//    String add_cover_url = "http://192.168.137.1/library/add_book.php";
//    String validate_url = "http://192.168.137.1/library/validate_book.php";

      String add_info_url = "http://192.168.43.225/library/book_info_upload.php";
    String add_cover_url = "http://192.168.43.225/library/add_book.php";
    String validate_url = "http://192.168.43.225/library/validate_book.php";
    String categorySelected;
    Uri uri;
    Bitmap bitmap;
    Button addbookBtn;
    EditText Title, Author, Isbn, Description,remaining;
    ImageView bookCover, bookcover2;
    Spinner categorySpinner;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        Title = findViewById(R.id.bookTitle);
        remaining = findViewById(R.id.present);
        Author = findViewById(R.id.bookAuthor);
        Isbn = findViewById(R.id.isbn);
        Description = findViewById(R.id.description);
        bookCover = findViewById(R.id.bookCover);
        bookcover2 = findViewById(R.id.book_cover2);
        addbookBtn = findViewById(R.id.addbookBtn);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bookCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);



        addbookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validate();
            }

        });

        bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(AddBooks.this);
            }
        });

    }



    public  void validate(){
        final String title, author, isbn, description, Available;
        title = Title.getText().toString();
        author = Author.getText().toString();
        isbn = Isbn.getText().toString();
        description = Description.getText().toString();
        Available = remaining.getText().toString();
        final String logged =  getIntent().getStringExtra("Mail");


        if(!title.equals("") || !author.equals("") || !isbn.equals("") || !description.equals("")){
            StringRequest validate = new StringRequest(Request.Method.POST, validate_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("This book already Exists")){
                                Toast.makeText(AddBooks.this, response, Toast.LENGTH_SHORT).show();
                            }else if(response.equals("dont Exist")){
//                                Toast.makeText(AddBooks.this, "Choose Book Cover", Toast.LENGTH_SHORT).show();

                                if(!(bookCover.getDrawable() == bookcover2.getDrawable())){
                                    sendData(title, author, isbn, description, logged, categorySelected,Available);
                                        for(int i = 0; i < 5000;i++){

                                        }

                                }else {
                                    Toast.makeText(AddBooks.this, "Choose Book Cover", Toast.LENGTH_SHORT).show();
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
                    params.put("title",title);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(AddBooks.this);
            requestQueue.add(validate);


        }else {
            Toast.makeText(AddBooks.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData(final String title, final String author, final String isbn, final String description, final String logged , final String categorySelected, final String Available){
            StringRequest signUprequest = new StringRequest(Request.Method.POST, add_info_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Uploaded")){
                                Toast.makeText(AddBooks.this, "The book " + Title.getText() + " was added successfully", Toast.LENGTH_LONG).show();
                                Author.setText("");
                                Isbn.setText("");
                                Description.setText("");
                                Title.setText("");
                                remaining.setText("");

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddBooks.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title",title);
                    params.put("author",author);
                    params.put("isbn",isbn);
                    params.put("descriptions",description);
                    params.put("uploadedby",logged);
                    params.put("available",Available);
                    params.put("category",categorySelected);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(AddBooks.this);
            requestQueue.add(signUprequest);

        ImageUploadToServerFunction();


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

                    bookCover.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(11, 16)
                .setMultiTouchEnabled(false)
                .start(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categorySelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

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
                bookCover.setImageResource(R.drawable.book_cover);

            }

            @Override
            protected String doInBackground(Void... params) {
                String title = Title.getText().toString();
                AddBooks.ImageProcessClass imageProcessClass = new AddBooks.ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("ImageData", ConvertImage);
                HashMapParams.put("title", title);
                HashMapParams.put("coverName", title.replace(" ", ""));
                return imageProcessClass.ImageHttpRequest(add_cover_url, HashMapParams);
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(AddBooks.this, librarian_account.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        finish();
    }
}