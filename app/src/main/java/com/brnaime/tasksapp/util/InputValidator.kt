package com.brnaime.tasksapp.util

object InputValidator {
    fun isInputValid(input: String?): Boolean {
        return !input?.trim().isNullOrEmpty() && input.trim().length > 4
    }
}