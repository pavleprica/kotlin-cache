package io.github.pavleprica.kotlin.cache

import io.kotest.core.spec.style.FunSpec
import org.mockito.Mockito
import kotlin.random.Random

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

open class BaseTest : FunSpec() {

    init {
        context("Test load") {
            test("Test loaded") {
                println("Success")
            }
        }
    }

    protected val createMockList: (listSize: Int) -> List<Pair<Int, Int>> = { listSize ->
        val list = mutableListOf<Pair<Int, Int>>()
        repeat(listSize) { list.add(Pair(it, it)) }
        list
    }

    protected val createMockItem: () -> Pair<Int, Int> = { Pair(Random(5).nextInt(), Random(5).nextInt()) }
}
