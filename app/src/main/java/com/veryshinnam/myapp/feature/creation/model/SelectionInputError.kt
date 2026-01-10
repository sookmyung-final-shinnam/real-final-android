package com.veryshinnam.myapp.feature.creation.model

fun validateSelectionInput(input: String): SelectionInputError {
    val trimmed = input.trim()

    if (trimmed.isEmpty()) return SelectionInputError.EMPTY
    if (trimmed.length !in 1..15) return SelectionInputError.LENGTH

    // 숫자 체크
    if (trimmed.any { it.isDigit() }) return SelectionInputError.NUMBER_NOT_ALLOWED

    // 자모만 입력한 경우 체크 
    if (trimmed.all { it.isKoreanJamo() }) return SelectionInputError.EXIST_JAMO

    // 한글 완성형 + 공백만 허용
    if (!trimmed.all { it.isKorean() || it == ' ' }) return SelectionInputError.SPECIAL_CHAR

    return SelectionInputError.NONE
}

// 한글 완성형 체크
fun Char.isKorean(): Boolean {
    val code = this.code
    return code in 0xAC00..0xD7A3
}

// 한글 자모 체크
fun Char.isKoreanJamo(): Boolean {
    val code = this.code
    return code in 0x1100..0x11FF || code in 0x3130..0x318F
}

enum class SelectionInputError {
    NONE,
    EMPTY,
    LENGTH,
    EXIST_JAMO,
    SPECIAL_CHAR,
    NUMBER_NOT_ALLOWED
}