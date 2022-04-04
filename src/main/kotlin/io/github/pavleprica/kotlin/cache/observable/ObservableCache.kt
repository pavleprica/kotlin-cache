package io.github.pavleprica.kotlin.cache.observable

import io.github.pavleprica.kotlin.cache.permanent.PermanentCache

class ObservableCache<T, E : Any> : PermanentCache<T, E>() {

    private val cacheObservers = mutableListOf<Observer<E>>()

    fun subscribe(observer: Observer<E>) {
        cacheObservers.add(observer)
    }

    fun unSubscribe(observer: Observer<E>) {
        cacheObservers.remove(observer)
    }

    override fun set(key: T, value: E) {
        notifyObservers(value)
        super.set(key, value)
    }

    override fun remove(key: T) {
        val value = super.get(key).orElseThrow()
        notifyObservers(value)
        super.remove(key)
    }

    private fun notifyObservers(value: E) {
        for (cacheObserver in cacheObservers) {
            cacheObserver.update(value)
        }
    }
}

inline fun <reified T, reified E : Any> observableCache(): ObservableCache<T, E> = ObservableCache()
