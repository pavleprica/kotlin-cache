package io.github.pavleprica.kotlin.cache.observable

interface Observer<E> {

    fun update(updatedValue: E)

}