package lynx.internship.tv.data

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import lynx.internship.tv.data.room.AppDatabase
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.data.room.FavoritesDao
import lynx.internship.tv.domain.FavoritesRepository
import lynx.internship.tv.domain.ResponseListener

class FavoritesRepositoryImpl(context: Context) : FavoritesRepository, FavoritesDao {

    var db: FavoritesDao? = AppDatabase.getInstance(context)?.favoritesDao()

    private val userID = FirebaseAuth.getInstance().uid
    val dbFirebase = Firebase.firestore

    override fun getAll(): List<Favorites> {
        return db?.getAll() ?: emptyList()
    }

    override fun searchById(favoritesId: Long): Boolean {
        return db?.searchById(favoritesId) ?: false
    }

    override fun insertAll(favorites: Favorites) {
        db?.insertAll(favorites)
    }

    override fun delete(favorites: Favorites) {
        db?.delete(favorites)
    }

    override fun fetchFavorites(responseListener: ResponseListener<List<Favorites>>) {
        var fav = getAll()
        if (fav != null) {
            responseListener.onSuccess(fav)
        } else {
            responseListener.onError(Exception("error"))
        }
    }

    override fun fetchFavoritesFireBase(responseListener: ResponseListener<List<Favorites>>) {
        val favoriteList = mutableListOf<Favorites>()

        if (userID == null) return

        dbFirebase.collection(userID)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val favoriteInstance = Favorites(
                        document.data.get("fid") as Long,
                        document.data.get("favTitle") as String,
                        document.data.get("dateAdded") as String,
                        document.data.get("imageUrl") as String,

                        )
                    Log.i("LOG", favoriteInstance.toString())
                    favoriteList.add(favoriteInstance)
                }
                responseListener.onSuccess(favoriteList)
            }

    }

    override fun insertFavorites(favorites: Favorites) {
        var inFav = insertAll(favorites)
    }

    override fun insertFireBase(favorites: Favorites) {
        if (favorites.favTitle == null) return
        dbFirebase.collection(userID.toString())
            .document(favorites.favTitle)
            .set(favorites)
            .addOnSuccessListener {
                Log.v("SUCCES", "data added successfully")
            }
    }

    override fun deleteFromFireBase(favorites: Favorites) {
        if (favorites.favTitle == null) return
        if (userID != null) {
            dbFirebase.collection(userID).document(favorites.favTitle).delete()
        }
    }

    override fun deleteFavorites(favorites: Favorites) {
        delete(favorites)
    }

    override fun searchFavorites(
        favoritesId: Long,
    ) : Boolean {
        return searchById(favoritesId)
    }
}