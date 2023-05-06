package lynx.internship.tv.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.databinding.FavoriteItemBinding
import lynx.internship.tv.domain.TvTrackerFactory

class FavoritesAdapter(val favorites: List<Favorites>, val favoritesClickListener: FavoritesClickListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val favoritesMutable: MutableList<Favorites> = favorites.toMutableList()

    interface FavoritesClickListener {
        fun onFavoritesClick(favorite:Favorites)
        fun onRemoveFavorite(favorite: Favorites)
    }

    class FavoritesViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorites, favoritesClickListener: FavoritesClickListener) {
            binding.tvMovieTitle.text = favorite.favTitle
            binding.tvDate.text = favorite.dateAdded
            binding.favoritesItem.setOnClickListener{
                favoritesClickListener.onFavoritesClick(favorite)
            }
            binding.ibRemoveFavorite.setOnClickListener {
                favoritesClickListener.onRemoveFavorite(favorite)
            }
            Glide.with(itemView.context)
                .load(favorite.imageUrl)
                .into(binding.ivMovieImage)
        }
    }

    fun removeFavorite(favorite: Favorites) {
        favoritesMutable.remove(favorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            FavoriteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return favoritesMutable.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favorite = favoritesMutable[position]
        holder.bind(favorite, object : FavoritesClickListener {
            override fun onFavoritesClick(favorite: Favorites) {
                favoritesClickListener.onFavoritesClick(favorite)
            }
            override fun onRemoveFavorite(favorite: Favorites) {
                favoritesClickListener.onRemoveFavorite(favorite)
                removeFavorite(favorite)
            }
        })
    }
}