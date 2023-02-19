package ru.denfad.studturism.Sevice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.denfad.studturism.Model.Filter;
import ru.denfad.studturism.Model.Hostel;

public interface JSONRequests {

    @GET("/hotels")
    public Call<List<Hostel>> getAllHostels();

    @GET("/hotel/{id}")
    public Call<Hostel> getHostel(@Path("id") int hostelId);

    @POST("/hotel_filter")
    public Call<List<Hostel>> filter(@Body Filter filter);
}
