package cloud.cosmin.checklister.repository

import cloud.cosmin.checklister.entity.ListEntity
import org.springframework.data.repository.PagingAndSortingRepository

import java.util.UUID

interface ListRepository : PagingAndSortingRepository<ListEntity, UUID>
