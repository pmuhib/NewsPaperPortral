package com.rozkhabardar.newspaperportral.webservices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public interface ApiList {
    @GET
    Call<ApiResponse> getfeed(@Url String url,@Query("api_key") String apiKey);

}
