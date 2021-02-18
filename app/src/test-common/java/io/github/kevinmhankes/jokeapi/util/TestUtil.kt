package io.github.kevinmhankes.jokeapi.util

import androidx.lifecycle.MutableLiveData
import io.github.kevinmhankes.jokeapi.model.Joke

/**
 * @author Kevin.
 * Created/Modified on February 16, 2021
 */

val testJokes = MutableLiveData(listOf(
    Joke(1, "Spooky", "Why did the ghost go inside the bar?", "For the boos."),
    Joke(2, "Programming", "Why does no one like SQLrillex?", "He keeps dropping the database."),
    Joke(3, "Pun", "What kind of doctor is Dr. Pepper?", "He's a fizzician")
))