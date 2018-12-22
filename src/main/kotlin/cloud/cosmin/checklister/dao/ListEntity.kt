package cloud.cosmin.checklister.dao

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
    var items: List<ItemEntity>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ListEntity?
        return id == that!!.id &&
                title == that.title &&
                items == that.items
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, items)
    }
}
