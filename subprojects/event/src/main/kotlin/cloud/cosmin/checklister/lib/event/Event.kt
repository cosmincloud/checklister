package cloud.cosmin.checklister.lib.event

import cloud.cosmin.checklister.lib.dto.Mappable
import java.util.*

/**
 * This class is used to encode data events in the system.
 * Data events include creation, update, and deletion of
 * domain entities.
 */
data class Event(
    /**
     * A unique id for this event.  Supports event auditing and tracking.
     */
    val id: UUID,

    /**
     * A marker field to indicate an event type.
     */
    val type: String,

    /**
     * The entity before this event was triggered.
     */
    val before: Map<String, String>?,

    /**
     * The entity after this event was completed.
     */
    val after: Map<String, String>?
) {
    companion object {
        fun create(id: UUID, type: String, after: Mappable): Event {
            return Event(id, type, null, after.toMap())
        }

        fun update(id: UUID, type: String, before: Mappable, after: Mappable): Event {
            return Event(id, type, before.toMap(), after.toMap())
        }
    }
}