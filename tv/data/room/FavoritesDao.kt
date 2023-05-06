package lynx.internship.tv.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorites>

    @Query("SELECT * FROM favorites WHERE fid= :favoritesId")
    fun searchById(favoritesId : Long) : Boolean

    @Insert
    fun insertAll(favorites: Favorites)

    @Delete
    fun delete(favorites: Favorites)

}