package cloud.cosmin.checklister.lib.event.serializer

import cloud.cosmin.checklister.lib.event.Event

interface EventSerializer {
    fun serialize(event: Event): ByteArray
}