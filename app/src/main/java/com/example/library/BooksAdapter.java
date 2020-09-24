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
        holder.bookTitle.setText(title);
        holder.bookAuthor.setText(author);
        Picasso.get().load(cover).fit().centerInside().into(holder.bookCover);
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCover;
        public TextView bookTitle;
        public TextView bookAuthor;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            bookAuthor = itemView.findViewById(R.id.author);
            bookCover = itemView.findViewById(R.id.bCover);
            bookTitle = itemView.findViewById(R.id.title);
        }
    }
}
