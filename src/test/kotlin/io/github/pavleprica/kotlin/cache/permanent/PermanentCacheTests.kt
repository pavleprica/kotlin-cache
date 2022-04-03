package io.github.pavleprica.kotlin.cache.permanent

import io.github.pavleprica.kotlin.cache.BaseTest
import io.github.pavleprica.kotlin.cache.Cache
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class PermanentCacheTests: BaseTest() {

    private lateinit var cache: Cache<Int, Int>

    init {
        context("When using permanent cache") {

            beforeEach { initCache() }

            context("When checking size") {

                test("Should be exact size as set times") {
                    val listSize = 3
                    val list = createMockList(listSize)
                    list.forEach { (key, value) -> cache[key] = value }
                    cache.size shouldBe listSize
                }

                test("Should be 0 when cache cleared") {
                    val listSize = 3
                    val list = createMockList(listSize)
                    list.forEach { (key, value) -> cache[key] = value }
                    cache.size shouldBe listSize

                    cache.clear()

                    cache.size shouldBe 0
                    cache.isEmpty() shouldBe true
                }

                test("Should be one minus the set times after single remove") {
                    val listSize = 3
                    val list = createMockList(listSize)
                    list.forEach { (key, value) -> cache[key] = value }
                    cache.remove(list[0].first)
                    cache.size shouldBe listSize - 1
                }

            }

            context("When getting values") {

                test("Should get value after set") {
                    val (key, value) = createMockItem()
                    cache[key] = value

                    val fetchedItem = cache[key]

                    fetchedItem.isPresent shouldBe true
                    fetchedItem.get() shouldBe value
                }

                test("Should get empty when getting without set") {
                    val (key, _) = createMockItem()

                    val fetchedItem = cache[key]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should get empty after getting after clear") {
                    val (key, value) = createMockItem()
                    cache[key] = value
                    cache.clear()

                    val fetchedItem = cache[key]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should get empty after getting after removing item") {
                    val (key, value) = createMockItem()
                    cache[key] = value
                    cache.remove(key)

                    val fetchedItem = cache[key]

                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When removing items") {

                test("Should remove when object present") {
                    val (key, value) = createMockItem()
                    cache[key] = value
                    cache.remove(key)

                    val fetchedItem = cache[key]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should remove when object not present") {
                    val (key, _) = createMockItem()
                    cache.remove(key)

                    val fetchedItem = cache[key]

                    fetchedItem.isEmpty shouldBe true
                }

            }

        }
    }

    private fun initCache() {
        cache = permanentCache()
    }

}