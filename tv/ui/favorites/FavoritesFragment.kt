package lynx.internship.tv.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import lynx.internship.tv.R
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.databinding.FragmentFavoritesBinding
import lynx.internship.tv.domain.FavoritesRepository
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.favorites.FavoritesAdapter
import lynx.internship.tv.ui.details.DetailsActivity
import lynx.internship.tv.favorites.FavoritesAdapter.*

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        addingFavorites()

        return binding.root
    }

    fun navigateToDetailsActivity(favoritesId: Long) {

        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("id", favoritesId.toString())
        activity?.startActivity(intent)
    }

    fun addingFavorites() {

        val repository: FavoritesRepository? = TvTrackerFactory.createFavoritesRepository(this@FavoritesFragment.context)

        repository?.fetchFavoritesFireBase(object : ResponseListener<List<Favorites>> {
            override fun onSuccess(value: List<Favorites>) {
                val favoriteList = value
                Thread(Runnable {
                    retainCommonFavorites(favoriteList)
                }).start()
            }
            override fun onError(error: Exception) {
                val wrong: String = getString(R.string.wrong)
                val ok: String = getString(R.string.ok)

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(wrong)
                builder.setMessage(error.message)
                builder.setPositiveButton(ok, null)

                val dialog = builder.create()
                dialog.show()
            }
        })
    }

    //function where we retain what's from Favorites and what's from Room ( the common data )
    fun retainCommonFavorites(favoriteList : List<Favorites>) {
        val repository: FavoritesRepository? = TvTrackerFactory.createFavoritesRepository(this@FavoritesFragment.context)

        repository?.fetchFavorites(object : ResponseListener<List<Favorites>>{
            override fun onSuccess(value: List<Favorites>) {
                activity?.runOnUiThread {
                    binding.rvFavorites.layoutManager = LinearLayoutManager(context)

                    val sortedList = value.sortedByDescending { it.dateAdded }
                    var mutableFavorites : MutableList<Favorites> = sortedList.toMutableList()
                    mutableFavorites.retainAll(favoriteList)

                    adaptFavorites(mutableFavorites)
                }
            }
            override fun onError(error: Exception) {
                val wrong: String = getString(R.string.wrong)
                Toast.makeText(this@FavoritesFragment.context,wrong, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // we adapt the items that are common, also provide implementations for navigating to detail or removing favoites
    fun adaptFavorites(mutableFavorites : List<Favorites>) {
        val repository: FavoritesRepository? = TvTrackerFactory.createFavoritesRepository(this@FavoritesFragment.context)
        binding.rvFavorites.adapter =
            FavoritesAdapter(mutableFavorites, object : FavoritesClickListener {
                override fun onFavoritesClick(favorites: Favorites) {
                    navigateToDetailsActivity(favorites.fid)
                }

                override fun onRemoveFavorite(favorite: Favorites) {
                    //delete from room
                    TvTrackerFactory.createFavoritesRepository(this@FavoritesFragment.context)
                        ?.deleteFavorites(favorite)

                    //delete from firebase
                    repository?.deleteFromFireBase(favorite)
                }
            })
    }
}