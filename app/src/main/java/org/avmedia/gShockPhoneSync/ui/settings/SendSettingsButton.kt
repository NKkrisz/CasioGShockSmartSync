/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-23, 9:38 a.m.
 */

package org.avmedia.gShockPhoneSync.ui.settings

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.gson.Gson
import org.avmedia.gShockPhoneSync.casio.SettingsTransferObject
import org.avmedia.gShockPhoneSync.customComponents.Button
import org.avmedia.gShockPhoneSync.utils.Utils

class SendSettingsButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    init {
        setOnTouchListener(OnTouchListener())
        onState()
    }

    inner class OnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    updateSettings()
                }
            }
            v?.performClick()
            return false
        }
    }

    private fun updateSettings() {
        var settingsTransferObj = SettingsTransferObject()

        val localeSetting = SettingsModel.locale as SettingsModel.Locale
        settingsTransferObj.language = localeSetting.dayOfWeekLanguage.value
        settingsTransferObj.timeFormat = localeSetting.timeFormat.value
        settingsTransferObj.dateFormat = localeSetting.dateFormat.value

        val lightSetting = SettingsModel.light as SettingsModel.Light
        settingsTransferObj.autoLight = lightSetting.autoLight
        settingsTransferObj.lightDuration = lightSetting.duration.value

        val powerSavingMode = SettingsModel.powerSavingMode as SettingsModel.PowerSavingMode
        settingsTransferObj.powerSavingMode = powerSavingMode.powerSavingMode

        val buttonTone = SettingsModel.buttonSound as SettingsModel.OperationSound
        settingsTransferObj.buttonTone = buttonTone.sound

        val settingJson = Gson().toJson(settingsTransferObj)
        sendMessage("{action: \"SET_SETTINGS\", value: ${settingJson}}")
        Utils.snackBar(context, "Settings Sent to Watch")
    }
}