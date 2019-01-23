package cloud.cosmin.checklister.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class BuildConfigService {
    fun getVersion(): String {
        val stream = javaClass.classLoader
                .getResourceAsStream("/checklister-build.properties")

        return when(stream) {
            null -> "null"
            else -> {
                val props = Properties()
                props.load(stream)
                props.getProperty("version")
            }
        }
    }
}