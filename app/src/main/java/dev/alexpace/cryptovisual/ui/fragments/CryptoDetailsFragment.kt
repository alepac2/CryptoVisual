package dev.alexpace.cryptovisual.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import dev.alexpace.cryptovisual.ui.CryptoDetailsViewModel

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

        val cryptoId = args.cryptoId

        // Use the cryptoId to fetch and display the details of the selected crypto
        viewModel.fetchCryptoById(cryptoId)

        viewModel.crypto.observe(viewLifecycleOwner, Observer { crypto ->
            assignCryptoDetails(crypto)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error!!.isNotEmpty()) {
                Snackbar.make(binding.cryptoName, error, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

    }

    private val cryptoImage: ImageView by lazy {
        binding.cryptoImage
    }

    private fun assignCryptoDetails(crypto: Crypto) {
        Glide.with(binding.root.context)
            .load(crypto.image)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(cryptoImage)

        binding.cryptoName.text = crypto.name
        binding.cryptoSymbol.text = crypto.symbol
        binding.cryptoPrice.text = crypto.currentPrice.toString()
        binding.cryptoMarketCap.text = crypto.marketCap.toString()
        binding.cryptoVolume.text = crypto.totalVolume.toString()

    }


}