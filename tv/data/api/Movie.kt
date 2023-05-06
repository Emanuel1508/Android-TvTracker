package lynx.internship.tv.data.api

import com.google.gson.annotations.SerializedName
import lynx.internship.tv.data.Image

data class Movie(
    @SerializedName("id")
    public var id: String?,

    @SerializedName("name")
    public var title: String?,

    @SerializedName("backdrop_path")
    public var backdropPath: String?
) {

    var imageUrl: String? = null
        get() = if (backdropPath != null) "${Image.imageURLPrefix}$backdropPath" else null
}