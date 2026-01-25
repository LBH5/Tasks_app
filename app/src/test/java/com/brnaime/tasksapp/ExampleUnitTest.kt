package com.brnaime.tasksapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun substraction_isCorrect() {
        // Perform an action
        val result = 2 - 2
        // Assert the result
        assertEquals(0, result)
    }

    @Test
    fun greet_returnsCorrectGreeting() {

        // Perform an action
        val greeter = Greeter()
        val result = greeter.greet("Alice")

        // Assert the result
        assertEquals("Hello, Alice!", result)

    }
}

class Greeter(){

    fun greet(name: String): String {
        return "Hello, $name!"
    }
}
