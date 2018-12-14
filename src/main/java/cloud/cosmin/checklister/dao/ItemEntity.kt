package cloud.cosmin.checklister.dao

import org.hibernate.annotations.DynamicInsert

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import java.util.Objects
import java.util.UUID

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

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ItemEntity?
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
