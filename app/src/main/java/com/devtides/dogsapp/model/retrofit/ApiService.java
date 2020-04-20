package com.devtides.dogsapp.model.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService
{
    private static final String baseUrl = "";
    private ApiUrl apiUrl;

    public ApiService()
    {
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        this.apiUrl = (ApiUrl) new Builder().baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .client(new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
                                .build()
                                .create(ApiUrl.class);
    }

    public Single<JsonObject> apiLogin(String userName, String password)
    {
        try
        {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username",userName);
            hashMap.put("password",password);
            return this.apiUrl.apiLogin(hashMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
