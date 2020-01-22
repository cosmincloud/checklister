package cloud.cosmin.checklister.repository

import cloud.cosmin.checklister.entity.EventEntity
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface EventRepository : PagingAndSortingRepository<EventEntity, UUID>