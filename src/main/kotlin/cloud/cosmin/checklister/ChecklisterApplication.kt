package cloud.cosmin.checklister

import cloud.cosmin.checklister.lib.event.serializer.ToStringEventSerializer
import cloud.cosmin.checklister.lib.event.sink.EventSink
import cloud.cosmin.checklister.lib.eventsink.logger.LoggerEventSink
import org.apache.logging.log4j.LogManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ChecklisterApplication {
    @Bean
    fun eventSink(): EventSink {
        val serializer = ToStringEventSerializer()
        val logger = LogManager.getLogger(LoggerEventSink::class.java)
        return LoggerEventSink(serializer, logger)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ChecklisterApplication::class.java, *args)
}