package com.veryshinnam.myapp.feature.creation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Gender: Parcelable {
    NONE, FEMALE, MALE
}