package ru.denfad.studturism.Sevice;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.Filter;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.Model.Review;
import ru.denfad.studturism.Model.User;

public interface JSONRequests {

    @GET("/hotels")
    public Call<List<Hostel>> getAllHostels();

    @GET("/hotel/{id}")
    public Call<Hostel> getHostel(@Path("id") int hostelId);

    @POST("/hotel_filter")
    public Call<List<Hostel>> filter(@Body Filter filter);

    @GET("/news")
    public Call<List<NewsPost>> getNews();

    @POST("/users")
    public Call<ResponseBody> addUser(@Body User user);

    @GET("/user/{username}")
    public Call<User> getUser(@Path("username") String username);

    @POST("/booking")
    public Call<ResponseBody> book(@Body Book book);

    @GET("/booking/{username}")
    public Call<List<Book>> getBooks(@Path("username") String username);

    @Multipart
    @POST("/uploader")
    public Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);


    @POST("/review")
    public Call<ResponseBody> addReview(@Body Review review);

    @GET("/review/{username}")
    public Call<List<Review>>  getUserReviews(@Path("username") String username);

    @GET("/review")
    public Call<List<Review>> getReviews();




}
