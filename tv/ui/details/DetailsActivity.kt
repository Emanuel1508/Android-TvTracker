package lynx.internship.tv.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import lynx.internship.tv.data.api.DetailMovie
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.databinding.ActivityDetailsBinding
import lynx.internship.tv.domain.FavoritesRepository
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var movieId = intent.getStringExtra("id")

        if (movieId == null) return

        TvTrackerFactory.createTvTrackerRepository()
            .fetchDetail(object : ResponseListener<DetailMovie> {
                override fun onSuccess(detail: DetailMovie) {
                    binding.ivBackButton.setOnClickListener {
                        onBackPressed()
                    }

                    Glide.with(binding.root)
                        .load(detail.backdropUrl)
                        .into(binding.ivBackground)

                    Glide.with(binding.root)
                        .load(detail.posterUrl)
                        .into(binding.ivProfile)

                    binding.tvContent.text = detail.overview
                    binding.tvSeriesName.text = detail.name
                    binding.tvRating.text = detail.vote_average.toString()
                    val repository: FavoritesRepository? =
                        TvTrackerFactory.createFavoritesRepository(this@DetailsActivity.applicationContext)

                    if(repository?.searchFavorites(movieId.toLong())==true){
                        btnAddToFavorite.isEnabled=false;
                    }else{
                        btnAddToFavorite.isEnabled=true;
                    }

                    Thread(Runnable {
                        val currentDate = Date()
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDate = dateFormat.format(currentDate)
                        runOnUiThread {
                            binding.btnAddToFavorite.setOnClickListener {
                                binding.btnAddToFavorite.isEnabled = false
                                repository?.insertFavorites(
                                    Favorites(
                                        movieId.toLong(),
                                        detail.name,
                                        formattedDate,
                                        detail.backdropUrl,

                                    )
                                )

                                repository?.insertFireBase(
                                    Favorites(
                                    movieId.toLong(),
                                        detail.name,
                                        formattedDate,
                                        detail.backdropUrl
                                )
                                )

                            }
                        }

                    }
                    ).start()

                }
                override fun onError(error: Exception) {
                    Toast.makeText(
                        this@DetailsActivity.applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, movieId.toString())
    }
}










