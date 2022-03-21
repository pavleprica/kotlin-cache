package io.github.pavleprica.kotlin.cache.time.based

import io.github.pavleprica.kotlin.cache.model.CustomTimeBasedValue
import io.github.pavleprica.kotlin.cache.model.KeyTimeWrapper
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap

open class CustomTimeBasedCache<T, E: Any> (
    private var defaultExpiration: Duration
        ): TimeBasedCache<T, E> {

    private val cacheValueMap: ConcurrentHashMap<T, E> = ConcurrentHashMap()
    private val cacheExpirationList: MutableList<KeyTimeWrapper<T>> = mutableListOf()

    override val size: Int
        get() = cacheValueMap.size

    override val defaultExpirationTime: Duration
        get() = defaultExpiration

    override fun set(key: T, value: E) {
        cacheExpirationList.add(KeyTimeWrapper(key, Duration.ofMillis(System.currentTimeMillis()).plus(defaultExpiration)))
        cacheValueMap[key] = value
    }

    override fun get(key: T): Optional<E> {
        expirationTimeCheckAndClean()

        val value = cacheValueMap[key]

        return if (value != null) Optional.of(value) else Optional.empty()
    }

    private fun expirationTimeCheckAndClean() {
        val currentTime = Duration.ofMillis(System.currentTimeMillis())
        cacheExpirationList.sortBy { it.time }
        val removeList = mutableListOf<KeyTimeWrapper<T>>()

        cacheExpirationList.forEach {
            if (it.time > currentTime) { return@forEach }
            if (it.time < currentTime) { removeList.add(it) }
        }

        removeList.forEach { cacheValueMap.remove(it.key) }
        cacheExpirationList.removeAll { it.time < currentTime }
    }

    override fun remove(key: T) {
        cacheValueMap.remove(key)
        cacheExpirationList.removeAll { it.key == key }
    }

    override fun clear() {
        cacheValueMap.clear()
        cacheExpirationList.clear()
    }

    override fun isEmpty(): Boolean {
        return cacheValueMap.isEmpty()
    }

    override fun setDefaultExpirationTime(expirationTime: Duration) {
        require(expirationTime >= Duration.ZERO)

        defaultExpiration = expirationTime
    }

    override fun set(key: T, value: CustomTimeBasedValue<E>) {
        require(value.expirationTime >= Duration.ZERO)

        cacheExpirationList.add(KeyTimeWrapper(key, Duration.ofMillis(System.currentTimeMillis()).plus(value.expirationTime)))
        cacheValueMap[key] = value.value
    }
}

inline fun <reified T, reified E: Any> customTimeBasedCache(expirationTime: Duration): CustomTimeBasedCache<T, E> = CustomTimeBasedCache(expirationTime)
