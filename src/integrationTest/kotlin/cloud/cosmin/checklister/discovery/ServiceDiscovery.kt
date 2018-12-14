package cloud.cosmin.checklister.discovery

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import java.io.File
import java.io.IOException

object ServiceDiscovery {
    private val mapper = ObjectMapper(YAMLFactory())

    @Throws(IOException::class)
    private fun getServicePort(serviceName: String, internalPort: Int): Int {
        val tree = mapper.readTree(File("docker-compose.yml"))

        val ports = tree.get("services").get(serviceName).get("ports")

        for (i in 0 until ports.size()) {
            val port = ports.get(i).asText()
            if (port.contains(":")) {
                val portParts = port.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (Integer.parseInt(portParts[1]) == internalPort) {
                    return Integer.parseInt(portParts[0])
                }
            }
        }
        return -1
    }

    @Throws(IOException::class)
    fun getService(name: String): Service {
        var host: String? = System.getenv(name + "_HOST")
        val port = System.getenv(name + "_TCP_8080")

        if (host == null) {
            host = "localhost"
        }

        return if (port == null) {
            Service(host, getServicePort("checklister", 8080))
        } else Service(host, Integer.valueOf(port))

    }
}
