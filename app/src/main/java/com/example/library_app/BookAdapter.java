package com.example.library_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<BookInfo> bookInfoArrayList;
    private Context mcontext;

    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context mcontext) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookInfo bookInfo = bookInfoArrayList.get(position);
        holder.nameTV.setText(bookInfo.getTitle());

        StringBuilder authorSTRbfr = new StringBuilder();
        if (bookInfo.getAuthors() != null && !bookInfo.getAuthors().isEmpty()) {
            for (int j = 0; j < bookInfo.getAuthors().size(); j++) {

                if(j + 1 == bookInfo.getAuthors().size()){
                    authorSTRbfr.append(bookInfo.getAuthors().get(j));
                }
                else{
                    authorSTRbfr.append(bookInfo.getAuthors().get(j)).append(", ");
                }
            }
        }

        holder.authorTV.setText(authorSTRbfr);


        holder.publisherTV.setText(bookInfo.getPublisher());

        holder.pageCountTV.setText("No of Pages : " + bookInfo.getPageCount());
        holder.dateTV.setText(bookInfo.getPublishedDate());

        String img_url = bookInfo.getThumbnail();
        if (img_url != null && img_url.startsWith("http://")) {
            img_url = img_url.replaceFirst("http://", "https://");
        }

        if (img_url != null && !img_url.equals("")) {
            Picasso.get().load(img_url)
                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .into(holder.bookIV);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(mcontext, BookDetails.class);
            i.putExtra("title", bookInfo.getTitle());
            i.putExtra("subtitle", bookInfo.getSubtitle());

            StringBuilder authorSTRbfr_N = new StringBuilder();
            if (bookInfo.getAuthors() != null && !bookInfo.getAuthors().isEmpty()) {
                for (int j = 0; j < bookInfo.getAuthors().size(); j++) {

                    if(j + 1 == bookInfo.getAuthors().size()){
                        authorSTRbfr_N.append(bookInfo.getAuthors().get(j));
                    }
                    else{
                        authorSTRbfr_N.append(bookInfo.getAuthors().get(j)).append(", ");
                    }
                }
            }

            i.putExtra("authors", (CharSequence) authorSTRbfr_N);


            i.putExtra("publisher", bookInfo.getPublisher());
            i.putExtra("publishedDate", bookInfo.getPublishedDate());
            i.putExtra("description", bookInfo.getDescription());
            i.putExtra("pageCount", bookInfo.getPageCount());
            i.putExtra("thumbnail", bookInfo.getThumbnail());
            i.putExtra("previewLink", bookInfo.getPreviewLink());
            i.putExtra("infoLink", bookInfo.getInfoLink());
            i.putExtra("buyLink", bookInfo.getBuyLink());
            //after passing that data we are starting our new  intent.
            mcontext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, publisherTV, pageCountTV, dateTV, authorTV;
        ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVBookTitle);
            publisherTV = itemView.findViewById(R.id.idTVpublisher);
            pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
            authorTV = itemView.findViewById(R.id.idTVauthor);
        }
    }
}

