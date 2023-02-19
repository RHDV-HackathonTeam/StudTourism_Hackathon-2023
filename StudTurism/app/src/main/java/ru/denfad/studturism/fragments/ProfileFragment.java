package ru.denfad.studturism.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.sagrishin.collageview.CollageItemData;
import com.sagrishin.collageview.CollageItemDrawableData;
import com.sagrishin.collageview.CollageView;
import com.sagrishin.collageview.ItemPreviewLoader;

import java.util.ArrayList;
import java.util.List;

import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.activities.AddMarkActivity;
import ru.denfad.studturism.activities.ReviewEditActivity;


public class ProfileFragment extends Fragment {

    private List<NewsPost> posts;
    private List<Book> books = new ArrayList<>();
    private MainService service;

    public ProfileFragment() {
        // Required empty public constructor
        service = MainService.getInstance();
        posts = service.getUserNewsPosts(3);
        books = service.getBooks();
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView image = rootView.findViewById(R.id.user_image);
        image.setClipToOutline(true);

        RecyclerView profileList = rootView.findViewById(R.id.profile_list);
        NewsAdapter adapter = new NewsAdapter();
        profileList.setAdapter(adapter);
        profileList.setLayoutManager(new LinearLayoutManager(getContext()));

        RadioGroup group = rootView.findViewById(R.id.radio_button_posts);
        group.check(R.id.my_review);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });

        RecyclerView bookList = rootView.findViewById(R.id.book_list);
        BookAdapter bookAdapter = new BookAdapter();
        bookList.setAdapter(bookAdapter);
        bookList.setLayoutManager(new LinearLayoutManager(getContext()));

        TextRoundCornerProgressBar progress = rootView.findViewById(R.id.user_progress);
        progress.setProgress(40);
        progress.setProgressText(String.valueOf(40));

        Button add = rootView.findViewById(R.id.make_review);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReviewEditActivity.class));
            }
        });
        return rootView;
    }


    class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
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
                    imageView.setImageDrawable(((CollageItemDrawableData) collageItemData).getDrawable());
                }
            });
            List<CollageItemDrawableData> data = new ArrayList<>();

            for (Integer i : p.imageIds) {
                data.add(new CollageItemDrawableData(getResources().getDrawable(i)));
            }
            view.setItemDatas(data);
            view.showCollage();


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
            holder.authorImage.setClipToOutline(true);
            holder.authorImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_ic));

            holder.authorName.setText("Дмитрий Гидаспов");

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

    class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.price_item, parent, false);

            // Return a new holder instance
            BookViewHolder viewHolder = new BookViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book b = books.get(position);
            holder.name.setText(b.hostel);
            holder.price.setText(b.visitDate + "\n - \n" + b.exitDate);
            holder.description.setText(b.town + "\n");
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView description;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.price_card_name);
            price = itemView.findViewById(R.id.price_card_price);
            description = itemView.findViewById(R.id.price_card_description);

        }
    }
}