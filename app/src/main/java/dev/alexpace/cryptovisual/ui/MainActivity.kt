package dev.alexpace.cryptovisual.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dev.alexpace.cryptovisual.R
import dev.alexpace.cryptovisual.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /**
     * Initializes the activity and declares the navigation controller
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    /**
     * Necessary for the back button to work
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)
                ?.navController
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}
