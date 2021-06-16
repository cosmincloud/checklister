package cloud.cosmin.checklister.entity

import org.hibernate.annotations.DynamicInsert
import java.time.OffsetDateTime
import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@DynamicInsert
@Table(name = "item")
class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @Column(nullable = false)
    var content: String? = null

    @Column(nullable = false)
    var rank: Int = 0

    @Column(nullable = false)
    var contentType: String? = null

    @ManyToOne
    @JoinColumn(nullable = false)
    var list: ListEntity? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null

    @Column(name = "last_modified", nullable = false)
    var lastModified: OffsetDateTime? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ItemEntity?
        return rank == that!!.rank &&
                id == that.id &&
                content == that.content &&
                contentType == that.contentType &&
                list == that.list
    }

    override fun hashCode(): Int {
        return Objects.hash(id, content, rank, contentType, list)
    }
}
