package lynx.internship.tv.data


import lynx.internship.tv.data.api.DetailMovie
import lynx.internship.tv.data.api.Movie
import lynx.internship.tv.data.api.RetrofitInstance
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerRepository
import lynx.internship.tv.data.api.MovieResponse

import retrofit2.Call
import retrofit2.Response

import java.lang.Exception

class TvTrackerRepositoryImpl : TvTrackerRepository, TheMovieApi {
    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
    override fun fetchTopRated(responseListener: ResponseListener<List<Movie>>) {

        fetchTopRatedMovies()
            .enqueue(object : retrofit2.Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    var movieList = response?.body()?.results
                    if (movieList == null) {
                        return
                    }
                    responseListener.onSuccess(movieList)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    responseListener.onError(Exception(t?.message))
                }
            })
    }

    override fun fetchPopular(responseListener: ResponseListener<List<Movie>>) {
        fetchPopularMovies()
            .enqueue(object : retrofit2.Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    var movieList = response?.body()?.results
                    if(movieList == null)  {
                        return
                    }
                    responseListener.onSuccess(movieList)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    responseListener.onError(Exception(t?.message))
                }
            })
    }

    override fun fetchDetail(responseListener: ResponseListener<DetailMovie>, id : String) {
        fetchDetailForMovies(id)
            .enqueue(object : retrofit2.Callback<DetailMovie> {
                override fun onResponse(
                    call: Call<DetailMovie>,
                    response: Response<DetailMovie>
                ) {
                    var detail = response?.body()
                    if(detail == null) return
                    detail.backdropUrl = imageBaseUrl + detail.backdrop_path
                    detail.posterUrl = imageBaseUrl + detail.poster_path
                    responseListener.onSuccess(detail)
                }

                override fun onFailure(call: Call<DetailMovie>, t: Throwable) {
                    responseListener.onError(Exception(t?.message))
                }
            })
    }

    override fun fetchTopRatedMovies(): Call<MovieResponse> {

        return RetrofitInstance.retrofit.create(TheMovieApi::class.java).fetchTopRatedMovies()

    }

    override fun fetchPopularMovies(): Call<MovieResponse> {
        return RetrofitInstance.retrofit.create(TheMovieApi::class.java).fetchPopularMovies()
    }

    override fun fetchDetailForMovies(id: String): Call<DetailMovie> {
        return RetrofitInstance.retrofit.create(TheMovieApi::class.java).fetchDetailForMovies(id)
    }
}