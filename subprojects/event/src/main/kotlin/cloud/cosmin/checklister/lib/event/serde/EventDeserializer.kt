package cloud.cosmin.checklister.lib.event.serde

import cloud.cosmin.checklister.lib.event.Event

interface EventDeserializer {
    fun <T : Event> deserialize(byteArray: ByteArray, cls: Class<T>): T
}