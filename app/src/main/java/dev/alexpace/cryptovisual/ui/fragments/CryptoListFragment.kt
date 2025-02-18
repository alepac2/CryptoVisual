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
import dev.alexpace.cryptovisual.ui.CryptoAdapter
import dev.alexpace.cryptovisual.ui.CryptoViewModel

class CryptoListFragment : Fragment() {

    private var _binding: FragmentCryptoListBinding? = null
    private val binding get() = _binding!!

    private val cryptoAdapter by lazy { CryptoAdapter() }
    private val viewModel: CryptoViewModel by viewModels { CryptoViewModel.Factory }

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

        viewModel.fetchCryptos()

        viewModel.cryptos.observe(viewLifecycleOwner, Observer { cryptos ->
            cryptoAdapter.addCryptos(cryptos)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error!!.isNotEmpty()) {
                Snackbar.make(binding.cryptoList, error, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBar.isVisible = loading
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
