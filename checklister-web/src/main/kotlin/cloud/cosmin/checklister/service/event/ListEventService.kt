package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.dao.EventEntity
import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.ListEvents
import cloud.cosmin.checklister.lib.event.sink.EventSink
import cloud.cosmin.checklister.repo.EventRepo
import cloud.cosmin.checklister.service.UuidService
import org.springframework.stereotype.Service

@Service
class ListEventService
constructor(private val eventSink: EventSink,
            private val uuidService: UuidService,
            private val eventRepo: EventRepo) : ListEvents {
    override fun create(dto: ListGetDto) {
        val event = Event.after(uuidService.get(), "LIST_CREATE", dto)
        val bytes = eventSink.accept(event)
        val eventEntity = EventEntity.from(event, bytes)
        eventRepo.save(eventEntity)
    }

    override fun update(before: ListGetDto, after: ListGetDto) {
        val event = Event.create(uuidService.get(), "LIST_UPDATE", before, after)
        val bytes = eventSink.accept(event)
        val eventEntity = EventEntity.from(event, bytes)
        eventRepo.save(eventEntity)
    }
}