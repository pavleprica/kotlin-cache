package io.github.pavleprica.kotlin.cache.permanent

import io.github.pavleprica.kotlin.cache.Cache
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class PermanentCache<T: Any, E: Any>: Cache<T, E> {

    private val cacheMap: ConcurrentHashMap<T, E> = ConcurrentHashMap()

    override val size: Int
        get() = cacheMap.size

    override fun set(key: T, value: E) {
        cacheMap[key] = value
    }

    override fun get(key: T): Optional<E> {
        val value = cacheMap[key]

        return if (value != null) Optional.of(value) else Optional.empty()
    }

    override fun remove(key: T) {
        cacheMap.remove(key)
    }

    override fun clear() {
        cacheMap.clear()
    }

    override fun isEmpty(): Boolean {
        return cacheMap.isEmpty()
    }
}

inline fun <reified T: Any, reified E: Any> permanentCache(): PermanentCache<T, E> = PermanentCache()
