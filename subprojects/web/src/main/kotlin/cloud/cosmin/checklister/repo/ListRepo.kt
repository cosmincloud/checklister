package cloud.cosmin.checklister.repo

import cloud.cosmin.checklister.entity.ListEntity
import org.springframework.data.repository.PagingAndSortingRepository

import java.util.UUID

interface ListRepo : PagingAndSortingRepository<ListEntity, UUID>
