package lynx.internship.tv.data.api

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("results")
    public var results: List<Movie>
)
