package cloud.cosmin.checklister.lib.eventserde.json

import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.serde.EventDeserializer
import cloud.cosmin.checklister.lib.event.serde.EventSerializer
import com.fasterxml.jackson.databind.ObjectMapper

class JsonEventSerde : EventSerializer, EventDeserializer {
    companion object {
        private val mapper: ObjectMapper = ObjectMapper()
    }

    override fun serialize(event: Event): ByteArray {
        return mapper.writeValueAsBytes(event)
    }

    override fun deserialize(byteArray: ByteArray, cls: Class<Event>): Event {
        return mapper.readValue(byteArray, cls)
    }
}