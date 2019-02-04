package cloud.cosmin.checklister.service.event.eventsink

import cloud.cosmin.checklister.service.event.EventSink
import cloud.cosmin.checklister.service.event.Event
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class LoggerEventSink : EventSink {
    private val log = LogManager.getLogger(javaClass)

    override fun accept(event: Event) {
        log.info("Event: {}", String(event.serialize()))
    }
}