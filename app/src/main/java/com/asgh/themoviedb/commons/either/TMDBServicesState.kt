package com.asgh.themoviedb.commons.either

sealed class ServiceState<out T> {
    object Loading: ServiceState<Nothing>()
    class Failure(val model: FailureModel): ServiceState<Nothing>()
    class Success<out T>(val data: T): ServiceState<T>()

    val isSuccess get() = this is Success<T>

    fun onEach(
        onFailure: (FailureModel) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (T) -> Unit
    ): ServiceState<T> =
        when(this) {
            is Failure -> onFailure(model)
            Loading -> onLoading()
            is Success -> onSuccess(data)
        }.let { this }
}

fun <T> ServiceState<T>?.isSuccess(): Boolean {
    return this?.isSuccess ?: run { false }
}

data class FailureModel(
    val message: String
)