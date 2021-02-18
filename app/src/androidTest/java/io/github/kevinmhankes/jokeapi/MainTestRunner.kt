package io.github.kevinmhankes.jokeapi

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * @author Kevin.
 * Created/Modified on February 16, 2021
 */

/**
 * Set up test runner for implementation tests to use [TestMainApplication] instead of the
 * production version application class.
 *
 * Marked as unused by IDE, but is reference in app/build.gradle
 */
class MainTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestMainApplication::class.java.name, context)
    }
}