package io.github.pavleprica.kotlin.cache.time.based

/**
 * Short time based cache that will hold the cache for 1 minute
 */
class ShortTimeBasedCache<T, E>: CustomTimeBasedCache<T, E>(ONE_MINUTE) {

}

inline fun <reified T, reified E> shortTimeBasedCache(): ShortTimeBasedCache<T, E> = ShortTimeBasedCache()