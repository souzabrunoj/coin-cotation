package br.com.souzabrunoj.coinquotation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.souzabrunoj.coinquotation.utils.Status.*

fun <T> LiveData<ViewState<T>>.handleWithFlow(
    lifecycleOwner: LifecycleOwner,
    onLoading: () -> Unit = {},
    onFailure: (Throwable) -> Unit = {},
    onComplete: (() -> Unit) = {},
    onSuccess: (T) -> Unit,
    onNeutral: () -> Unit = {},
) {
    this.removeObservers(lifecycleOwner)
    observe(lifecycleOwner) { viewState ->
        when (viewState.status) {
            NEUTRAL -> onNeutral()
            LOADING -> onLoading()
            ERROR -> viewState.throwable?.let {
                onFailure(it)
                onComplete.invoke()
            }

            SUCCESS -> viewState.data?.let {
                onSuccess(it)
                onComplete.invoke()
            }
        }
    }

}

fun <T> MutableLiveData<ViewState<T>>.postSuccess(data: T) = postValue(ViewState(status = SUCCESS, data = data))

fun <T> MutableLiveData<ViewState<T>>.postFailure(throwable: Throwable) = postValue(ViewState(status = ERROR, throwable = throwable))

fun <T> MutableLiveData<ViewState<T>>.postLoading() = postValue(ViewState(status = LOADING))

fun <T> MutableLiveData<ViewState<T>>.postNeutral() = postValue(ViewState(status = NEUTRAL))
