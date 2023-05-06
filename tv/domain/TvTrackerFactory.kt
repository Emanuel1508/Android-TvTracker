package lynx.internship.tv.domain

import lynx.internship.tv.data.auth.AuthRepositoryImpl
import android.content.Context
import lynx.internship.tv.data.FavoritesRepositoryImpl
import lynx.internship.tv.data.TvTrackerRepositoryImpl
import lynx.internship.tv.domain.auth.AuthRepository

object TvTrackerFactory {

    fun createTvTrackerRepository(): TvTrackerRepository {
        return TvTrackerRepositoryImpl()
    }

    fun createTvTrackerAuthRepository() : AuthRepository {
        return AuthRepositoryImpl()
    }

    fun createFavoritesRepository(context: Context?): FavoritesRepository? {
        if (context == null) {
            return null
        }
        return FavoritesRepositoryImpl(context)
    }
}