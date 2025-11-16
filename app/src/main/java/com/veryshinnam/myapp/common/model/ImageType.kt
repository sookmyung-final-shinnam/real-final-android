package com.veryshinnam.myapp.common.model

import androidx.annotation.DrawableRes

sealed class ImageType {
    data class Url(val url: String) : ImageType()
    data class Resource(@DrawableRes val resId: Int) : ImageType()
}