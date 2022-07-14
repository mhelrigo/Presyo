package com.mhelrigo.presyo.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val sharedPreferences: SharedPreferences) :
    ViewModel() {
    lateinit var colorMode: (ColorMode) -> Unit

    fun modifyColorMode(colorMode: ColorMode) {
        var colorModeTemp: ColorMode =
            if (colorMode == ColorMode.LIGHT_MODE) {
                ColorMode.DARK_MODE
            } else {
                ColorMode.LIGHT_MODE
            }

        setColorMode(colorModeTemp)
        sharedPreferences.edit().putInt(COLOR_MODE, colorModeTemp.value).apply()
    }

    fun setColorMode(colorMode: ColorMode) {
        this.colorMode.invoke(colorMode)
    }

    fun getColorMode(): ColorMode {
        var colorMode: ColorMode

        sharedPreferences.getInt(COLOR_MODE, ColorMode.DARK_MODE.value).also {
            colorMode = when (it) {
                ColorMode.LIGHT_MODE.value -> {
                    ColorMode.LIGHT_MODE
                }
                ColorMode.DARK_MODE.value -> {
                    ColorMode.DARK_MODE
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        return colorMode
    }
}