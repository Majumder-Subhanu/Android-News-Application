package com.company.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// be7cfcef10b54f59b2bee6d0ceed375b
public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
    RecyclerView newsRV, categoryRV;
    ProgressBar loadingPB;
    ArrayList<Articles> articlesArrayList;
    ArrayList<CategoryRVModel> categoryRVModels;
    CategoryRVAdapter categoryRVAdapter;
    NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryRV = findViewById(R.id.idRVCategories);
        newsRV = findViewById(R.id.idRVNews);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModels = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModels, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setLayoutManager(new LinearLayoutManager(this));
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        categoryRVModels.add(new CategoryRVModel("All", "https://cdn-icons-png.flaticon.com/128/5110/5110472.png"));
        categoryRVModels.add(new CategoryRVModel("Technology", "https://cdn-icons.flaticon.com/png/128/3135/premium/3135889.png?token=exp=1638007863~hmac=21406445bf98517de24f086c41394f13"));
        categoryRVModels.add(new CategoryRVModel("Science", "https://cdn-icons.flaticon.com/png/128/3920/premium/3920108.png?token=exp=1638007901~hmac=cab25995f272175aace599abb452bb14"));
        categoryRVModels.add(new CategoryRVModel("Sports", "https://cdn-icons.flaticon.com/png/128/3920/premium/3920108.png?token=exp=1638007901~hmac=cab25995f272175aace599abb452bb14"));
        categoryRVModels.add(new CategoryRVModel("General", "https://cdn-icons.flaticon.com/png/128/2622/premium/2622955.png?token=exp=1638008056~hmac=c79ba089cc3b1f2f3616793a683b4b22"));
        categoryRVModels.add(new CategoryRVModel("Business", "https://cdn-icons.flaticon.com/png/128/998/premium/998341.png?token=exp=1638008078~hmac=38c5759e702ca8e5e638dbc215f87646"));
        categoryRVModels.add(new CategoryRVModel("Entertainment", "https://cdn-icons.flaticon.com/png/128/3083/premium/3083401.png?token=exp=1638008112~hmac=48ca60b02c3f79d7bc77b0a4550406a4"));
        categoryRVModels.add(new CategoryRVModel("Health", "https://cdn-icons-png.flaticon.com/128/2966/2966486.png"));
        categoryRVModels.add(new CategoryRVModel("Education", "https://cdn-icons.flaticon.com/png/128/2936/premium/2936719.png?token=exp=1638008164~hmac=8b0ad6767e60e073e46585ffa10ae8b6"));
        categoryRVModels.add(new CategoryRVModel("Bitcoin", "https://cdn-icons.flaticon.com/png/128/838/premium/838352.png?token=exp=1638008197~hmac=c4460ad3d592a32c844af1ea1b659bc7"));
        categoryRVModels.add(new CategoryRVModel("Military", "https://cdn-icons-png.flaticon.com/128/3111/3111484.png"));
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "http://newsapi.org/v2/top-headlines?c..." + category + "&apiKey=be7cfcef10b54f59b2bee6d0ceed375b";
        String url = "http://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=be7cfcef10b54f59b2bee6d0ceed375b";
        String BASE_URL = "https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<NewsModel> call;
        if (category.equals("All")) call = retrofitApi.getAllNews(url);
        else call = retrofitApi.getNewsByCategory(categoryURL);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                //TODO find the problem
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                    newsRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModels.get(position).getCategory();
        getNews(category);
    }
}