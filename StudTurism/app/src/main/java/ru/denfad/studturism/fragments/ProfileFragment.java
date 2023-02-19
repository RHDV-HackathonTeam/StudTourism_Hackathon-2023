package ru.denfad.studturism.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.sagrishin.collageview.CollageItemData;
import com.sagrishin.collageview.CollageItemDrawableData;
import com.sagrishin.collageview.CollageItemUrlData;
import com.sagrishin.collageview.CollageView;
import com.sagrishin.collageview.ItemPreviewLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.Model.Review;
import ru.denfad.studturism.Model.User;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;
import ru.denfad.studturism.activities.ReviewEditActivity;


public class ProfileFragment extends Fragment {

    private List<NewsPost> posts;
    private List<Book> books = new ArrayList<>();
    private MainService service;
    private SharedPreferences mSharedPreferences;
    private List<Review> reviews;


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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        RecyclerView profileList = rootView.findViewById(R.id.profile_list);

        NetworkService.getInstance().getJSONApi()
                .getUserReviews(mSharedPreferences.getString("username", "1"))
                .enqueue(new Callback<List<Review>>() {
                    @Override
                    public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                        reviews = response.body();
                        Log.e("Profile", new Gson().toJson(response.body()));
                        NewsAdapter adapter = new NewsAdapter();
                        profileList.setAdapter(adapter);
                        profileList.setLayoutManager(new LinearLayoutManager(getContext()));

                    }

                    @Override
                    public void onFailure(Call<List<Review>> call, Throwable t) {

                    }
                });


        RadioGroup group = rootView.findViewById(R.id.radio_button_posts);
        group.check(R.id.my_review);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });



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


        TextView name = rootView.findViewById(R.id.user_name);

        NetworkService.getInstance()
                .getJSONApi()
                .getUser(mSharedPreferences.getString("username", "1"))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();
                        name.setText(u.name + " "+u.surname);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

        RecyclerView bookList = rootView.findViewById(R.id.book_list);


        NetworkService.getInstance().getJSONApi()
                .getBooks(mSharedPreferences.getString("username", "1"))
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        books = response.body();
                        Log.e("Server", response.message());
                        Log.e("Server", response.toString());
                        Log.e("Server", new Gson().toJson(response.body()));
                        BookAdapter bookAdapter = new BookAdapter();
                        bookList.setAdapter(bookAdapter);
                        bookList.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {

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

            holder.likeLayout.setVisibility(View.GONE);
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
        TextView text;
        LinearLayout source;
        LikeButton like;
        LinearLayout likeLayout;
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
            NetworkService.getInstance()
                    .getJSONApi()
                    .getHostel(Integer.parseInt(b.hostelId))
                    .enqueue(new Callback<Hostel>() {
                        @Override
                        public void onResponse(Call<Hostel> call, Response<Hostel> response) {
                            Hostel h = response.body();
                            holder.name.setText(h.hostel);
                        }

                        @Override
                        public void onFailure(Call<Hostel> call, Throwable t) {

                        }
                    });



            holder.price.setText(b.visitDate + "\n - \n" + b.exitDate);
            holder.description.setText("Колличество гостей: "+b.guestCount + "\n");
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