package ru.denfad.studturism.Sevice;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * A specific Retrofit library class
 *  */
public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://66ec-2a00-1fa0-4a46-2469-7a30-9ca6-609b-b91.eu.ngrok.io";
    private Retrofit mRetrofit;

    private NetworkService() {


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .build();
    }

    // Realising Singleton pattern
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    // Returns interaction with server Interface
    public JSONRequests getJSONApi() {
        return mRetrofit.create(JSONRequests.class);
    }
}
