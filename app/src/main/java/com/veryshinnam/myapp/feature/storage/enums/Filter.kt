package com.veryshinnam.myapp.feature.storage.enums

sealed interface Filter {
    data object ALL : Filter
}