package io.github.kevinmhankes.jokeapi.util

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

/**
 * @author Kevin.
 * Created/Modified on February 17, 2021
 */

/**
 * A [TestRule] to allow for the loading and unloading of koin modules before and after tests
 */
class InjectKoinModuleRule(
    private vararg val modules: Module = emptyArray()
) : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                loadKoinModules(modules.asList())
                // Run the Test
                try {
                    base?.evaluate()
                } finally {
                    unloadKoinModules(modules.asList())
                }
            }
        }
    }
}