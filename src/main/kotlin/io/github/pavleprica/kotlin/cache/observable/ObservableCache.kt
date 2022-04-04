package io.github.pavleprica.kotlin.cache.observable

import io.github.pavleprica.kotlin.cache.permanent.PermanentCache

/**
 * Observable cache is used if we want to be notified on changes happening inside the cache.
 * The changes to be notified on are adding and removing values from the cache.
 */
class ObservableCache<T, E : Any> : PermanentCache<T, E>() {

    private val cacheObservers = mutableListOf<Observer<E>>()

    /**
     * Subscribes a [Observer] to changes done on cache.
     * The update method is being invoked on adding and deleting values.
     *
     * @param observer to be notified on add or remove.
     * @see [Observer]
     */
    fun subscribe(observer: Observer<E>) {
        cacheObservers.add(observer)
    }

    /**
     * Unsubscribes a [Observer] to changes done on cache.
     *
     * @param observer to be unsubscribed from changes.
     * @see [Observer]
     */
    fun unsubscribe(observer: Observer<E>) {
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
