package lynx.internship.tv.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey val fid: Long,
    @ColumnInfo (name = "fav_title") val favTitle: String?,
    @ColumnInfo (name = "date_added") val dateAdded: String?,
    @ColumnInfo (name = "image_url") val imageUrl: String?,
)
