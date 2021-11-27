package com.company.newsfeed;

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

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    ArrayList<Articles> articlesArrayList;
    Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item, parent, false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.SubTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(e -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("title", articles.getTitle());
            intent.putExtra("content", articles.getContent());
            intent.putExtra("desc", articles.getDescription());
            intent.putExtra("image", articles.getUrlToImage());
            intent.putExtra("url", articles.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV, SubTitleTV;
        ImageView newsIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVNewsHeader);
            SubTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idTVNews);
        }

    }
}
