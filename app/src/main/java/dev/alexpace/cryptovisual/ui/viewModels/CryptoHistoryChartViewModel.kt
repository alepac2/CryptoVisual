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
import dev.alexpace.cryptovisual.domain.models.CryptoHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoHistoryChartViewModel(private val cryptoRepository: CryptoRepository) : ViewModel() {

    // Public LiveData and private MutableLiveData, for encapsulation and data protection
    private val _crypto = MutableLiveData<CryptoHistory?>()
    val crypto: LiveData<CryptoHistory?> get() = _crypto

    private val _error = MutableLiveData<String?>()

    /**
     * Fetch crypto history from the repository and update the LiveData
     */
    fun getCryptoHistory(cryptoId: String): LiveData<List<CryptoHistory>> {
        val historyLiveData = MutableLiveData<List<CryptoHistory>>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val history = cryptoRepository.getCryptoHistory(cryptoId)

                historyLiveData.postValue(history)
            } catch (e: Exception) {
                _error.postValue(
                    "An error occurred while fetching crypto history: ${e.message}"
                )
            }
        }
        return historyLiveData
    }

    fun getCryptoHistoryByDateRange(
        cryptoId: String, dateStart: String, dateEnd: String
    ): LiveData<List<CryptoHistory>> {
        val historyLiveData = MutableLiveData<List<CryptoHistory>>()

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val history: List<CryptoHistory> = if (checkDateRange(dateStart, dateEnd)) {
                    cryptoRepository
                        .getCryptoHistoryByDateRange(
                            cryptoId, dateStart.toLong(), dateEnd.toLong()
                        )
                } else {
                    cryptoRepository.getCryptoHistory(cryptoId)
                }
                historyLiveData.postValue(history)
            } catch (e: Exception) {
                _error.postValue(
                    "An error occurred while fetching crypto history: ${e.message}"
                )
            }
        }
        return historyLiveData
    }

    private fun checkDateRange(dateStart: String, dateEnd: String): Boolean {
        val dateStartLong = dateStart.toLongOrNull()
        val dateEndLong = dateEnd.toLongOrNull()
        return dateStartLong != null && dateEndLong != null && dateStartLong < dateEndLong
    }

    /**
     * Factory for creating instances of CryptoHistoryChartViewModel
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CryptoApplication)
                CryptoHistoryChartViewModel(application.cryptoRepository)
            }
        }
    }
}