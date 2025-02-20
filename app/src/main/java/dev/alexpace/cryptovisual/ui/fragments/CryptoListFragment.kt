package dev.alexpace.cryptovisual.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.alexpace.cryptovisual.databinding.FragmentCryptoListBinding
import dev.alexpace.cryptovisual.ui.adapters.CryptoAdapter
import dev.alexpace.cryptovisual.ui.viewModels.CryptoListViewModel

class CryptoListFragment : Fragment() {

    // Variables and values
    private var _binding: FragmentCryptoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CryptoListViewModel by viewModels { CryptoListViewModel.Factory }

    // Setting up the adapter as seen in class, by passing the click listener
    private val cryptoAdapter by lazy {
        CryptoAdapter { crypto ->
            val action =
                CryptoListFragmentDirections
                    .actionCryptoListFragmentToCryptoDetailsFragment(crypto.id)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * When the view has already been created, we set the adapter and initialize
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cryptoList.adapter = cryptoAdapter
        initCryptos()
        initListeners()
    }

    /**
     * When we go back to the Fragment after clicking the back button, from the
     * Details Fragment
     */
    override fun onResume() {
        super.onResume()

        if (binding.switchFavorites.isChecked) {
            viewModel.fetchFavoriteCryptos()
        } else {
            viewModel.fetchCryptos()
        }

        binding.switchFavorites.setOnCheckedChangeListener { _, isChecked ->
            cryptoAdapter.clearCryptos()
            if (isChecked) {
                viewModel.fetchFavoriteCryptos()
            } else {
                viewModel.fetchCryptos()
            }
        }
    }

    /**
     * Initialize searchBar listener. Submit returns false, change returns true,
     * as we don't want to submit
     */
    private fun initListeners() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cryptoAdapter.filter(newText.orEmpty())
                return true
            }
        })
    }

    /**
     * Initializing cryptos with Observers, like a hook for when the data changes
     * in the ViewModel
     */
    private fun initCryptos() {
        viewModel.cryptos.observe(viewLifecycleOwner) { cryptos ->
            cryptoAdapter.clearCryptos()
            if (cryptos != null) {
                cryptoAdapter.addCryptos(cryptos)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Snackbar.make(binding.cryptoList, error, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

        if (viewModel.cryptos.value.isNullOrEmpty()) {
            viewModel.fetchCryptos()
        }
    }

    /**
     * When the view is destroyed, we set the binding to null (good practice?)
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}