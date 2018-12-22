package cloud.cosmin.checklister.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class UuidService {
    fun get(): UUID {
        return UUID.randomUUID()
    }
}