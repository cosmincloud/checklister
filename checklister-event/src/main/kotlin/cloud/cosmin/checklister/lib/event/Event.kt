package cloud.cosmin.checklister.lib.event

import cloud.cosmin.checklister.lib.dto.Mappable
import java.util.*

data class Event(
    val id: UUID, // support event auditing and tracking
    val type: String, // because we can't use typed classes
    val before: Map<String, String>?, // what the entity contained before this event
    val after: Map<String, String>? // after what the entity contained after this event
) {
    companion object {
        fun create(id: UUID, type: String, before: Mappable, after: Mappable): Event {
            return Event(id, type, before.toMap(), after.toMap())
        }

        fun after(id: UUID, type: String, after: Mappable): Event {
            return Event(id, type, null, after.toMap())
        }
    }
}