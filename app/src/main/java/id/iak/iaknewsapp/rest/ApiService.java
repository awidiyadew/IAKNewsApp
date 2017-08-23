package id.iak.iaknewsapp.rest;


import id.iak.iaknewsapp.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("articles")
    Call<ApiResponse> getArticle(
        @Query("source") String source,
        @Query("apiKey") String apiKey
    );

}
