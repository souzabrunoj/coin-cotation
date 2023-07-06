package br.com.souzabrunoj.coinquotation.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.souzabrunoj.coinquotation.utils.ViewState
import br.com.souzabrunoj.coinquotation.utils.postFailure
import br.com.souzabrunoj.coinquotation.utils.postLoading
import br.com.souzabrunoj.coinquotation.utils.postSuccess
import br.com.souzabrunoj.domain.model.CoinDetails
import br.com.souzabrunoj.domain.repository.CoinDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val repository: CoinDetailsRepository) : ViewModel() {

    private val _coinDetails = MutableLiveData<ViewState<CoinDetails>>()
    val coinDetails: LiveData<ViewState<CoinDetails>> = _coinDetails

    fun getCoinDetails(id: String) = viewModelScope.launch {
        _coinDetails.postLoading()
        repository.getCoinDetails(id)
            .onSuccess { _coinDetails.postSuccess(it) }
            .onFailure { _coinDetails.postFailure(it) }
    }
}