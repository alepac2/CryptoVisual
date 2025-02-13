package dev.alexpace.cryptovisual.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import dev.alexpace.cryptovisual.databinding.ActivityMainBinding
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val cryptoAdapter by lazy { CryptoAdapter() }

    private val viewModel: CryptoViewModel by viewModels { CryptoViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cryptoList.adapter = cryptoAdapter

        viewModel.fetchCryptos()

        viewModel.cryptos.observe(this, Observer { cryptos ->
            cryptoAdapter.addCryptos(cryptos)
        })

        viewModel.error.observe(this, Observer { error ->
            if (error!!.isNotEmpty()) {
                Snackbar.make(binding.cryptoList, error, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loading.observe(this, Observer { loading ->
            binding.progressBar.isVisible = loading
        })
    }
}
