package cloud.cosmin.checklister.repository

import cloud.cosmin.checklister.entity.ItemEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import java.time.OffsetDateTime

import java.util.UUID

interface ItemRepository : PagingAndSortingRepository<ItemEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM rank_up(?1, ?2)")
    fun rankUp(itemId: UUID, modified_at: OffsetDateTime): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_down(?1, ?2)")
    fun rankDown(itemId: UUID, modified_at: OffsetDateTime): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_top(?1, ?2)")
    fun rankTop(itemId: UUID, modified_at: OffsetDateTime): ItemEntity

    @Query(nativeQuery = true, value = "SELECT * FROM rank_bottom(?1, ?2)")
    fun rankBottom(itemId: UUID,  modified_at: OffsetDateTime): ItemEntity

    @Query(nativeQuery = true, value = "UPDATE item SET list_id = ?2 WHERE list_id = ?1")
    fun moveItems(from: UUID, to: UUID)
}
