package ru.denfad.studturism.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.sagrishin.collageview.CollageItemData;
import com.sagrishin.collageview.CollageItemDrawableData;
import com.sagrishin.collageview.CollageView;
import com.sagrishin.collageview.ItemPreviewLoader;

import java.util.ArrayList;
import java.util.List;

import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.databinding.ActivityMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    List<NewsPost> posts;

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
        NewsAdapter adapter = new NewsAdapter();
        newsList.setAdapter(adapter);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));


        TabLayout tab = rootView.findViewById(R.id.news_tab);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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

            holder.text.setText(p.text);

            CollageView view = holder.collage;
            view.setItemPreviewLoader(new ItemPreviewLoader() {
                @Override
                public void loadItemPreviewInto(@NonNull ImageView imageView, @NonNull CollageItemData collageItemData) {
                    imageView.setImageDrawable(((CollageItemDrawableData)collageItemData).getDrawable());
                }
            });
            List<CollageItemDrawableData> data = new ArrayList<>();

            for(Integer i: p.imageIds) {
                data.add(new CollageItemDrawableData(getResources().getDrawable(i)));
            }
            view.setItemDatas(data);
            view.showCollage();


            holder.source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/studturism"));
                    startActivity(intent);
                }
            });

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


            holder.authorImage.setImageDrawable(getResources().getDrawable(R.drawable.vk_logo));
            holder.authorImage.setClipToOutline(true);
            holder.authorName.setText("СтудТуризм");

        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        CollageView collage;
        TextView text;
        LinearLayout source;
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
        }
    }

}