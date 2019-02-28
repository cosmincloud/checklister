package cloud.cosmin.checklister.repo

import cloud.cosmin.checklister.dao.ItemEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

import java.util.UUID

interface ItemRepo : PagingAndSortingRepository<ItemEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM rank_up(?1)")
    fun rankUp(itemId: UUID): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_down(?1)")
    fun rankDown(itemId: UUID): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_top(?1)")
    fun rankTop(itemId: UUID): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_bottom(?1)")
    fun rankBottom(itemId: UUID): ItemEntity
}
