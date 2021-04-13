package com.pavleprica.kotlin.cache.time.based

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class TimeBasedCacheTests: FunSpec() {

    private lateinit var cache: TimeBasedCache<Int, Int>

    init {
        context("When using time based cache without expiration") {

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
                    val mockItem = createMockItem()
                    cache[mockItem.first] = mockItem.second

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isPresent shouldBe true
                    fetchedItem.get() shouldBe mockItem.second
                }

                test("Should get empty when getting without set") {
                    val mockItem = createMockItem()

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should get empty after getting after clear") {
                    val mockItem = createMockItem()
                    cache[mockItem.first] = mockItem.second
                    cache.clear()

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should get empty after getting after removing item") {
                    val mockItem = createMockItem()
                    cache[mockItem.first] = mockItem.second
                    cache.remove(mockItem.first)

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When removing items") {

                test("Should remove when object present") {
                    val mockItem = createMockItem()
                    cache[mockItem.first] = mockItem.second
                    cache.remove(mockItem.first)

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isEmpty shouldBe true
                }

                test("Should remove when object not present") {
                    val mockItem = createMockItem()
                    cache.remove(mockItem.first)

                    val fetchedItem = cache[mockItem.first]

                    fetchedItem.isEmpty shouldBe true
                }

            }

        }

        context("When using time based cache with expiration") {

            context("When checking size") {

                test("Should be 0 after value expires") {
                    val mockItem = createMockItem()
                    cache[mockItem.first] = CustomTimeBasedValue(mockItem.second, 1)

                    Thread.sleep(2L)

                    val fetchedItem = cache[mockItem.first]
                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When overriding default expiration time") {

                test("Should override default value") {
                    cache.setDefaultExpirationTime(5L)

                    cache.defaultExpirationTime shouldBeExactly 5L
                }

                test("When overriding default value and value expires") {
                    val mockItem = createMockItem()
                    cache.setDefaultExpirationTime(1L)

                    cache[mockItem.first] = mockItem.second

                    Thread.sleep(2L)

                    val fetchedItem = cache[mockItem.first]
                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When using expiration time with below 0") {

                test("Should throw error on overriding default") {
                    shouldThrow<IllegalArgumentException> { cache.setDefaultExpirationTime(-5) }
                }

                test("Should throw error on setting with custom expiration time below 0") {
                    val mockItem = createMockItem()
                    shouldThrow<IllegalArgumentException> {
                        cache.set(mockItem.first, CustomTimeBasedValue(mockItem.second, -5))
                    }
                }

            }

        }
    }

    private fun initCache() { cache = shortTimeBasedCache() }

    private val createMockList: (listSize: Int) -> List<Pair<Int, Int>> = { listSize ->
        val list = mutableListOf<Pair<Int, Int>>()
        repeat(listSize) { list.add(Pair(it, it)) }
        list
    }

    private val createMockItem: () -> Pair<Int, Int> = { Pair(Random(5).nextInt(), Random(5).nextInt()) }

}