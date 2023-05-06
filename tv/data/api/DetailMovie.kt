package lynx.internship.tv.data.api

import com.google.gson.annotations.SerializedName
import lynx.internship.tv.data.Image

data class DetailMovie(
    @SerializedName("backdrop_path")
    val backdrop_path: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val poster_path: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("vote_average")
    val vote_average: Double
) {

    var backdropUrl: String? = null
        get() = if (backdrop_path != null) "${Image.imageURLPrefix}$backdrop_path" else null

    var posterUrl: String? = null
        get() = if (poster_path != null) "${Image.imageURLPrefix}$poster_path" else null
}