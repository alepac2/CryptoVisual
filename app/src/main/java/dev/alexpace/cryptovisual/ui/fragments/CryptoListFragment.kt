package dev.alexpace.cryptovisual.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dev.alexpace.cryptovisual.databinding.FragmentCryptoListBinding
import dev.alexpace.cryptovisual.ui.adapters.CryptoAdapter
import dev.alexpace.cryptovisual.ui.viewModels.CryptoListViewModel

class CryptoListFragment : Fragment() {

    private var _binding: FragmentCryptoListBinding? = null
    private val binding get() = _binding!!

    private val cryptoAdapter by lazy { CryptoAdapter() }
    private val viewModel: CryptoListViewModel by viewModels { CryptoListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cryptoList.adapter = cryptoAdapter
        initCryptos()
    }

    override fun onResume() {
        super.onResume()

        binding.switchFavorites.setOnCheckedChangeListener { _, isChecked ->
            cryptoAdapter.clearCryptos()
            if (isChecked) {
                viewModel.fetchFavoriteCryptos()
            } else {
                viewModel.fetchCryptos()
            }
        }
    }

    private fun initCryptos() {
        viewModel.cryptos.observe(viewLifecycleOwner, Observer { cryptos ->
            cryptoAdapter.clearCryptos()
            if (cryptos != null) {
                cryptoAdapter.addCryptos(cryptos)
            }
        })

        // Always observe errors and loading states.
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (!error.isNullOrEmpty()) {
                Snackbar.make(binding.cryptoList, error, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

        // Trigger initial load
        if (viewModel.cryptos.value.isNullOrEmpty()) {
            viewModel.fetchCryptos()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
