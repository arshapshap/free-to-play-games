package com.example.games.presentation.utils

import androidx.annotation.DrawableRes
import com.example.games.R
import com.example.games.domain.models.Platform

@DrawableRes
fun Platform.getDrawable(): Int {
    return when (this) {
        Platform.Windows -> R.drawable.ic_windows
        Platform.WebBrowser -> R.drawable.ic_browser
    }
}