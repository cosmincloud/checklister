package cloud.cosmin.checklister.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties("eventsave")
class EventConfig {
    var enabled: Boolean = false
}