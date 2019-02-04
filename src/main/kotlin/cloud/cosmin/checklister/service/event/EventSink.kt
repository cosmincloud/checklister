package cloud.cosmin.checklister.service.event

interface EventSink {
    fun accept(event: Event)
}