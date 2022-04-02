package io.github.pavleprica.kotlin.cache.time.based

import io.github.pavleprica.kotlin.cache.model.CustomTimeBasedValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
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

        context("When using time based cache with expiration") {

            context("When checking size") {

                test("Should be 0 after value expires") {
                    val (key, value) = createMockItem()
                    cache[key] = CustomTimeBasedValue(value, Duration.ofMillis(1L))

                    Thread.sleep(2L)

                    val fetchedItem = cache[key]
                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When overriding default expiration time") {

                test("Should override default value") {
                    cache.setDefaultExpirationTime(Duration.ofMillis(5L))

                    cache.defaultExpirationTime shouldBe Duration.ofMillis(5L)
                }

                test("When overriding default value and value expires") {
                    val (key, value) = createMockItem()
                    cache.setDefaultExpirationTime(Duration.ofMillis(1L))

                    cache[key] = value

                    Thread.sleep(2L)

                    val fetchedItem = cache[key]
                    fetchedItem.isEmpty shouldBe true
                }

            }

            context("When using expiration time with below 0") {

                test("Should throw error on overriding default") {
                    shouldThrow<IllegalArgumentException> { cache.setDefaultExpirationTime(Duration.ofMillis(-5L)) }
                }

                test("Should throw error on setting with custom expiration time below 0") {
                    val (key, value) = createMockItem()
                    shouldThrow<IllegalArgumentException> {
                        cache.set(key, CustomTimeBasedValue(value, Duration.ofMillis(-5L)))
                    }
                }

            }

            context("When having multiple items and ordering cache time") {

                test("Should empty first three items") {
                    val mockItemList = listOf(
                        createMockItem(),
                        createMockItem(),
                        createMockItem(),
                    )

                    mockItemList.forEachIndexed { i, it ->
                        cache[it.first + i + 1] = CustomTimeBasedValue(it.second, Duration.ofMillis(2L + i))
                    }

                    createMockItem().let { cache[it.first] = CustomTimeBasedValue(it.second, Duration.ofMillis(1000L)) }

                    Thread.sleep(3)

                    mockItemList.forEachIndexed { i, it ->
                        cache[it.first + i + 1].isEmpty shouldBe true
                    }

                    createMockItem().let { cache[it.first].isPresent shouldBe true }
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