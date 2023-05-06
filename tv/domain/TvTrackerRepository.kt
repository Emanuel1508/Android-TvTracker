package lynx.internship.tv.domain

import lynx.internship.tv.data.api.DetailMovie
import lynx.internship.tv.data.api.Movie

interface TvTrackerRepository {
    fun fetchTopRated(responseListener: ResponseListener<List<Movie>>)

    fun fetchPopular(responseListener: ResponseListener<List<Movie>>)

    fun fetchDetail(responseListener: ResponseListener<DetailMovie>, id : String)
}