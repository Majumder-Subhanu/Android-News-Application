package com.company.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String title, desc, content, imageURL, Url;
    TextView Title, subDescription, Content;
    ImageView imageView;
    Button readMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        Url = getIntent().getStringExtra("url");

        Title = findViewById(R.id.idTVNews);
        subDescription = findViewById(R.id.idTVSubDescription);
        Content = findViewById(R.id.idTVContent);
        imageView = findViewById(R.id.idIVNews);
        readMore = findViewById(R.id.idBtnReadNews);

        Title.setText(title);
        subDescription.setText(desc);
        Content.setText(content);
        Picasso.get().load(imageURL).into(imageView);
        readMore.setOnClickListener(e -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Url));
            startActivity(intent);
        });
    }
}