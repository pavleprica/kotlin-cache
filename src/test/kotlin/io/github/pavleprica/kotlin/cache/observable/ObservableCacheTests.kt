package io.github.pavleprica.kotlin.cache.observable

import io.github.pavleprica.kotlin.cache.BaseTest
import io.github.pavleprica.kotlin.cache.mock
import io.mockk.confirmVerified
import io.mockk.verify

class ObservableCacheTests : BaseTest() {

    private lateinit var cache: ObservableCache<Int, Int>

    init {
        context("When using observable cache") {

            beforeEach { initCache() }

            context("When subscribing to the cache") {

                context("When adding items") {

                    test("Should trigger update when subscribed before add") {
                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        val (key, value) = createMockItem()
                        cache[key] = value

                        verify(exactly = 1) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should not trigger update when subscribed after add") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        verify(exactly = 0) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should trigger update to all mocks") {
                        val observerMocks = listOf<Observer<Int>>(
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                        )

                        for (observerMock in observerMocks) {
                            cache.subscribe(observerMock)
                        }

                        val (key, value) = createMockItem()
                        cache[key] = value

                        verify(exactly = 1) {
                            for (observerMock in observerMocks) {
                                observerMock.update(value)
                            }
                        }

                        for (observerMock in observerMocks) {
                            confirmVerified(observerMock)
                        }
                    }
                }

                context("When removing items items") {

                    test("Should trigger update when subscribed before add") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        cache.remove(key)

                        verify(exactly = 1) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should not trigger update when subscribed after add") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        cache.remove(key)

                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        verify(exactly = 0) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should trigger update to all mocks") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        val observerMocks = listOf<Observer<Int>>(
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                        )

                        for (observerMock in observerMocks) {
                            cache.subscribe(observerMock)
                        }

                        cache.remove(key)

                        verify(exactly = 1) {
                            for (observerMock in observerMocks) {
                                observerMock.update(value)
                            }
                        }

                        for (observerMock in observerMocks) {
                            confirmVerified(observerMock)
                        }
                    }
                }

                context("When clearing cache") {

                    test("Should trigger update when subscribed before add") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        cache.clear()

                        verify(exactly = 1) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should not trigger update when subscribed after add") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        cache.clear()

                        val observerMock: Observer<Int> = mock()
                        cache.subscribe(observerMock)

                        verify(exactly = 0) {
                            observerMock.update(value)
                        }

                        confirmVerified(observerMock)
                    }

                    test("Should trigger update to all mocks") {
                        val (key, value) = createMockItem()
                        cache[key] = value

                        val observerMocks = listOf<Observer<Int>>(
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                            mock(),
                        )

                        for (observerMock in observerMocks) {
                            cache.subscribe(observerMock)
                        }

                        cache.clear()

                        verify(exactly = 1) {
                            for (observerMock in observerMocks) {
                                observerMock.update(value)
                            }
                        }

                        for (observerMock in observerMocks) {
                            confirmVerified(observerMock)
                        }
                    }
                }
            }

            context("When unsubscribing from the cache") {

                test("Should not receive update on add") {
                    val observerMock: Observer<Int> = mock()
                    cache.subscribe(observerMock)
                    cache.unSubscribe(observerMock)

                    val (key, value) = createMockItem()
                    cache[key] = value

                    verify(exactly = 0) {
                        observerMock.update(value)
                    }

                    confirmVerified(observerMock)
                }

                test("Should not receive update on remove") {
                    val observerMock: Observer<Int> = mock()
                    cache.subscribe(observerMock)
                    cache.unSubscribe(observerMock)

                    val (key, value) = createMockItem()
                    cache[key] = value
                    cache.remove(key)

                    verify(exactly = 0) {
                        observerMock.update(value)
                    }

                    confirmVerified(observerMock)
                }

                test("Should not receive update on clear") {
                    val observerMock: Observer<Int> = mock()
                    cache.subscribe(observerMock)
                    cache.unSubscribe(observerMock)

                    val (key, value) = createMockItem()
                    cache[key] = value
                    cache.clear()

                    verify(exactly = 0) {
                        observerMock.update(value)
                    }

                    confirmVerified(observerMock)
                }

                test("Should not receive update on add with multiple unsubscribes") {
                    val observerMocks = listOf<Observer<Int>>(
                        mock(),
                        mock(),
                        mock(),
                        mock(),
                        mock(),
                    )

                    for (observerMock in observerMocks) {
                        cache.subscribe(observerMock)
                        cache.unSubscribe(observerMock)
                    }

                    val (key, value) = createMockItem()
                    cache[key] = value

                    verify(exactly = 0) {
                        for (observerMock in observerMocks) {
                            observerMock.update(value)
                        }
                    }

                    for (observerMock in observerMocks) {
                        confirmVerified(observerMock)
                    }
                }
            }
        }
    }

    private fun initCache() {
        cache = observableCache()
    }
}
