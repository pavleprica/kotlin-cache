package io.github.pavleprica.kotlin.cache.model

/**
 * [KeyTimeWrapper] is used to store timestamps of the expiration time. Using it it will sort them
 * with the time given.
 */
data class KeyTimeWrapper<T>(
    val key: T,
    val time: Long
)