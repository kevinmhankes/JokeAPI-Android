package io.github.kevinmhankes.jokeapi.testing

/**
 * @author Kevin.
 * Created/Modified on February 16, 2021
 */

/**
 * This annotation opens some classes for mocking purposes while they are final in release builds.
 * This is not present in release builds.
 * See a debug version of this file under app/src/release/java
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Annotate a class with [OpenForTesting] to be extendable in debug builds for mocking in unit tests
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting