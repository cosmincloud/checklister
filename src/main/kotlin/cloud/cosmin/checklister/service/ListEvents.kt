package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ListGetDto

interface ListEvents {
    fun create(dto: ListGetDto)
    fun update(before: ListGetDto, after: ListGetDto)
}