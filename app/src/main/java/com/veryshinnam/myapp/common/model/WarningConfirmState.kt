package com.veryshinnam.myapp.common.model

data class WarningConfirmState(
    val isVisible: Boolean = false,
    val warningText: String = "",
    val confirmText: String = "",
    val onConfirm: () -> Unit = {  }
)
