package ru.denfad.studturism.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.sagrishin.collageview.CollageItemData;
import com.sagrishin.collageview.CollageItemDrawableData;
import com.sagrishin.collageview.CollageItemUrlData;
import com.sagrishin.collageview.CollageView;
import com.sagrishin.collageview.ItemPreviewLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.Model.Review;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;
import ru.denfad.studturism.ui.ExpandableTextView;


public class NewsFragment extends Fragment {

    List<NewsPost> posts;
    List<Review> reviews;
    private SharedPreferences mSharedPreferences;

    public NewsFragment() {
        // Required empty public constructor
        posts = MainService.getInstance().getNewsPosts();
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
       // RecyclerView list = rootView.findViewById(R.id.news_list);
        RecyclerView newsList = rootView.findViewById(R.id.news_list);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        NetworkService.getInstance()
                .getJSONApi()
                .getNews()
                .enqueue(new Callback<List<NewsPost>>() {
                    @Override
                    public void onResponse(Call<List<NewsPost>> call, Response<List<NewsPost>> response) {

                        posts = response.body();
                        if(posts != null) {
                            NewsAdapter adapter = new NewsAdapter();
                            newsList.setAdapter(adapter);
                            newsList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                        else Log.e("News", "None news");
                    }

                    @Override
                    public void onFailure(Call<List<NewsPost>> call, Throwable t) {

                    }
                });

        TabLayout tab = rootView.findViewById(R.id.news_tab);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("Tab", "selected");
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("Tab", "selected");
                        NetworkService.getInstance()
                                .getJSONApi()
                                .getNews()
                                .enqueue(new Callback<List<NewsPost>>() {
                                    @Override
                                    public void onResponse(Call<List<NewsPost>> call, Response<List<NewsPost>> response) {

                                        posts = response.body();
                                        if(posts != null) {
                                            NewsAdapter adapter = new NewsAdapter();
                                            newsList.setAdapter(adapter);
                                            newsList.setLayoutManager(new LinearLayoutManager(getContext()));
                                        }
                                        else Log.e("News", "None news");
                                    }

                                    @Override
                                    public void onFailure(Call<List<NewsPost>> call, Throwable t) {

                                    }
                                });
                        break;
                    case 1:
                        NetworkService.getInstance().getJSONApi()
                                .getReviews()
                                .enqueue(new Callback<List<Review>>() {
                                    @Override
                                    public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                                        reviews = response.body();
                                        Log.e("Profile", new Gson().toJson(response.body()));
                                        ReviewAdapter adapter = new ReviewAdapter();
                                       newsList.setAdapter(adapter);
                                       newsList.setLayoutManager(new LinearLayoutManager(getContext()));

                                    }

                                    @Override
                                    public void onFailure(Call<List<Review>> call, Throwable t) {

                                    }
                                });
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    class NewsAdapter extends  RecyclerView.Adapter<NewsViewHolder> {

        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.news_item, parent, false);

            // Return a new holder instance
            NewsViewHolder viewHolder = new NewsViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            NewsPost p = posts.get(position);

            holder.text.setText(p.title + "\n\n"+p.text);
            CollageView view = holder.collage;
            view.setItemPreviewLoader(new ItemPreviewLoader() {
                @Override
                public void loadItemPreviewInto(@NonNull ImageView imageView, @NonNull CollageItemData collageItemData) {
                    Picasso.get().load(((CollageItemUrlData)collageItemData).getImageUrl()).resize(500*2, 350*2).onlyScaleDown().into(imageView);
                }
            });
            List<CollageItemUrlData> data = new ArrayList<>();

                data.add(new CollageItemUrlData(p.pictureUrl));


            view.setItemDatas(data);
            view.showCollage();

            holder.source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p.href));
                    startActivity(intent);
                }
            });


            if(p.likesCount == -1){
                holder.likeLayout.setVisibility(View.GONE);
            }
            else {
                holder.likesCount.setText(String.valueOf(p.likesCount));
                holder.like.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        holder.likesCount.setText(String.valueOf(p.likesCount + 1));
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        holder.likesCount.setText(String.valueOf(p.likesCount));
                    }
                });
            }


            holder.authorImage.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            holder.authorImage.setClipToOutline(true);
            holder.authorName.setText("@студтуризм");

        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

    class ReviewAdapter extends RecyclerView.Adapter<NewsViewHolder> {
        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.news_item, parent, false);

            // Return a new holder instance
            NewsViewHolder viewHolder = new NewsViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            Review p = reviews.get(position);

            holder.text.setText(p.text);

            CollageView view = holder.collage;


//            for (Integer i : p.imageIds) {
//                data.add(new CollageItemDrawableData(getResources().getDrawable(i)));
//            }

            view.setItemPreviewLoader(new ItemPreviewLoader() {
                @Override
                public void loadItemPreviewInto(@NonNull ImageView imageView, @NonNull CollageItemData collageItemData) {
                    Picasso.get().load(((CollageItemUrlData)collageItemData).getImageUrl()).resize(500*2, 350*2).onlyScaleDown().into(imageView);
                }
            });
            List<CollageItemUrlData> data = new ArrayList<>();
            data.add(new CollageItemUrlData("https://66ec-2a00-1fa0-4a46-2469-7a30-9ca6-609b-b91.eu.ngrok.io/uploads/"+p.imgRefs.get(0)));
            view.setItemDatas(data);
            view.showCollage();

            holder.likesCount.setText("0");
            holder.like.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    holder.likesCount.setText("1");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    holder.likesCount.setText("0");
                }
            });
            holder.authorImage.setClipToOutline(true);
            holder.authorImage.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));

            holder.authorName.setText("@"+p.username);

        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        CollageView collage;
        ExpandableTextView text;
        LinearLayout source;
        LinearLayout likeLayout;
        LikeButton like;
        TextView likesCount;
        TextView authorName;
        ImageView authorImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            collage = itemView.findViewById(R.id.card_news_collage);
            text = itemView.findViewById(R.id.news_card_text);
            source = itemView.findViewById(R.id.source_layout);
            like = itemView.findViewById(R.id.like_button);
            likesCount = itemView.findViewById(R.id.like_count);
            authorImage = itemView.findViewById(R.id.user_image);
            authorName = itemView.findViewById(R.id.user_name_post);
            likeLayout = itemView.findViewById(R.id.like_layout);
        }
    }

}