package cloud.cosmin.checklister.lib.event

import java.util.UUID

interface Event {
    val eventId: UUID
}