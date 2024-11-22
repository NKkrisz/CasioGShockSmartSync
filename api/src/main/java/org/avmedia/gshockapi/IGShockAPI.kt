package org.avmedia.gshockapi

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay
import org.avmedia.gshockapi.ble.Connection
import org.avmedia.gshockapi.casio.*
import org.avmedia.gshockapi.io.*
import org.avmedia.gshockapi.utils.*
import timber.log.Timber
import java.time.ZoneId
import java.util.*

interface IGShockAPI {
    suspend fun waitForConnection(deviceId: String? = "", deviceName: String? = "")
    suspend fun init(): Boolean
    fun isConnected(): Boolean
    fun teardownConnection(device: BluetoothDevice)
    suspend fun getPressedButton(): IO.WATCH_BUTTON
    fun isActionButtonPressed(): Boolean
    fun isNormalButtonPressed(): Boolean
    fun isAutoTimeStarted(): Boolean
    fun isFindPhoneButtonPressed(): Boolean
    suspend fun getWatchName(): String
    suspend fun getError(): String
    suspend fun getDSTWatchState(state: IO.DTS_STATE): String
    suspend fun getDSTForWorldCities(cityNumber: Int): String
    suspend fun getWorldCities(cityNumber: Int): String
    suspend fun getHomeTime(): String
    suspend fun getBatteryLevel(): Int
    suspend fun getWatchTemperature(): Int
    suspend fun getTimer(): Int
    fun setTimer(timerValue: Int)
    suspend fun getAppInfo(): String
    suspend fun setTime(timeZone: String = TimeZone.getDefault().id, timeMs: Long? = null)
    suspend fun getAlarms(): ArrayList<Alarm>
    fun setAlarms(alarms: ArrayList<Alarm>)
    suspend fun getEventsFromWatch(): ArrayList<Event>
    suspend fun getEventFromWatch(eventNumber: Int): Event
    fun setEvents(events: ArrayList<Event>)
    fun clearEvents()
    suspend fun getSettings(): Settings
    suspend fun getBasicSettings(): Settings
    suspend fun getTimeAdjustment(): TimeAdjustmentInfo
    fun setSettings(settings: Settings)
    fun disconnect()
    fun stopScan()
    fun close ()
    fun isBluetoothEnabled(context: Context): Boolean

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String)
    fun resetHand()
    fun validateBluetoothAddress(deviceAddress: String?): Boolean
    fun preventReconnection(): Boolean
}