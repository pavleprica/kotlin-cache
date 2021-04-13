package io.github.pavleprica.kotlin.cache.time.based

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Short time based cache that will hold the cache for 60 minutes
 */
class LongTimeBasedCache<T, E>: TimeBasedCache<T, E> {

    private var defaultExpiration = ONE_HOUR

    private val cacheValueMap: ConcurrentHashMap<T, E> = ConcurrentHashMap()
    private val cacheExpirationMap: ConcurrentHashMap<T, Long> = ConcurrentHashMap()

    override val size: Int
        get() = cacheValueMap.size

    override val defaultExpirationTime: Long
        get() = defaultExpiration

    override fun set(key: T, value: E) {
        cacheExpirationMap[key] = System.currentTimeMillis() + defaultExpiration
        cacheValueMap[key] = value
    }

    override fun get(key: T): Optional<E> {
        expirationTimeCheckAndClean()

        val value = cacheValueMap[key]

        return if (value != null) Optional.of(value) else Optional.empty()
    }

    private fun expirationTimeCheckAndClean() {
        val removeKeyList = mutableListOf<T>()
        cacheExpirationMap.forEach { (key, time) ->
            if (System.currentTimeMillis() > time) { cacheValueMap.remove(key); removeKeyList.add(key) }
        }
        removeKeyList.forEach { cacheExpirationMap.remove(it) }
    }

    override fun remove(key: T) {
        cacheValueMap.remove(key)
        cacheExpirationMap.remove(key)
    }

    override fun clear() {
        cacheValueMap.clear()
        cacheExpirationMap.clear()
    }

    override fun isEmpty(): Boolean {
        return cacheValueMap.isEmpty()
    }

    override fun setDefaultExpirationTime(expirationTime: Long) {
        require(expirationTime >= 0)

        defaultExpiration = expirationTime
    }

    override fun set(key: T, value: CustomTimeBasedValue<E>) {
        require(value.expirationTime >= 0)

        cacheExpirationMap[key] = System.currentTimeMillis() + value.expirationTime
        cacheValueMap[key] = value.value
    }

}

inline fun <reified T, reified E> longTimeBasedCache(): LongTimeBasedCache<T, E> = LongTimeBasedCache()