package com.devtides.dogsapp.model.retrofit;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiUrl
{
    @POST("admin/authenticate")
    @Headers({"Content-Type: application/json"})
    Single<JsonObject> apiLogin(@Body HashMap hashMap);
}
