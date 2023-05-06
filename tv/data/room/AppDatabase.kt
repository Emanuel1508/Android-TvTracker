package lynx.internship.tv.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (db == null) {
                synchronized(AppDatabase::class) {
                    db = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "favorites.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return db
        }
    }
}