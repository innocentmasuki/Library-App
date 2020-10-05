package com.example.library.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.Books;
import com.example.library.Notification;
import com.example.library.R;
import com.example.library.activities.AppHome;
import com.example.library.activities.AppNotification;
import com.example.library.activities.EditAccount;
import com.example.library.activities.signUp;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.notificationViewHolder> {

    private Context mycontext;
    private ArrayList<Notification> myarrayList;
    public String bookisbn, userRole, userEmail;
    String update_notification_url = "http://192.168.43.225/library/updateRequest.php";
    String delete_notification_url = "http://192.168.43.225/library/deleteRequest.php";


    public NotificationAdapter(Context context, ArrayList<Notification> arrayList, String isbn, String role, String userMail){
        myarrayList = arrayList;
        mycontext = context;
        bookisbn = isbn;
        userRole = role;
        userEmail = userMail;
    }

    @NonNull
    @Override
    public NotificationAdapter.notificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.notification_row, parent, false);
        return new notificationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final notificationViewHolder holder, int position) {
        Notification currentNotification = myarrayList.get(position);

        String title = currentNotification.getnBookTitle();
        String author = currentNotification.getnBookAuthor();
        String cover = currentNotification.getnBookCover();
        final String requestedBy = currentNotification.getNrequestedBy();
        final String noteId = currentNotification.getnId();
        String status = currentNotification.getnStatus();
        final String isbn = currentNotification.getnIsbn();
        String approvedBy = currentNotification.getApprovedBy();

        holder.noteStatus.setText(status);
        holder.noteIsbn.setText(isbn);
        holder.notificationId.setText(noteId);
        holder.bookTitle.setText(title);
        holder.bookAuthor.setText(author);

        Picasso.get().load(cover).fit().centerInside().into(holder.bookCover);





        if(status.equals("Requested")){
            holder.approve.setText("Approve");
            holder.requestedBy.setText("Requested By: " + requestedBy);
        }else if(status.equals("Approved") && userRole.equals("user")){
            holder.approve.setText("Return");
            holder.requestedBy.setText("Approved by: " + approvedBy);
        }else if (status.equals("Approved") && userRole.equals("Admin")){
            holder.approve.setText("Returned");
            holder.requestedBy.setText("Approved by: " + approvedBy);
        }



        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRole.equals("Admin")){
                    if(holder.approve.getText().toString().equals("Received")){
                        Toast.makeText(mycontext, "This book was returned", Toast.LENGTH_SHORT).show();
                    }else if(!(holder.approve.getText().toString().equals("Received"))){
                        openDialogBox(holder.approve, noteId, requestedBy);
                    }
                }else if(userRole.equals("user")){
                    Toast.makeText(mycontext, "Please Return On Time", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void openDialogBox(final Button holder, final String noteId, final String reqBy) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mycontext);
        // Set the message show for the Alert time
        builder.setMessage("Are you sure you want to make this change?");
        // Set Alert Title
        builder.setTitle("⚠ " + holder.getText().toString() + " ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (holder.getText().toString().equals("Approve")){
                    updateRequest(noteId,bookisbn, update_notification_url);
                    holder.setText("Returned");
                }else if(holder.getText().toString().equals("Returned")){
                    holder.setText("Received");
                    deleteRequest(bookisbn, delete_notification_url, reqBy);
                }

            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateRequest(final String id, final String isbn, final String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mycontext, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mycontext, error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("isbn", isbn);
                params.put("email", userEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
        requestQueue.add(request);
    }

    private void deleteRequest(final String isbn, final String url, final String requby) {


        StringRequest delete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mycontext, "Returned", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mycontext, error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("requestedby", requby);
                params.put("isbn", isbn);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
        requestQueue.add(delete);
    }


    @Override
    public int getItemCount() {
        return myarrayList.size();
    }

    public static class notificationViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCover;
        public Button approve;

        public TextView bookTitle, bookAuthor, requestedBy, notificationId, noteStatus, noteIsbn;
        public notificationViewHolder(@NonNull View itemView) {
            super(itemView);
            bookAuthor = itemView.findViewById(R.id.rbookAuthor);
            bookCover = itemView.findViewById(R.id.rbookCover);
            bookTitle = itemView.findViewById(R.id.rbookTitle);
            requestedBy = itemView.findViewById(R.id.requestedBy);
            approve = itemView.findViewById(R.id.approveBtn);
            notificationId = itemView.findViewById(R.id.notiId);
            noteStatus = itemView.findViewById(R.id.notistatus);
            noteIsbn = itemView.findViewById(R.id.nisbn);



        }
    }

}
