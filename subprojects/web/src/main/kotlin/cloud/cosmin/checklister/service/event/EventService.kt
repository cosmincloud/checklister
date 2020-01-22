package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.config.EventConfig
import cloud.cosmin.checklister.entity.EventEntity
import cloud.cosmin.checklister.lib.dto.EventDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.repo.EventRepo
import cloud.cosmin.checklister.service.ConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class EventService
@Autowired constructor(private val config: EventConfig,
                       private val eventRepo: EventRepo,
                       private val converterService: ConverterService) {
    fun save(event: Event, bytes: ByteArray) {
        if (config.enabled) {
            val eventEntity = EventEntity.from(event, bytes)
            eventRepo.save(eventEntity)
        }
    }

    fun findAll(pageable: Pageable): Page<EventDto> {
        return eventRepo.findAll(pageable)
                .map(converterService::eventDto)
    }

    fun delete(id: UUID): Optional<EventDto> {
        val optEvent = eventRepo.findById(id)
        if (optEvent.isPresent) {
            val event = optEvent.get()
            eventRepo.deleteById(event.id!!)
            return Optional.of(converterService.eventDto(event))
        } else {
            return Optional.empty()
        }
    }
}