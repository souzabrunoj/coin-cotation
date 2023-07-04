package br.com.souzabrunoj.coinquotation.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.souzabrunoj.coinquotation.utils.ViewState
import br.com.souzabrunoj.coinquotation.utils.postFailure
import br.com.souzabrunoj.coinquotation.utils.postLoading
import br.com.souzabrunoj.coinquotation.utils.postSuccess
import br.com.souzabrunoj.domain.model.Coin
import br.com.souzabrunoj.domain.useCase.CoinQuotationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinQuotationViewModel @Inject constructor(private val coinQuotationUseCase: CoinQuotationUseCase) : ViewModel() {

    private val _coins = MutableLiveData<ViewState<List<Coin>>>()
    val coins: LiveData<ViewState<List<Coin>>> = _coins

    fun init() {
        viewModelScope.launch {
            _coins.postLoading()
            coinQuotationUseCase.invoke(
                params = "eur",
                onSuccess = { _coins.postSuccess(it) },
                onFailure = { _coins.postFailure(it) })
        }
    }
}