package io.github.pavleprica.kotlin.cache.time.based

/**
 * Short time based cache that will hold the cache for 60 minutes
 */
class LongTimeBasedCache<T, E: Any>: CustomTimeBasedCache<T, E>(ONE_HOUR) {


}

inline fun <reified T, reified E: Any> longTimeBasedCache(): LongTimeBasedCache<T, E> = LongTimeBasedCache()
