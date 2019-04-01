package cloud.cosmin.checklister.service

import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.util.*

@Service
class BuildConfigService {
    fun getVersion(): String {
        val stream = javaClass.classLoader
                .getResourceAsStream("/checklister-build.properties")

        return when(stream) {
            null -> readVersionFromLocalFile()
            else -> {
                val props = Properties()
                props.load(stream)
                props.getProperty("version")
            }
        }
    }

    /*
     * When running directly from IDE.
     */
    private fun readVersionFromLocalFile(): String {
        val gradleProperties = File("gradle.properties")
        if(gradleProperties.exists()) {
            val fis = FileInputStream(gradleProperties)
            val props = Properties()
            props.load(fis)
            return props.getProperty("version") ?: "null"
        } else {
            return "null"
        }
    }
}