package com.brnaime.tasksapp

import com.brnaime.tasksapp.util.InputValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class InputValidationTest {

    @Test
    fun titleInputValidator_returnsFalse_whenInputIsEmpty() {
        // Perform
        val input = ""
        val result = InputValidator.isInputValid(input)

        // Assert
        assertFalse(result)
    }

    @Test
    fun titleInputValidator_returnsFalse_whenInputIsNull() {
        // Perform
        val input = null
        val result = InputValidator.isInputValid(input)

        // Assert
        assertFalse(result)
    }

    @Test
    fun titleInputValidator_returnsFalse_whenInputIsLessThanFiveCharacters() {
        // Perform
        val input = "abcd"
        val result = InputValidator.isInputValid(input)

        // Assert
        assertFalse(result)
    }

    @Test
    fun titleInputValidator_returnsFalse_whenInputIsBlank() {
        // Perform
        val input = "   "
        val result = InputValidator.isInputValid(input)

        // Assert
        assertFalse(result)
    }

    @Test
    fun titleInputValidator_returnsTrue_whenInputIsValid() {
        // Perform
        val input = "valid input"
        val result = InputValidator.isInputValid(input)

        // Assert
        assertTrue(result)
    }

}