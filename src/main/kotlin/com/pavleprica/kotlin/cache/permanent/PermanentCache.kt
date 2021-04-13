package com.pavleprica.kotlin.cache.permanent

import com.pavleprica.kotlin.cache.Cache
import com.pavleprica.kotlin.cache.time.based.ShortTimeBasedCache
import java.util.*


class PermanentCache<T, E>: Cache<T, E> {

    private val cacheMap: HashMap<T, E> = hashMapOf()

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

inline fun <reified T, reified E> permanentCache(): PermanentCache<T, E> = PermanentCache()