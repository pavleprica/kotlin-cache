# kotlin-cache

Cache implementation in Kotlin that can be used locally in code.
The idea is to get rid of the constant cache creation per project by a developer.

Current implementations:

## Permanent cache
Permanent cache is a simple cache that will hold the value indefinitely.

### Usage
```kotlin
val cache = PermanentCache<Int, String>()
cache[5] = "This is number five"

val numberFive = cache[5].get()

cache.remove(5)

if (cache.isEmpty()) {
    println("No number five :(")
}
```

## Time based cache
Time based cache will always check if the key has expired and remove it on each
get request. It offers custom setting of the expiration time, alongside the short and long one.

Inside those it can be overridden as well.
- Short = 1 minute expiration time
- Long = 1 hour expiration time

### Usage

```kotlin
// Creates a time based cache of 10 millis
val cache = customTimeBasedCache<Int, String>(10L)

// Overrides the set default time to 5 millis.
// All cache from this point will expire in 5 millis
cache.setDefaultExpirationTime(5L)

// This cache only will expire in 100 millis
cache.set(5, CustomTimeBasedValue("Something cool", 100L))

// Other default time based caches

// One min
val shortCache = shortTimeBasedCache()

// One hour
val longCache = longTimeBasedCache()
```

## How to include it in the project

Just add the maven dependency in your pom file.

```xml
<dependency>
  <groupId>io.github.pavleprica</groupId>
  <artifactId>kotlin-cache</artifactId>
  <version>1.0</version>
</dependency>
```

Or for Gradle Groovy

```groovy
implementation 'io.github.pavleprica:kotlin-cache:1.0'
```