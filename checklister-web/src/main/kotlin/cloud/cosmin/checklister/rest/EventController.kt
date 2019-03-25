package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.lib.event.Event
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
@Api(description = "Operations on events that were emitted from this service", tags = arrayOf("bytes"))
class EventController {
    fun getEvents(): ResponseEntity<List<Event>> {
        return ResponseEntity.status(500).build()
    }

    fun deleteEvent(id: UUID): ResponseEntity<Event> {
        return ResponseEntity.status(500).build()
    }
}