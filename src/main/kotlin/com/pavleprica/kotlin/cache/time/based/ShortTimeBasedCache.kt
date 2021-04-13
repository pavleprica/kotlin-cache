package com.pavleprica.kotlin.cache.time.based

import java.util.*

/**
 * Short time based cache that will hold the cache for 1 minute
 */
class ShortTimeBasedCache<T, E>: TimeBasedCache<T, E> {

    private val defaultExpiration = ONE_MINUTE

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun set(key: T, value: E) {
        TODO("Not yet implemented")
    }

    override fun get(key: T): Optional<E> {
        TODO("Not yet implemented")
    }

    override fun remove(key: T) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override val defaultExpirationTime: Long
        get() = TODO("Not yet implemented")

    override fun setDefaultExpirationTime(expirationTime: Long) {
        TODO("Not yet implemented")
    }

    override fun set(key: T, value: CustomTimeBasedValue<E>) {
        TODO("Not yet implemented")
    }

}

inline fun <reified T, reified E> shortTimeBasedCache(): ShortTimeBasedCache<T, E> = ShortTimeBasedCache()