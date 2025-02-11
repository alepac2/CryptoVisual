package dev.alexpace.cryptovisual

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dev.alexpace.cryptovisual.data.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val cryptoRepository = CryptoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //! TEST & TEMPORARY
        testApiCall()
    }




    //! TEST & TEMPORARY
    private fun testApiCall() {
        lifecycleScope.launch {
            try {
                val cryptos = withContext(Dispatchers.IO) {
                    cryptoRepository.getCryptos()
                }
                cryptos.forEach {
                    Log.d("MainActivity", "Crypto: ${it.name}, Price: ${it.currentPrice}, Image: ${it.image}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching data: ${e.message}")
            }
        }
    }
}