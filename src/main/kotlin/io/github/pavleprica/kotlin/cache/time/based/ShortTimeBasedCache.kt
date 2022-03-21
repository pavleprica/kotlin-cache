package io.github.pavleprica.kotlin.cache.time.based

/**
 * Short time based cache that will hold the cache for 1 minute
 */
class ShortTimeBasedCache<T: Any, E: Any>: CustomTimeBasedCache<T, E>(ONE_MINUTE) {

}

inline fun <reified T: Any, reified E: Any> shortTimeBasedCache(): ShortTimeBasedCache<T, E> = ShortTimeBasedCache()
