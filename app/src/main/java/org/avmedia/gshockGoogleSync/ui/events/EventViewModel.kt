package org.avmedia.gshockGoogleSync.ui.events

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.utils.Utils
import org.avmedia.gshockapi.Event
import org.avmedia.gshockapi.EventAction
import org.avmedia.gshockapi.ProgressEvents
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: GShockRepository
) : ViewModel() {

    init {
        listenForUpdateRequest()
    }

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    fun loadEvents(context: Context) {
        viewModelScope.launch {
            try {
                val loadedEvents = CalendarEvents.getEventsFromCalendar(context)
                _events.value = loadedEvents
                EventsModel.refresh(context)
            } catch (e: Exception) {
                ProgressEvents.onNext("ApiError", e.message)
            }
        }
    }

    fun toggleEvents(index: Int, isEnabled: Boolean) {
        _events.value = _events.value.toMutableList().apply {
            this[index] = this[index].copy(enabled = isEnabled)
        }
    }

    private fun listenForUpdateRequest() {
        val eventActions = arrayOf(
            EventAction("CalendarUpdated") {
                Timber.d("CalendarUpdated, events: ${EventsModel.events.size}")
                _events.value = ProgressEvents.getPayload("CalendarUpdated") as List<Event>
            },
        )

        ProgressEvents.runEventActions(
            Utils.AppHashCode() + "listenForUpdateRequest",
            eventActions
        )
    }

    fun sendEventsToWatch() {
        viewModelScope.launch {
            try {
                val events = _events.value
                repository.setEvents(ArrayList(_events.value))
                AppSnackbar("Events Set")
            } catch (e: Exception) {
                ProgressEvents.onNext("ApiError", e.message ?: "")
            }
        }
    }
}