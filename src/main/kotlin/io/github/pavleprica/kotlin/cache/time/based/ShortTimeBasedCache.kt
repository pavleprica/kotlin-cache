package io.github.pavleprica.kotlin.cache.time.based

/**
 * Short time based cache that will hold the cache for 1 minute
 */
class ShortTimeBasedCache<T, E : Any> : CustomTimeBasedCache<T, E>(ONE_MINUTE)

inline fun <reified T, reified E : Any> shortTimeBasedCache(): ShortTimeBasedCache<T, E> = ShortTimeBasedCache()
