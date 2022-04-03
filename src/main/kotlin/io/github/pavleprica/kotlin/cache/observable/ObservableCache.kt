package io.github.pavleprica.kotlin.cache.observable

import io.github.pavleprica.kotlin.cache.permanent.PermanentCache


class ObservableCache<T, E: Any> : PermanentCache<T, E>() {

    fun subscribeOnAll(callback: () -> Unit) {
        TODO("Not implemented yet")
    }

    fun subscribeOnAll(callback: (E) -> Unit) {
        TODO("Not implemented yet")
    }

    fun subscribeOnAdd(callback: () -> Unit) {
        TODO("Not implemented yet")
    }

    fun subscribeOnAdd(callback: (E) -> Unit) {
        TODO("Not implemented yet")
    }

    fun subscribeOnDelete(callback: () -> Unit) {
        TODO("Not implemented yet")
    }

    fun subscribeOnDelete(callback: (E) -> Unit) {
        TODO("Not implemented yet")
    }

}