package io.github.pavleprica.kotlin.cache.time.based

import io.github.pavleprica.kotlin.cache.Cache
import io.github.pavleprica.kotlin.cache.model.CustomTimeBasedValue
import java.time.Duration

/**
 * [TimeBasedCache] is a expansion of the cache interface.
 * Used to implement a time based cache that will be responsible for checking if the keys
 * have expired and remove them the map.
 *
 * @param T is the key of the cache.
 * @param E is the value of the cache.
 * @property defaultExpirationTime is the default value for setting the expiration time.
 */
interface TimeBasedCache<T, E : Any> : Cache<T, E> {

    val defaultExpirationTime: Duration

    /**
     * Overrides the default value of the expiration time and sets a new values.
     *
     * @param expirationTime the new expiration time.
     */
    fun setDefaultExpirationTime(expirationTime: Duration)

    /**
     * Sets a value with a custom expiration time without influencing the default one.
     *
     * @param key of the value that will be used to access it.
     * @param [CustomTimeBasedValue] a value with the custom expiration time
     */
    operator fun set(key: T, value: CustomTimeBasedValue<E>)
}

val ONE_MINUTE = Duration.ofMinutes(1L)

val ONE_HOUR = Duration.ofHours(1L)
