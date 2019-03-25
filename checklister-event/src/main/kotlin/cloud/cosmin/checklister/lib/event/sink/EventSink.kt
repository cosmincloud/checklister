package cloud.cosmin.checklister.lib.event.sink

import cloud.cosmin.checklister.lib.event.Event

interface EventSink {
    /**
     * @return The serialized event.
     */
    fun accept(event: Event): ByteArray
}