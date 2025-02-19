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

class CryptoDetailsViewModel(private val cryptoRepository: CryptoRepository): ViewModel() {

    private val _crypto = MutableLiveData<Crypto?>()
    val crypto: LiveData<Crypto?> get() = _crypto

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchCryptoById(id: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = cryptoRepository.getCryptoById(id)
                _crypto.postValue(result)
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun addCryptoToFavorites(cryptoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val crypto = cryptoRepository.getCryptoById(cryptoId)
            if (crypto != null)
                cryptoRepository.addToFavorites(crypto)
        }
    }

    fun removeCryptoFromFavorites(cryptoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.removeFromFavorites(cryptoId)
        }
    }

    fun isCryptoFavorite(cryptoId: String): LiveData<Boolean> {
        return cryptoRepository.isCryptoFavorite(cryptoId)
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CryptoApplication)
                CryptoDetailsViewModel(application.cryptoRepository)
            }
        }
    }
}