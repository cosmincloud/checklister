package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.lib.dto.EventDto
import cloud.cosmin.checklister.service.event.EventService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Controller
@Api(description = "Operations on events that were emitted from this service", tags = arrayOf("event"))
class EventController
@Autowired constructor(private val eventService: EventService) {
    @GetMapping("/api/v1/event")
    @ApiOperation("List events")
    fun getEvents(pageable: Pageable): ResponseEntity<Page<EventDto>> {
        val page = eventService.findAll(pageable)
        return ResponseEntity.ok(page)
    }

    @DeleteMapping("/api/v1/event/{id}")
    @ApiOperation("Delete an event")
    fun deleteEvent(@PathVariable id: UUID): ResponseEntity<EventDto> {
        return ResponseEntity.of(eventService.delete(id))
    }
}