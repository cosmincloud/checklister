package cloud.cosmin.checklister.discovery

import java.lang.String.format

class Service(val host: String, val port: Int) {
    val http: String

    init {
        this.http = format("http://%s:%d", host, port)
    }
}
