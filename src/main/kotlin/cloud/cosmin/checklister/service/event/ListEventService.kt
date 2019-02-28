package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.ListEvents
import cloud.cosmin.checklister.lib.event.model.ListCreateEvent
import cloud.cosmin.checklister.lib.event.model.ListUpdateEvent
import cloud.cosmin.checklister.lib.event.sink.EventSink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ListEventService @Autowired
constructor(private val eventSink: EventSink) : ListEvents {
    override fun create(dto: ListGetDto) {
        val event = ListCreateEvent(dto)
        eventSink.accept(event)
    }

    override fun update(before: ListGetDto, after: ListGetDto) {
        val event = ListUpdateEvent(before, after)
        eventSink.accept(event)
    }
}