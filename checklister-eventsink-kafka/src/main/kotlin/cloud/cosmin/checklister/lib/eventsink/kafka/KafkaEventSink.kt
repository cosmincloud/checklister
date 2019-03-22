package cloud.cosmin.checklister.lib.eventsink.kafka

import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.serde.EventSerializer
import cloud.cosmin.checklister.lib.event.sink.EventSink
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class KafkaEventSink(brokers: String,
                     private val topic: String,
                     private val serializer: EventSerializer): EventSink {
    private val producer: KafkaProducer<String, ByteArray> by lazy {
        val properties = Properties()
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers)
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer")
        KafkaProducer<String, ByteArray>(properties)
    }

    override fun accept(event: Event) {
        val eventBytes = serializer.serialize(event)
        val record = ProducerRecord<String, ByteArray>(topic, null, eventBytes)
        producer.send(record)
    }

    fun close(): Unit {
        producer.close()
    }
}