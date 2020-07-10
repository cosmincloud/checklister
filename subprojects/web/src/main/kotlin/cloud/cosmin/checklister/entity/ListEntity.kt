package cloud.cosmin.checklister.entity

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "list")
class ListEntity {
    @Id
    var id: UUID? = null

    @Column(nullable = false)
    var title: String = ""

    @OneToMany(mappedBy = "list")
    @Fetch(FetchMode.JOIN)
    @OrderBy("rank")
    var items: List<ItemEntity> = emptyList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ListEntity?
        return id == that!!.id &&
                title == that.title &&
                items == that.items
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, items)
    }
}
