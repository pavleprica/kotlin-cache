package io.github.pavleprica.kotlin.cache.observable

import io.github.pavleprica.kotlin.cache.permanent.PermanentCache

class ObservableCache<T, E : Any> : PermanentCache<T, E>() {

    private val cacheObservers = mutableListOf<Observer<E>>()

    fun subscribe(observer: Observer<E>) {
//        cacheObservers.add(observer)
        TODO("Not yet implemented")
    }

    fun unSubscribe(observer: Observer<E>) {
//        cacheObservers.remove(observer)
        TODO("Not yet implemented")
    }
}

inline fun <reified T, reified E : Any> observableCache(): ObservableCache<T, E> = ObservableCache()
