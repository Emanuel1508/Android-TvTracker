package lynx.internship.tv.data

import lynx.internship.tv.data.api.DetailMovie
import lynx.internship.tv.data.api.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieApi {

    @GET("tv/top_rated?api_key=0a416fc6c49f4a04db6e3bd398ef8579")
    fun fetchTopRatedMovies() : Call<MovieResponse>

    @GET("tv/popular?api_key=0a416fc6c49f4a04db6e3bd398ef8579")
    fun fetchPopularMovies() : Call<MovieResponse>

    @GET("tv/{movie_id}?api_key=0a416fc6c49f4a04db6e3bd398ef8579")
    fun fetchDetailForMovies(@Path("movie_id") id : String) : Call<DetailMovie>
}