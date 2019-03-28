package cloud.cosmin.checklister.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("kafkasink")
class KafkaConfig {
    var enabled: Boolean = false
    var brokers: String? = null
    var topic: String? = null
}