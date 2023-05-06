package lynx.internship.tv.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import lynx.internship.tv.databinding.ActivityMainBinding
import android.view.Menu
import lynx.internship.tv.R
import lynx.internship.tv.domain.TvTrackerFactory
import lynx.internship.tv.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        setupWithNavController(bottomNavigationView, navController)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun logout() {
        TvTrackerFactory.createTvTrackerAuthRepository().logOut()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}