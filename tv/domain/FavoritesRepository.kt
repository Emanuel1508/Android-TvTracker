package lynx.internship.tv.domain


import lynx.internship.tv.data.room.Favorites


interface FavoritesRepository {
    fun fetchFavorites(responseListener: ResponseListener <List<Favorites>>)

    fun fetchFavoritesFireBase(responseListener: ResponseListener<List<Favorites>>)

    fun insertFavorites(favorites: Favorites)

    fun insertFireBase(favorites: Favorites)

    fun deleteFromFireBase(favorites: Favorites)

    fun deleteFavorites(favorites: Favorites)

    fun searchFavorites(favoritesId: Long) : Boolean
}