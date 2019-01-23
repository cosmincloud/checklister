package cloud.cosmin.checklister.service.eventsink

import cloud.cosmin.checklister.service.EventSink
import cloud.cosmin.checklister.service.ItemRankEvent
import cloud.cosmin.checklister.service.ItemUpdateEvent
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class LoggerEventSink : EventSink {
    private val log = LogManager.getLogger(javaClass)

    override fun accept(event: ItemUpdateEvent) {
        log.info("ItemEvent: {}", event)
    }

    override fun accept(event: ItemRankEvent) {
        log.info("ItemEvent: {}", event)
    }
}