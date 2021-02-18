package io.github.kevinmhankes.jokeapi.testing

/**
 * This annotation opens some classes for mocking purposes while they are final in release builds.
 * This is not present in release builds.
 * See a debug version of this file under app/src/debug/java
 */
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting