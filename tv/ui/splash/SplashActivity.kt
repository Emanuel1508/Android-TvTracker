package lynx.internship.tv.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import lynx.internship.tv.ui.MainActivity
import lynx.internship.tv.R
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.databinding.ActivitySplashScreenBinding
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.domain.auth.AuthResponseListener
import lynx.internship.tv.ui.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // action bar utility method

        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Glide.with(this).load(R.drawable.splash_gif).into(binding.ivLottieAnimation)

        Handler().postDelayed({
            // to check whether the "if..else" statement works properly I commented the "if" condition
            //and checked only the "else" condition and for the "if" statement when uncommented I put a Log to check
            // which user is signed in
            Thread(Runnable {


            TvTrackerFactory.createTvTrackerAuthRepository()
                .isAuthenticated(object : AuthResponseListener {
                    override fun onSuccess(value: Boolean) {
                        if (value == true) {
                            fetchFirebaseFavorite()
                        } else {
                            runOnUiThread(Runnable {
                                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                                finish()
                            })

                        }
                    }

                    override fun onError(error: Exception) {
                        val wrong: String = getString(R.string.wrong)
                        runOnUiThread(Runnable {
                            Toast.makeText(this@SplashActivity, wrong, Toast.LENGTH_SHORT).show()
                        })

                    }
                })
            }).start()
        }, 2000)//2 seconds delay
    }


    //function that does the fetching for favorite data from Firebase
    fun fetchFirebaseFavorite() {
        val repository = TvTrackerFactory.createFavoritesRepository(this)

        repository?.fetchFavoritesFireBase(object : ResponseListener<List<Favorites>> {

            override fun onSuccess(value: List<Favorites>) {
                searchRoomFavorite(value) // function that does the searching for favorite data from Room
            }

            override fun onError(error: Exception) {
                val wrong: String = getString(R.string.wrong)
                runOnUiThread(Runnable {
                    Toast.makeText(this@SplashActivity, wrong, Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    fun searchRoomFavorite(value: List<Favorites>) { // function that fetches favorite data from Room
        val repository = TvTrackerFactory.createFavoritesRepository(this)
        value.forEach {
            val resultOfSearch=repository?.searchFavorites(it.fid)
            if(resultOfSearch == false){
                repository.insertFavorites(it)
            }
        }

        runOnUiThread(Runnable{
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        })
    }
}





