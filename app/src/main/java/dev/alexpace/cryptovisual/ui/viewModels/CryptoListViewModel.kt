package dev.alexpace.cryptovisual.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.alexpace.cryptovisual.CryptoApplication
import dev.alexpace.cryptovisual.domain.CryptoRepository
import dev.alexpace.cryptovisual.domain.models.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoListViewModel(private val cryptoRepository: CryptoRepository) : ViewModel() {

    // Public LiveData and private MutableLiveData, for encapsulation and data protection
    // (good practice)
    private val _cryptos = MutableLiveData<List<Crypto>?>()
    val cryptos: LiveData<List<Crypto>?> get() = _cryptos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    /**
     * Fetch cryptos from the repository and update the LiveData.
     */
    fun fetchCryptos() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = cryptoRepository.getCryptos()
                _cryptos.postValue(result)
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    /**
     * Fetch favorite cryptos from the repository and update the same LiveData.
     */
    fun fetchFavoriteCryptos() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = cryptoRepository.getFavoriteCryptos()
                if (result != null) {
                    _cryptos.postValue(result)
                }
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    /**
     * Factory for creating instances of CryptoListViewModel.
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CryptoApplication)
                CryptoListViewModel(application.cryptoRepository)
            }
        }
    }
}
