package cloud.cosmin.checklister.lib.event.sink

import cloud.cosmin.checklister.lib.event.Event

interface EventSink {
    fun accept(event: Event)
}