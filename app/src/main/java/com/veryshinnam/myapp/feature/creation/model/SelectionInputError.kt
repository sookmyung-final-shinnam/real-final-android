package com.veryshinnam.myapp.feature.creation.model

private val JAMO_REGEX = Regex("[ㄱ-ㅎㅏ-ㅣ]")
private val NUMBER_REGEX = Regex("[0-9]")

fun validateSelectionInput(input: String): SelectionInputError {
    val trimmed = input.trim()

    if (trimmed.isEmpty()) return SelectionInputError.EMPTY
    if (trimmed.length !in 1..15) return SelectionInputError.LENGTH
    if (JAMO_REGEX.containsMatchIn(trimmed)) return SelectionInputError.EXIST_JAMO
    if (NUMBER_REGEX.containsMatchIn(trimmed)) return SelectionInputError.NUMBER_NOT_ALLOWED
    if (!trimmed.all { it.isLetter() || it == ' ' }) return SelectionInputError.SPECIAL_CHAR

    return SelectionInputError.NONE
}

enum class SelectionInputError {
    NONE,
    EMPTY,
    LENGTH,
    EXIST_JAMO,
    SPECIAL_CHAR,
    NUMBER_NOT_ALLOWED
}