package cloud.cosmin.checklister.lib.event.serde

import cloud.cosmin.checklister.lib.event.Event

interface EventSerializer {
    fun serialize(event: Event): ByteArray
}