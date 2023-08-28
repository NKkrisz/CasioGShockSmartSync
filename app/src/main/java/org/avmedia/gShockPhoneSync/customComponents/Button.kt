/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-28, 4:05 p.m.
 */

package org.avmedia.gShockPhoneSync.customComponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import org.avmedia.gshockapi.utils.WatchDataEvents

open class Button @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : com.google.android.material.button.MaterialButton(context, attrs, defStyleAttr) {

    init {
    }

    open fun show() {
        visibility = VISIBLE
    }

    @SuppressLint("CheckResult")
    protected fun subscribe(subject: String, ignoredOnDataReceived: (String) -> Unit) {
        WatchDataEvents.addSubject(subject)
        WatchDataEvents.subscribe(this.javaClass.canonicalName, subject, onNext = {
            ignoredOnDataReceived(it as String)
        })
    }

    protected open fun onState() {
    }
}
