package io.github.pavleprica.kotlin.cache

import java.util.Optional

/**
 * Interface for operations to be done under cache implementation.
 *
 * @param T is the key of the cache.
 * @param E is the value of the cache.
 * @property size is the current size of the cache.
 */
interface Cache<T, E : Any> {

    val size: Int

    /**
     * Adds the value to the cache, or overrides the existing one under the same key.
     *
     * @param key of the value that will be used to access it.
     * @param value of the item to be placed inside the cache.
     */
    operator fun set(key: T, value: E)

    /**
     * Gets the value by key.
     *
     * @param key of the value to search with.
     * @return Optional of [E]. It will be present in case it has been placed inside the cache.
     */
    operator fun get(key: T): Optional<E>

    /**
     * Removes an item from the cache by given key.
     *
     * @param key of the value to search with.
     */
    fun remove(key: T)

    /**
     * Clears the whole cache.
     */
    fun clear()

    /**
     * Checks if the cache is empty.
     *
     * @return [Boolean] in case cache is empty.
     */
    fun isEmpty(): Boolean
}
