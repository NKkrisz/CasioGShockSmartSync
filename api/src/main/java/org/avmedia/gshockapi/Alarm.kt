package org.avmedia.gshockapi

open class Alarm(
    var hour: Int,
    var minute: Int,
    var enabled: Boolean,
    var hasHourlyChime: Boolean = false
) {
    override fun toString(): String {
        return "Alarm(hour=$hour, minute=$minute, enabled=$enabled, hasHourlyChime=$hasHourlyChime)"
    }

    companion object {
        var alarms: ArrayList<Alarm> = ArrayList()

        fun addSorted(source: ArrayList<Alarm>) {
            alarms.addAll(if (source.size == 1) 0 else alarms.size, source)
        }

        fun clear() {
            alarms.clear()
        }
    }
}