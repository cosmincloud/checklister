package cloud.cosmin.checklister.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UuidServiceTest {
    @Test
    fun test() {
        val service = UuidService()
        assertNotNull(service.get())
    }
}