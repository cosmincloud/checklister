package cloud.cosmin.checklister.lib.event

import cloud.cosmin.checklister.lib.dto.ListGetDto

interface ListEvents {
    fun create(dto: ListGetDto)
    fun update(before: ListGetDto, after: ListGetDto)
}