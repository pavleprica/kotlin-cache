package io.github.pavleprica.kotlin.cache.time.based

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

/**
 * Short time based cache that will hold the cache for 1 minute
 */
class ShortTimeBasedCache<T, E>: CustomTimeBasedCache<T, E>(ONE_MINUTE) {

}

inline fun <reified T, reified E> shortTimeBasedCache(): ShortTimeBasedCache<T, E> = ShortTimeBasedCache()