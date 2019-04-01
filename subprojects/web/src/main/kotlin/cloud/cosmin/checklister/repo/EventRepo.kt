package cloud.cosmin.checklister.repo

import cloud.cosmin.checklister.dao.EventEntity
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface EventRepo : PagingAndSortingRepository<EventEntity, UUID>