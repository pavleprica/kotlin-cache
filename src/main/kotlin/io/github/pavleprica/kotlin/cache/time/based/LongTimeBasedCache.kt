package io.github.pavleprica.kotlin.cache.time.based

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Short time based cache that will hold the cache for 60 minutes
 */
class LongTimeBasedCache<T, E>: CustomTimeBasedCache<T, E>(ONE_HOUR) {


}

inline fun <reified T, reified E> longTimeBasedCache(): LongTimeBasedCache<T, E> = LongTimeBasedCache()