package com.example.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.notificationViewHolder> {

    private Context mcontext;
    private ArrayList<Notification> marrayList;
    private NotificationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(NotificationAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public NotificationAdapter(Context context, ArrayList<Notification> arrayList){
        marrayList = arrayList;
        mcontext = context;

    }

    @NonNull
    @Override
    public NotificationAdapter.notificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.notification_row, parent, false);
        return new notificationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull notificationViewHolder holder, int position) {
        Notification currentNotification = marrayList.get(position);

        String title = currentNotification.getnBookTitle();
        String author = currentNotification.getnBookAuthor();
        String cover = currentNotification.getnBookCover();
        String requestedBy = currentNotification.getNrequestedBy();


        holder.bookTitle.setText(title);
        holder.bookAuthor.setText(author);
        holder.requestedBy.setText(requestedBy);
        Picasso.get().load(cover).fit().centerInside().into(holder.bookCover);
    }


    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public class notificationViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCover;
        public TextView bookTitle, bookAuthor, requestedBy;
        public notificationViewHolder(@NonNull View itemView) {
            super(itemView);
            bookAuthor = itemView.findViewById(R.id.author);
            bookCover = itemView.findViewById(R.id.bCover);
            bookTitle = itemView.findViewById(R.id.title);
            requestedBy = itemView.findViewById(R.id.descripts);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){

                            mListener.onItemClick(position);

                        }
                    }
                }
            });
        }
    }
}
