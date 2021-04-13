package io.github.pavleprica.kotlin.cache

import io.kotest.core.spec.style.FunSpec
import org.mockito.Mockito

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

class BaseTest: FunSpec() {

    init {
        context("Test load") {
            test("Test loaded") {
                println("Success")
            }
        }
    }

}