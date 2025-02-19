package dev.alexpace.cryptovisual.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.alexpace.cryptovisual.R
import dev.alexpace.cryptovisual.databinding.FragmentCryptoDetailsBinding
import dev.alexpace.cryptovisual.domain.models.Crypto
import dev.alexpace.cryptovisual.ui.viewModels.CryptoDetailsViewModel

class CryptoDetailsFragment : Fragment() {

    val args: CryptoDetailsFragmentArgs by navArgs()

    private var _binding: FragmentCryptoDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CryptoDetailsViewModel by viewModels { CryptoDetailsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initCrypto()
        initListeners()
        testCryptoHistoryRepository(cryptoId = args.cryptoId)
    }

    private var isFavorite = false

    private fun initCrypto() {
        val cryptoId = args.cryptoId

        // Use the cryptoId to fetch and display the details of the selected crypto
        viewModel.fetchCryptoById(cryptoId)

        viewModel.crypto.observe(viewLifecycleOwner, Observer { crypto ->
            if (crypto != null) {
                assignCryptoDetails(crypto)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error!!.isNotEmpty()) {
                Snackbar.make(binding.cryptoName, error, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

        viewModel.isCryptoFavorite(cryptoId).observe(viewLifecycleOwner) { favorite ->
            isFavorite = favorite
            binding.btnFavorite.setImageResource(if (favorite) R.drawable.star_filled else R.drawable.star_empty)
        }

    }

    private fun initListeners() {
        binding.btnFavorite.setOnClickListener {
            val cryptoId = args.cryptoId
            if (isFavorite) {
                removeCryptoFromFavorites()
            } else {
                addCryptoToFavorites()
            }
        }
    }

    private fun addCryptoToFavorites() {
        val cryptoId = args.cryptoId
        viewModel.addCryptoToFavorites(cryptoId)
    }

    private fun removeCryptoFromFavorites() {
        val cryptoId = args.cryptoId
        viewModel.removeCryptoFromFavorites(cryptoId)
    }

    @SuppressLint("SetTextI18n")
    private fun assignCryptoDetails(crypto: Crypto) {
        Glide.with(binding.root.context)
            .load(crypto.image)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(binding.cryptoImage)

        binding.cryptoName.text = crypto.name
        binding.cryptoSymbol.text = crypto.symbol
        binding.cryptoPrice.text = crypto.currentPrice.toString()
        binding.cryptoMarketCap.text = crypto.marketCap.toString()
        binding.cryptoVolume.text = crypto.totalVolume.toString()
    }




    //! TEMPORARY
    private fun testCryptoHistoryRepository(cryptoId: String) {
        Log.d("CryptoDetailsFragment", "Testing repository for crypto ID: $cryptoId")

        viewModel.getCryptoHistory(cryptoId).observe(viewLifecycleOwner, Observer { history ->
            if (history.isNotEmpty()) {
                Log.d("CryptoDetailsFragment", "Crypto history fetched: ${history.size} items")
                history.forEach { entry ->
                    Log.d("CryptoDetailsFragment", "Timestamp: ${entry.timestamp}, Price: ${entry.price}")
                }
            } else {
                Log.d("CryptoDetailsFragment", "No crypto history found in repository.")
            }
        })
    }

}