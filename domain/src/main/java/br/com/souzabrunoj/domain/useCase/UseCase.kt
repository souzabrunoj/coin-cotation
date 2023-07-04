package br.com.souzabrunoj.domain.useCase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<P, R> {

    protected abstract suspend fun call(params: P): Result<R>

    protected abstract val scope: CoroutineScope?

    operator fun invoke(params: P, onSuccess: (R) -> Unit, onFailure: (Throwable) -> Unit) {
        scope?.launch(Dispatchers.IO) {
            val result = call(params)
            withContext(Dispatchers.Main) {
                when {
                    result.isSuccess -> result.onSuccess { onSuccess(it) }
                    result.isFailure -> result.onFailure { onFailure(it) }
                }
            }
        }
    }

    fun cancel() = scope?.coroutineContext?.cancel()
}