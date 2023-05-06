package lynx.internship.tv.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import lynx.internship.tv.R
import lynx.internship.tv.data.room.Favorites
import lynx.internship.tv.databinding.ActivityLoginBinding
import lynx.internship.tv.domain.ResponseListener
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.domain.auth.AuthResponseListener
import lynx.internship.tv.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSignin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val completeall: String = getString(R.string.completefields)

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, completeall, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.pbLoadingBar.visibility = View.VISIBLE

            TvTrackerFactory.createTvTrackerAuthRepository().login(email, password, object :
                AuthResponseListener {
                override fun onSuccess(success: Boolean) {

                    if (success == true) {

                        /*val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()*/
                        fetchFirebaseFavorite()
                    }
                }

                override fun onError(error: Exception) {
                    binding.pbLoadingBar.visibility = View.GONE
                    val authfail: String = getString(R.string.authfailed)
                    val ok: String = getString(R.string.ok)
                    val builder = AlertDialog.Builder(this@LoginActivity)
                    builder.setTitle(authfail)
                    builder.setMessage(error.message)
                    builder.setPositiveButton(ok, null)
                    val dialog = builder.create()
                    dialog.show()
                }
            })
        }
        binding.tvRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //function that does the fetching for favorite data from Firebase
    fun fetchFirebaseFavorite() {
        val repository = TvTrackerFactory.createFavoritesRepository(this)

        repository?.fetchFavoritesFireBase(object : ResponseListener<List<Favorites>> {

            override fun onSuccess(value: List<Favorites>) {
                searchRoomFavorite(value) // function that does the searching for favorite data from Room
            }

            override fun onError(error: Exception) {
                binding.pbLoadingBar.visibility = View.GONE
                val wrong: String = getString(R.string.wrong)
                Toast.makeText(this@LoginActivity, wrong, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun searchRoomFavorite(value: List<Favorites>) { // function that fetches favorite data from Room
        val repository = TvTrackerFactory.createFavoritesRepository(this)
        value.forEach {
            val resultOfSearch = repository?.searchFavorites(it.fid)
            if (resultOfSearch == false) {
                repository.insertFavorites(it)
            }
        }
        runOnUiThread(Runnable {
            binding.pbLoadingBar.visibility = View.GONE
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        })
    }
}


