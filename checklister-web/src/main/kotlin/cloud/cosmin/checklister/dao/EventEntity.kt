package cloud.cosmin.checklister.dao

import cloud.cosmin.checklister.lib.event.Event
import org.hibernate.annotations.DynamicInsert
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@DynamicInsert
@Table(name = "event")
class EventEntity {
    companion object {
        fun from(event: Event, bytes: ByteArray): EventEntity {
            val entity = EventEntity()
            entity.id = event.id
            entity.type = event.type
            entity.bytes = bytes
            return entity
        }
    }

    @Id
    var id: UUID? = null

    @Column(nullable = false)
    var type: String? = null

    @Column(nullable = true)
    var bytes: ByteArray? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventEntity

        if (id != other.id) return false
        if (type != other.type) return false
        if (bytes != null) {
            if (other.bytes == null) return false
            if (!bytes!!.contentEquals(other.bytes!!)) return false
        } else if (other.bytes != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (bytes?.contentHashCode() ?: 0)
        return result
    }
}
