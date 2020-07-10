package cloud.cosmin.checklister.lib.eventsink.kafka

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.eventserde.json.JsonEventSerde
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.*

@DisplayName("KafkaEventSink")
class KafkaEventSinkTest {
    private val log = LogManager.getLogger(javaClass)

    @Test
    @DisplayName("should publish events to a kafka topic (ToStringSerializer)")
    fun simplePublish() {
        val bootstrapServers = "localhost:9092"
        val topic = "checklister.events"
        val groupId = UUID.randomUUID().toString()
        val pollTimeout = Duration.ofSeconds(5)

        log.info("Kafka consumer group: {}", groupId)

        val properties = Properties()
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        properties.setProperty(GROUP_ID_CONFIG, groupId)
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer")

        val consumer = KafkaConsumer<String, ByteArray>(properties)
        consumer.subscribe(listOf(topic))

        val records = consumer.poll(pollTimeout)
        assertTrue(records.isEmpty)

        val serializer = JsonEventSerde()
        val sink = KafkaEventSink(bootstrapServers, topic, serializer)

        val eventUuid = UUID.randomUUID()
        val listId = UUID.randomUUID()
        // TODO: Fix this test.
        val event = Event(eventUuid, "list_create", null, null)
        sink.accept(event)

        val recordsList = consumer.poll(pollTimeout).toList()
        assertFalse(recordsList.isEmpty())
        assertEquals(1, recordsList.size)
        val record = recordsList.get(0)
        assertEquals("{\"id\":\"$eventUuid\",\"item\":{\"id\":\"$listId\",\"title\":\"list title\"}}", String(record.value()!!))

        consumer.close()
        sink.close()
    }
}