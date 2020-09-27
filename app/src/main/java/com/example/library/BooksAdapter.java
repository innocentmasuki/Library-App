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

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private Context mcontext;
    private ArrayList<Books> marrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public BooksAdapter(Context context, ArrayList<Books> arrayList){
        marrayList = arrayList;
        mcontext = context;

    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.book_card, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        Books currentBook = marrayList.get(position);

        String title = currentBook.getTitle();
        String author = currentBook.getAuthor();
        String cover = currentBook.getCover();
        String isbn = currentBook.getIsbn();
        String category = currentBook.getCategory();
        String uploadedby = currentBook.getUploadedBy();
        String available = currentBook.getAvailable();
        String description = currentBook.getDescript();

        holder.bookTitle.setText(title);
        holder.bookAuthor.setText(author);
        holder.bookDescriptions.setText(description);
        holder.isbn.setText(isbn);
        holder.bookCategory.setText(category);
        holder.uploader.setText(uploadedby);
        holder.available.setText(available);
        Picasso.get().load(cover).fit().centerInside().into(holder.bookCover);
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCover;
        public TextView bookTitle, bookAuthor, bookDescriptions, available, isbn, uploader, bookCategory;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            bookAuthor = itemView.findViewById(R.id.author);
            bookCover = itemView.findViewById(R.id.bCover);
            bookTitle = itemView.findViewById(R.id.title);
            bookDescriptions = itemView.findViewById(R.id.descripts);
            available = itemView.findViewById(R.id.available);
            isbn = itemView.findViewById(R.id.isbn);
            uploader = itemView.findViewById(R.id.uploadby);
            bookCategory = itemView.findViewById(R.id.category);

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
