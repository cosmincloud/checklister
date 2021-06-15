package cloud.cosmin.checklister.lib.event.serde

import cloud.cosmin.checklister.lib.event.Event

interface EventDeserializer {
    fun deserialize(byteArray: ByteArray, cls: Class<Event>): Event
}