package com.example.testlynx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

public class FeedCardsAdapter extends RecyclerView.Adapter<FeedCardsAdapter.ViewHolder> {
    private Context context;
    private String[] usernames = {"Hayk", "Artur", "Vochil"};

    public FeedCardsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).placeholder(R.drawable.ic_launcher_background).centerCrop();

        Glide.with(context).load(R.drawable.back).apply(options).into(holder.iv);

//        Picasso.with(context).load(R.mipmap.ic_launcher).fit().centerCrop().into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return usernames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv_username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}