package dev.alexpace.cryptovisual

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import dev.alexpace.cryptovisual.data.CryptoRepository
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //! TEST & TEMPORARY
    private lateinit var db: AppDatabase
    private lateinit var cryptoRepository: CryptoRepository
    private val dbName = "cryptos.db"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //! TEST & TEMPORARY
        testApiCall()
    }


    private fun initRepository() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, dbName
        ).build()

        cryptoRepository = CryptoRepository(db)
    }

    //! TEST & TEMPORARY
    private fun testApiCall() {

        initRepository()

        lifecycleScope.launch {
            try {
                val cryptos = withContext(Dispatchers.IO) {
                    cryptoRepository.getCryptos()
                }
                cryptos.forEach {
                    Log.d("MainDebug", "Crypto: ID: ${it.id}, Symbol: ${it.symbol}, Name: ${it.name}, Price: ${it.currentPrice}, Image: ${it.image}")
                }
            } catch (e: Exception) {
                Log.e("MainDebug", "Error fetching data: ${e.message}")
            }
        }
    }
}