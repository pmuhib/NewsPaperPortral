package com.rozkhabardar.newspaperportral.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class RetrofitBuilder {
    private final static String BASE_URL = "https://api.rss2json.com/v1/";
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static ApiList retrofitapisjson()
    {
        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1,TimeUnit.MINUTES).build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
        return retrofit.create(ApiList.class);
    }
  /*  public static ApiList retrofitapisxml()
    {
        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1,TimeUnit.MINUTES).build();

         Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(SimpleXmlConverterFactory.create()).client(client).build();
        return retrofit.create(ApiList.class);
    }*/
}
