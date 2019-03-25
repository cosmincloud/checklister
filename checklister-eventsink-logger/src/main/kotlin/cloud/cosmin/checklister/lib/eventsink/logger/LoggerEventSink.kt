package cloud.cosmin.checklister.lib.eventsink.logger

import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.serde.EventSerializer
import cloud.cosmin.checklister.lib.event.sink.EventSink
import org.apache.logging.log4j.Logger

class LoggerEventSink(private val serializer: EventSerializer,
                      private val log: Logger) : EventSink {
    override fun accept(event: Event): ByteArray {
        val bytes = serializer.serialize(event)
        log.info("Event: {}", String(bytes))
        return bytes
    }
}